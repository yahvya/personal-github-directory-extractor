package sabo.yahvya.githubdirectoryextractor.resources.utils;

/**
 * @brief Ressource de l'application
 */
public enum ResourcesPath {
    // ICÔNE DE L'APPLICATION
    /**
     * @brief Chemin de l'icône de l'application
     */
    APPLICATION_ICON_PATH("images/icon.png"),

    // STYLES

        // GLOBAL
        STYLES_GLOBAL_APP("css/app/color-theme.css"),

    ;

    /**
     * @brief Chemin associé
     */
    public final String path;

    ResourcesPath(String path){
        this.path = path;
    }
}
