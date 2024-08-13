package sabo.yahvya.githubdirectoryextractor.resources.utils;

import sabo.yahvya.githubdirectoryextractor.GithubDirectoryExtractorApplication;

import java.net.URL;

/**
 * @brief Utilitaire de chargement de ressource
 * @param <FromType> class contenu
 */
public class ResourceLoader<FromType> {
    /**
     * @brief
     */
    protected Class<FromType> fromClass;

    /**
     * @param fromClass Class de chargement
     */
    public ResourceLoader(Class<FromType> fromClass) {
        this.fromClass = fromClass;
    }

    /**
     * @brief Fourni l'URL complète de la ressource
     * @param resourcePath chemin de la ressource à partir du dossier class lié
     * @return l'URL de la ressource ou NULL si non trouvée
     */
    public URL getResource(String resourcePath){
        GithubDirectoryExtractorApplication.appLogger.entering(this.getClass().getName(),"getResource");

        try{
            URL url = this.fromClass.getClassLoader().getResource(resourcePath);

            if(url == null)
                GithubDirectoryExtractorApplication.appLogger.warning("\tRessource non trouvée");

            return url;
        }
        catch(Exception e){
            GithubDirectoryExtractorApplication.appLogger.severe(String.format("Echec de chargement <%s>",e.getMessage()));
            return null;
        }
        finally {
            GithubDirectoryExtractorApplication.appLogger.exiting(this.getClass().getName(),"getResource");
        }
    }
}
