package sabo.yahvya.githubdirectoryextractor.githubextractor.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import sabo.yahvya.githubdirectoryextractor.githubextractor.extractor.GithubDirectoryExtractorManager;

import java.io.File;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @brief Gestionnaire d'api git
 */
public class GithubApi {
    /**
     * @brief Utilitaire de log
     */
    protected GithubDirectoryExtractorManager.ExtractionAction log;

    /**
     * @brief Client de requête
     */
    protected HttpClient client;

    /**
     * @param log gestionnaire de log
     * @throws UncheckedIOException en cas d'échec d'initialisation du client http
     */
    public GithubApi(GithubDirectoryExtractorManager.ExtractionAction log) throws UncheckedIOException {
        this.log = log;
        this.client = HttpClient.newHttpClient();
    }

    /**
     * @brief Télécharge le contenu du dossier du chemin fourni
     * @param directoryPath chemin du dossier git
     * @param storeIn chemin du dossier de stockage
     */
    public boolean downloadRepositoryDirectoryContent(String directoryPath,String storeIn){
        // récupération des données du dossier
        this.log.execute("Récupération des données (propriétaire et repository) à partir du lien");

        Map<String,String> repoDatas = GithubApi.extractRepoDatasFromLink(directoryPath);

        if(repoDatas == null){
            this.log.execute("Echec de récupération des données (propriétaire et repository) à partir du lien");
            return false;
        }

        // construction de la requête de récupération
        String requestUri = GithubApi.formatUri(
            GithubApiLinks.GET_REPOSITORY_CONTENT.link,
            Map.of(
                "owner",repoDatas.get("owner"),
                "repo",repoDatas.get("repo"),
                "path",repoDatas.get("path"),
                "branch",repoDatas.get("branch")
            )
        );

        // extraction récursive
        return this.downloadDirectoryContent(requestUri,storeIn);
    }

    /**
     * @brief Télécharge récursivement les données d'un dossier
     * @param requestUri url de requête de récupération du contenu
     * @param storeIn dossier de stockage
     * @return si le téléchargement réussi
     */
    protected boolean downloadDirectoryContent(String requestUri,String storeIn){
        if(!storeIn.endsWith("/") && !storeIn.endsWith("\\"))
            storeIn += "/";

        try{
            // exécution et récupération de la réponse
            HttpRequest request = HttpRequest.newBuilder(URI.create(requestUri))
                .GET()
                .build();

            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            final int statusCode = response.statusCode();

            if(statusCode != 200){
                this.log.execute(String.format("Echec de téléchargement, code status <%d>", statusCode));
                return false;
            }

            // parsing du contenu de la réponse
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<?> results = mapper.readValue(response.body(),ArrayList.class);

            for(Object o : results){
                if(!(o instanceof Map<?, ?> result)){
                    this.log.execute("Résultat inattendu");
                    return false;
                }

                // vérification du type de donnée récupérée

                switch((String) result.get("type")){
                    case "dir":
                        // création du dossier
                        String directoryPath = String.format("%s%s/",storeIn,result.get("name"));

                        File f = new File(directoryPath);

                        if(!f.isDirectory() && !f.mkdir()){
                            this.log.execute(String.format("Echec de création du dossier <%s>",result.get("path")));
                            return false;
                        }

                        // récupération du contenu
                        this.log.execute(String.format("Récupération du contenu du dossier %s",directoryPath));

                        if(!this.downloadDirectoryContent((String)result.get("url"),directoryPath))
                            return false;
                    break;

                    case "file":
                        // récupération du fichier
                        InputStream inputStream = URI.create((String) result.get("download_url"))
                            .toURL()
                            .openStream();

                        // téléchargement du fichier
                        String filePath = String.format("%s%s",storeIn,result.get("name"));
                        Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

                        this.log.execute(String.format("Téléchargement du fichier %s",filePath));
                    break;

                    default:
                        this.log.execute("Type de donnée non connu");
                        return false;
                }
            }

            return true;
        }
        catch(Exception e){
            this.log.execute(String.format("Echec de récupération du lien <%s>",requestUri));
            return false;
        }
    }

    /**
     * @brief Formate l'URL en remplaçant les paramètres du format {param} à la donnée associée au nom dans la map
     * @param uri url
     * @param paramsMap map des remplacements
     * @return l'url formatée
     */
    public static String formatUri(String uri, Map<String,String> paramsMap){
        String uriClone = String.valueOf(uri);

        for(String key : paramsMap.keySet())
            uriClone = uriClone.replaceAll("\\{"+key+"\\}", paramsMap.get(key));

        return uriClone;
    }

    /**
     * @brief Extrait les données de repository à partir du lien (propriétaire dans la clé owner, nom du repository dans la clé repo, la branche dans la clé branch, le chemin dans la clé path)
     * @brief Regex : .+github.com/([^/]+)/([^/]+)/.*
     * @param link lien
     * @return les données extraites ou null en cas de format non valide
     */
    public static Map<String,String> extractRepoDatasFromLink(String link){
        try{
            // récupération des données via regex
            Pattern pattern = Pattern.compile(".+github.com/([^/]+)/([^/]+)/tree/([^/]+)/(.*)");
            Matcher matcher = pattern.matcher(link);

            if(!matcher.find())
                return null;

            final String owner = matcher.group(1);
            final String repo = matcher.group(2);
            final String branch = matcher.group(3);
            final String path = matcher.group(4);

            return owner == null || repo == null || branch == null || path == null ?
                null :
                Map.of(
                    "owner",owner,
                    "repo",repo,
                    "branch",branch,
                    "path",path
                );
        }
        catch(Exception ignore){
            return null;
        }
    }
}
