package sabo.yahvya.githubdirectoryextractor;

import javafx.application.Application;
import javafx.stage.Stage;
import sabo.yahvya.githubdirectoryextractor.resources.utils.ResourceLoader;
import sabo.yahvya.githubdirectoryextractor.views.utils.ViewsStack;
import sabo.yahvya.githubdirectoryextractor.views.views.AppVue;
import sabo.yahvya.githubdirectoryextractor.views.views.ErrorVue;
import sabo.yahvya.githubdirectoryextractor.views.views.ExtractionVue;

import java.util.logging.*;

/**
 * @brief Application d'extraction
 */
public class GithubDirectoryExtractorApplication extends Application {
    /**
     * @brief Pile des vues
     */
    protected static ViewsStack viewsStack;

    /**
     * @brief Gestionnaire de chargement de ressource
     */
    public static ResourceLoader<? extends GithubDirectoryExtractorApplication> appResourceLoader;

    /**
     * @brief Gestionnaire de log de l'application
     */
    public static Logger appLogger;

    // initialisation des ressources statiques
    static {
        // pile de vue
        GithubDirectoryExtractorApplication.viewsStack = new ViewsStack();

        // gestionnaires de log
        GithubDirectoryExtractorApplication.appLogger = Logger.getLogger(GithubDirectoryExtractorApplication.class.getName());
        for(Handler h : GithubDirectoryExtractorApplication.getLogHandlers())
            GithubDirectoryExtractorApplication.appLogger.addHandler(h);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        GithubDirectoryExtractorApplication.appLogger.info("Lancement de l'application");

        // initialisation des ressources non statiques
        GithubDirectoryExtractorApplication.appResourceLoader = new ResourceLoader<>(this.getClass());

        // lancement de l'application
        GithubDirectoryExtractorApplication.loadVue(new ExtractionVue(),primaryStage);
    }

    /**
     * @brief Charge une vue
     * @param vue vue
     * @param onStage fenêtre
     * @return si le chargement a réussi
     */
    public static boolean loadVue(AppVue vue,Stage onStage){
        vue.setStage(onStage);

        if(!vue.configStage()){
            GithubDirectoryExtractorApplication.appLogger.warning(String.format("Echec de configuration de la vue <%s>",vue));
            GithubDirectoryExtractorApplication.loadVue(new ErrorVue(),onStage);
            return false;
        }

        if(!vue.loadContent()){
            GithubDirectoryExtractorApplication.appLogger.warning(String.format("Echec de chargement du contenu de la vue <%s>",vue));
            GithubDirectoryExtractorApplication.loadVue(new ErrorVue(),onStage);
            return false;
        }

        if(vue.addOnStackOnLoad())
            GithubDirectoryExtractorApplication.viewsStack.add(vue);

        GithubDirectoryExtractorApplication.appLogger.info(String.format("Vue <%s> chargée",vue));
        return true;
    }

    /**
     *
     * @return liste des gestionnaires de log de l'application
     */
    public static Handler[] getLogHandlers(){
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter(){
            @Override
            public synchronized String format(LogRecord record){
                return this.formatMessage(record) + System.lineSeparator().repeat(2);
            }
        });

        return new Handler[]{
            consoleHandler
        };
    }
}
