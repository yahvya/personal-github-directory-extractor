package sabo.yahvya.githubdirectoryextractor.githubextractor.extractor;

import sabo.yahvya.githubdirectoryextractor.githubextractor.api.GithubApi;

import java.io.UncheckedIOException;

/**
 * @brief Extracteur de dossier
 */
public class GithubDirectoryExtractor {
    /**
     * @brief fonction de log
     */
    protected GithubDirectoryExtractorManager.ExtractionAction log;

    /**
     * @brief Configuration d'extraction
     */
    protected ExtractionConfig extractionConfig;

    /**
     * @brief Gestionnaire d'api
     */
    protected GithubApi apiManager;

    /**
     * @param log fonction de log
     * @param extractionConfig configuration d'extraction
     * @throws UncheckedIOException en cas d'échec d'initialisation de l'utilitaire d'api
     */
    public GithubDirectoryExtractor(GithubDirectoryExtractorManager.ExtractionAction log,ExtractionConfig extractionConfig) throws UncheckedIOException {
        this.log = log;
        this.extractionConfig = extractionConfig;
        this.apiManager = new GithubApi(log);
    }

    /**
     * @brief Lance l'extraction
     * @return si l'extraction réussie
     */
    public boolean extract(){
        return this.apiManager.downloadRepositoryDirectoryContent(this.extractionConfig.directoryLink,this.extractionConfig.destinationBase);
    }

    /**
     * @brief Configuration d'une extraction
     */
    public static class ExtractionConfig{
        /**
         * @brief lien du dossier github
         */
        protected String directoryLink;

        /**
         * @brief dossier de destination
         */
        protected String destinationBase;

        /**
         * @brief Action à exécuter à l'extraction de l'élément
         */
        protected GithubDirectoryExtractorManager.ExtractionAction onElementExtracted;

        /**
         * @param directoryLink lien du dossier github
         * @param destinationBase dossier de destination
         * @param onElementExtracted Action à exécuter à l'extraction de l'élément
         */
        public ExtractionConfig(String directoryLink, String destinationBase, GithubDirectoryExtractorManager.ExtractionAction onElementExtracted){
            this.directoryLink = directoryLink;
            this.destinationBase = destinationBase;
            this.onElementExtracted = onElementExtracted;
        }

        /**
         * @return lien du dossier github
         */
        public String getDirectoryLink() {
            return this.directoryLink;
        }

        /**
         * @return dossier de destination
         */
        public String getDestinationBase() {
            return this.destinationBase;
        }

        /**
         * @return Action à exécuter à l'extraction de l'élément
         */
        public GithubDirectoryExtractorManager.ExtractionAction getOnElementExtracted() {
            return this.onElementExtracted;
        }
    }
}
