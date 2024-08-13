package sabo.yahvya.githubdirectoryextractor.resources.utils;

/**
 * @brief Ressource de l'application
 */
public enum ResourcesPath {
    /**
     * @brief Chemin de l'icône de l'application
     */
    APPLICATION_ICON_PATH("images/icon.png");

    /**
     * @brief Chemin associé
     */
    public final String path;

    ResourcesPath(String path){
        this.path = path;
    }
}
