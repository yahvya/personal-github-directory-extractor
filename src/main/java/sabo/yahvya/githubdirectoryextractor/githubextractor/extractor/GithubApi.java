package sabo.yahvya.githubdirectoryextractor.githubextractor.extractor;

import sabo.yahvya.githubdirectoryextractor.githubextractor.api.GithubApiLinks;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @brief Gestionnaire d'api git
 */
public class GithubApi {
    /**
     * @brief Id client
     */
    protected static final String clientId = "Iv23lixidMnH9objij0q";

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
     */
    public GithubApi(GithubDirectoryExtractorManager.ExtractionAction log){
        this.log = log;

        try{
            this.client = HttpClient.newHttpClient();
        }catch(Exception ignored){
            this.client = null;
        }
    }

    /**
     * @brief Lance la récupération du token de gestion github
     * @return le token récupéré ou null
     */
    public String getToken(){
        if(client == null) return null;

        this.log.execute("Initialisation du flow bureau");

        String token = null;

        try{
            // construction de la requête
            String requestUri = GithubApi.formatUri(
                GithubApiLinks.DEVICE_FLOW_AUTHORIZATION_INIT.link,
                Map.of("clientId",GithubApi.clientId)
            );

            HttpRequest request = HttpRequest.newBuilder(URI.create(requestUri))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

            // récupération des données device
            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            Map<String,String> desktopFlowInitDatas = GithubApi.formatDesktopFlowInitDatas(response.body());

            // envoi à l'utilisateur
            this.log.execute(String.format("Veuillez saisir le code : %s",desktopFlowInitDatas.get("user_code")));




            this.log.execute("Token récupéré");
        }
        catch(Exception e){
            this.log.execute("Echec de récupération du token, il est possible que la fréquence d'appel ait été atteinte");
        }

        return token;
    }

    /**
     * @brief Formate le retour d'initialisation de flow bureau
     * @param response la chaine réponse
     * @return map clés valeurs de retour
     */
    public static Map<String,String> formatDesktopFlowInitDatas(String response){
        Map<String,String> desktopFlowInitDatas = new HashMap<>();

        Pattern pattern = Pattern.compile("([^=]+)=([^&]*)&");
        Matcher matcher = pattern.matcher(response);

        while(matcher.find())
            desktopFlowInitDatas.put(matcher.group(1),matcher.group(2));

        return desktopFlowInitDatas;
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
}
