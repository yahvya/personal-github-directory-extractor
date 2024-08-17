package sabo.yahvya.githubdirectoryextractor.githubextractor.api;

/**
 * @brief Liens d'api git
 */
public enum GithubApiLinks {
    /**
     * @brief Lien de récupération du contenu d'un repository
     */
    GET_REPOSITORY_CONTENT("https://api.github.com/repos/{owner}/{repo}/contents/{path}?ref={branch}")
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
