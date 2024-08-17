package sabo.yahvya.githubdirectoryextractor;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sabo.yahvya.githubdirectoryextractor.resources.utils.ResourceLoader;
import sabo.yahvya.githubdirectoryextractor.resources.utils.ResourcesPath;
import sabo.yahvya.githubdirectoryextractor.views.utils.ViewsStack;
import sabo.yahvya.githubdirectoryextractor.views.views.AppVue;
import sabo.yahvya.githubdirectoryextractor.views.views.ErrorVue;
import sabo.yahvya.githubdirectoryextractor.views.views.ExtractionVue;

import java.net.URL;
import java.util.logging.Handler;
import java.util.logging.Logger;

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
        GithubDirectoryExtractorApplication.appResourceLoader = new ResourceLoader<>(GithubDirectoryExtractorApplication.class);

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

        // définition de l'icône
        Image appIconImage = GithubDirectoryExtractorApplication.getAppIcon();

        if(appIconImage != null)
            primaryStage.getIcons().add(appIconImage);

        // lancement de l'application
        Rectangle2D bounds = Screen.getPrimary().getBounds();

        primaryStage.setMaxWidth(bounds.getWidth());
        primaryStage.setMaxHeight(bounds.getHeight());

        if(!GithubDirectoryExtractorApplication.loadVue(new ExtractionVue(),primaryStage))
            GithubDirectoryExtractorApplication.loadVue(new ErrorVue(true,"Erreur","Une erreur s 'est produite lors du chargement de l'application."),primaryStage);
    }

    /**
     * @brief Charge une vue
     * @param vue vue
     * @param onStage fenêtre
     * @return si le chargement a réussi
     */
    public static boolean loadVue(AppVue vue,Stage onStage){
        // configuration de la vue
        vue.setStage(onStage);

        if(!vue.configStage()){
            GithubDirectoryExtractorApplication.appLogger.warning(String.format("Echec de configuration de la vue <%s>",vue));
            return false;
        }

        if(!vue.loadContent()){
            GithubDirectoryExtractorApplication.appLogger.warning(String.format("Echec de chargement du contenu de la vue <%s>",vue));
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
        return new Handler[]{
        };
    }

    /**
     * @brief Fourni l'icône de l'application
     * @return L'icône ou null en cas d'erreur
     */
    public static Image getAppIcon(){
        try{
            GithubDirectoryExtractorApplication.appLogger.info("Chargement d'icône de l'application");

            URL iconUrl = GithubDirectoryExtractorApplication.appResourceLoader.getResource(ResourcesPath.APPLICATION_ICON_PATH.path);

            if(iconUrl == null)
                throw new Exception();

            return new Image(iconUrl.toString());
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * @return listes des css globaux de l'application
     */
    public static URL[] getGlobalStyles(){
        return new URL[]{
            GithubDirectoryExtractorApplication.appResourceLoader.getResource(ResourcesPath.STYLES_GLOBAL_APP.path)
        };
    }

    /**
     * @brief Ajoute les styles globaux sur la scène
     * @param scene scene
     */
    public static void addGlobalStyles(Scene scene){
        GithubDirectoryExtractorApplication.appLogger.info("Ajout des css globaux");

        ObservableList<String> stylesheets = scene.getStylesheets();

        for(URL stylesheetUrl : GithubDirectoryExtractorApplication.getGlobalStyles()){
            if(stylesheetUrl != null)
                stylesheets.add(stylesheetUrl.toString());
        }
    }
}
