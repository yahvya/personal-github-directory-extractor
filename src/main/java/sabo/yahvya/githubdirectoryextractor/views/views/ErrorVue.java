package sabo.yahvya.githubdirectoryextractor.views.views;

import javafx.stage.Stage;
import sabo.yahvya.githubdirectoryextractor.GithubDirectoryExtractorApplication;
import sabo.yahvya.githubdirectoryextractor.resources.utils.ResourcesPath;
import sabo.yahvya.githubdirectoryextractor.views.components.AppInfoText;

/**
 * @brief Vue d'erreur
 */
public class ErrorVue implements AppVue{
    /**
     * @brief Fenêtre liée
     */
    protected Stage stage;

    /**
     * @brief Si l'application doit être fermée à la validation
     */
    protected boolean closeApp;

    /**
     * @brief Titre de l'erreur
     */
    protected String errorTitle;

    /**
     * @brief Message d'erreur
     */
    protected String errorMessage;

    /**
     * @param closeApp Si l'application doit être fermée à la validation
     * @param errorTitle Titre de l'erreur
     * @param errorMessage Message d'erreur
     */
    public ErrorVue(boolean closeApp,String errorTitle,String errorMessage){
        this.closeApp = closeApp;
        this.errorTitle = errorTitle;
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean addOnStackOnLoad() {
        return false;
    }

    @Override
    public boolean configStage() {
        return true;
    }

    @Override
    public boolean loadContent() {


        return true;
    }

    @Override
    public AppVue setStage(Stage stage) {
        this.stage = stage;

        return this;
    }

    @Override
    public String[] getRequiredStyles() {
        return new String[]{
            ResourcesPath.STYLES_VIEWS_ERROR_TEXT.path
        };
    }
}
