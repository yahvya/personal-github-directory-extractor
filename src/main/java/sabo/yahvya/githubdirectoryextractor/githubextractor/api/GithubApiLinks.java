package sabo.yahvya.githubdirectoryextractor.githubextractor.api;

/**
 * @brief Liens d'api git
 */
public enum GithubApiLinks {
    /**
     * @brief Lien d'initialisation du flow logiciel bureau
     */
    DEVICE_FLOW_AUTHORIZATION_INIT("https://github.com/login/device/code?client_id={clientId}"),

    ;

    /**
     * @brief Lien lié
     */
    public final String link;

    /**
     * @param link lien lié
     */
    GithubApiLinks(String link){
        this.link = link;
    }
}
