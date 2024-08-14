package sabo.yahvya.githubdirectoryextractor.githubextractor;

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
     * @param log fonction de log
     * @param extractionConfig configuration d'extraction
     */
    public GithubDirectoryExtractor(GithubDirectoryExtractorManager.ExtractionAction log,ExtractionConfig extractionConfig){
        this.log = log;
        this.extractionConfig = extractionConfig;
    }

    public boolean extract(){
        return true;
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
