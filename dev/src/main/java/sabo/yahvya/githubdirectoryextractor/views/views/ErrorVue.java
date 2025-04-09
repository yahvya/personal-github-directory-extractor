package sabo.yahvya.githubdirectoryextractor.views.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sabo.yahvya.githubdirectoryextractor.GithubDirectoryExtractorApplication;
import sabo.yahvya.githubdirectoryextractor.resources.utils.ResourcesPath;
import sabo.yahvya.githubdirectoryextractor.views.components.AppButtonComponent;
import sabo.yahvya.githubdirectoryextractor.views.components.AppInfoText;
import sabo.yahvya.githubdirectoryextractor.views.components.Component;

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
        this.stage.initStyle(StageStyle.UNDECORATED);

        return true;
    }

    @Override
    public boolean loadContent() {
        final double stageWidth = 500;
        final double stageHeight = 250;

        // éléments de scène
        VBox container = new VBox(15);
        Scene scene = new Scene(container);

        // définition des composants

        AppButtonComponent validationButtonComponent = new AppButtonComponent("Ok");
        AppInfoText infoTextComponent = new AppInfoText(this.errorMessage);
        Label titleLabel = new Label(this.errorTitle);

        titleLabel.getStyleClass().add("error-title");

        // placement des éléments

        Button validationButton = (Button) validationButtonComponent.build();
        Label infoText = (Label) infoTextComponent.build();

        infoText.setMaxWidth(stageWidth * 0.85);

        container
            .getChildren()
            .addAll(titleLabel,infoText,validationButton);

        // ajout des évènements

        if(this.closeApp)
            validationButton.setOnMouseClicked((e) -> this.stage.close());

        // ajout des styles

        GithubDirectoryExtractorApplication.addGlobalStyles(scene);
        AppVue.addVueRequiredStyles(this,scene);
        Component.addComponentRequiredStyles(validationButtonComponent,scene);
        Component.addComponentRequiredStyles(infoTextComponent,scene);

        // affectation de la scène

        this.stage.setWidth(stageWidth);
        this.stage.setScene(scene);
        this.stage.centerOnScreen();
        this.stage.show();

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
