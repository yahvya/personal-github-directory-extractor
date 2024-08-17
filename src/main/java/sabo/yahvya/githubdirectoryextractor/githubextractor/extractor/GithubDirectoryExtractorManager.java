package sabo.yahvya.githubdirectoryextractor.githubextractor.extractor;

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
    public GithubDirectoryExtractorManager(ArrayList<GithubDirectoryExtractor.ExtractionConfig> extractionConfigs,ExtractionAction log, ExtractionAction onEnd) {
        this.extractionConfigs = extractionConfigs;
        this.log = log;
        this.onEnd = onEnd;
        this.stop = false;
    }

    @Override
    public void run() {
        try{
            this.log.execute("Lancement de l'extraction");

            // lancement de l'extraction
            for(GithubDirectoryExtractor.ExtractionConfig extractionConfig : this.extractionConfigs) {
                // vérification d'arrêt du thread
                if(this.stop)
                    break;

                this.log.execute(String.format("Lancement d'extraction <%s>",extractionConfig.getDirectoryLink()));

                // extraction de la configuration fournie
                if(!new GithubDirectoryExtractor(this.log,extractionConfig).extract()){
                    this.onEnd.execute();
                    return;
                }

                this.log.execute("Extraction réussi");
                extractionConfig.getOnElementExtracted().execute();
            }

            this.log.execute("Fin de l'extraction");
            this.onEnd.execute();
        }
        catch(Exception ignore){
            this.log.execute("Une erreur s'est produite lors de l'extraction");
            this.onEnd.execute();
        }
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
