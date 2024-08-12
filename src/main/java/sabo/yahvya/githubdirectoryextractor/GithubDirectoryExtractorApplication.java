package sabo.yahvya.githubdirectoryextractor;

import javafx.application.Application;
import javafx.stage.Stage;
import sabo.yahvya.githubdirectoryextractor.views.utils.ViewsStack;
import sabo.yahvya.githubdirectoryextractor.views.views.AppVue;
import sabo.yahvya.githubdirectoryextractor.views.views.ErrorVue;
import sabo.yahvya.githubdirectoryextractor.views.views.ExtractionVue;

/**
 * @brief Application d'extraction
 */
public class GithubDirectoryExtractorApplication extends Application {
    /**
     * @brief Pile des vues
     */
    protected static ViewsStack viewsStack;

    static {
        GithubDirectoryExtractorApplication.viewsStack = new ViewsStack();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage){
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

        if(!vue.configStage(onStage)){
            GithubDirectoryExtractorApplication.loadVue(new ErrorVue(),onStage);
            return false;
        }

        if(!vue.loadContent()){
            GithubDirectoryExtractorApplication.loadVue(new ErrorVue(),onStage);
            return false;
        }

        if(vue.addOnStackOnLoad())
            GithubDirectoryExtractorApplication.viewsStack.add(vue);

        onStage.show();

        return true;
    }
}
