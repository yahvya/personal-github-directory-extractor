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
        STYLES_GLOBAL_APP("css/app/app.css"),

        // COMPOSANTS
        STYLES_COMPONENTS_APP_BUTTON("css/components/app-button.css"),
        STYLES_COMPONENTS_INFO_TEXT("css/components/info-text.css"),

        // VUES
        STYLES_VIEWS_COMPONENT_TEST("css/views/component-test.css"),
        STYLES_VIEWS_ERROR_TEXT("css/views/error.css"),
        STYLES_VIEWS_EXTRACTION("css/views/extraction.css"),
    ;

    /**
     * @brief Chemin associé
     */
    public final String path;

    ResourcesPath(String path){
        this.path = path;
    }
}
