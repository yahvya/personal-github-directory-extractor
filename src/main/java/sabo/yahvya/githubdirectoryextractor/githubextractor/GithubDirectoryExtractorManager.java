package sabo.yahvya.githubdirectoryextractor.githubextractor;

import java.util.ArrayList;

/**
 * @brief Gestionnaire d'extraction de dossier Github
 */
public class GithubDirectoryExtractorManager extends Thread{
    /**
     * @brief Configuration d'extraction
     */
    protected ArrayList<ExtractionConfig> extractionConfigs;

    /**
     * @brief Lambda de log
     */
    protected ExtractionAction log;

    /**
     * @brief Lambda de fin d'extraction
     */
    protected ExtractionAction onEnd;

    /**
     * @brief Défini l'arrêt de l'extraction
     */
    protected boolean stop;

    /**
     * @param extractionConfigs Configuration d'extraction
     * @param log fonction de log des évènements
     * @param onEnd fonction de fin d'extraction
     */
    public GithubDirectoryExtractorManager(ArrayList<ExtractionConfig> extractionConfigs, ExtractionAction log, ExtractionAction onEnd) {
        this.extractionConfigs = extractionConfigs;
        this.log = log;
        this.onEnd = onEnd;
        this.stop = false;
    }

    @Override
    public void run() {
        this.log.execute("Lancement de l'extraction");
    }

    /**
     * @brief Lance l'arrêt de l'exécution
     */
    public void stopExecution(){
        this.stop = true;
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
        protected ExtractionAction onElementExtracted;

        /**
         * @param directoryLink lien du dossier github
         * @param destinationBase dossier de destination
         * @param onElementExtracted Action à exécuter à l'extraction de l'élément
         */
        public ExtractionConfig(String directoryLink,String destinationBase,ExtractionAction onElementExtracted){
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
        public ExtractionAction getOnElementExtracted() {
            return this.onElementExtracted;
        }
    }

    /**
     * @brief Action d'extraction
     */
    public static interface ExtractionAction{
        /**
         * @param args arguments d'exécution
         */
        public void execute(Object ...args);
    }
}
