package sabo.yahvya.githubdirectoryextractor.githubextractor;

import java.util.ArrayList;

/**
 * @brief Gestionnaire d'extraction de dossier Github
 */
public class GithubDirectoryExtractorManager extends Thread{
    /**
     * @brief Configuration d'extraction
     */
    protected ArrayList<GithubDirectoryExtractor.ExtractionConfig> extractionConfigs;

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
    public GithubDirectoryExtractorManager(ArrayList<GithubDirectoryExtractor.ExtractionConfig> extractionConfigs, ExtractionAction log, ExtractionAction onEnd) {
        this.extractionConfigs = extractionConfigs;
        this.log = log;
        this.onEnd = onEnd;
        this.stop = false;
    }

    @Override
    public void run() {
        this.log.execute("Lancement de l'extraction");

        // lancement de l'extraction
        for(GithubDirectoryExtractor.ExtractionConfig extractionConfig : this.extractionConfigs) {
            // vérification d'arrêt du thread
            if(this.stop)
                break;

            this.log.execute(String.format("Lancement d'extraction <%s>",extractionConfig.getDirectoryLink()));

            // extraction de la configuration fournie
            if(new GithubDirectoryExtractor(this.log,extractionConfig).extract())
                this.log.execute("Extraction réussi");
            else
                this.log.execute("Echec d'extraction");

            extractionConfig.getOnElementExtracted().execute();
        }

        this.log.execute("Fin de l'extraction");
        this.onEnd.execute();
    }

    /**
     * @brief Lance l'arrêt de l'exécution
     */
    public void stopExecution(){
        this.stop = true;
    }

    /**
     * @return l'utilitaire de log
     */
    public ExtractionAction getLog() {
        return this.log;
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
