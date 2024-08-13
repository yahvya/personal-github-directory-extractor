package sabo.yahvya.githubdirectoryextractor.views.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sabo.yahvya.githubdirectoryextractor.GithubDirectoryExtractorApplication;
import sabo.yahvya.githubdirectoryextractor.resources.utils.ResourcesPath;
import sabo.yahvya.githubdirectoryextractor.views.components.AppButtonComponent;
import sabo.yahvya.githubdirectoryextractor.views.components.AppInfoText;
import sabo.yahvya.githubdirectoryextractor.views.components.Component;

/**
 * @brief Vue d'extraction
 */
public class ExtractionVue implements AppVue{
    /**
     * @brief Fenêtre liée
     */
    protected Stage stage;

    @Override
    public boolean addOnStackOnLoad() {
        return true;
    }

    @Override
    public boolean configStage() {
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.setTitle("Github directory extractor");

        return true;
    }

    @Override
    public boolean loadContent() {
        // création de la scène
        VBox container = new VBox(30);
        Scene scene = new Scene(container);

        container.getStyleClass().add("extraction-container");

        // définition des composants

        // titre
        Label title = new Label("Github directory extractor");
        title.getStyleClass().add("extractor-title");

        // instructions
        AppInfoText instructionsComponent = new AppInfoText("Veuillez fournir les liens github des dossiers souhaités.");

        // bouton d'ajout
        AppButtonComponent newButtonComponent = new AppButtonComponent("Nouveau dossier");
        Button newButton = (Button) newButtonComponent.build();

        // zones des éléments
        VBox elementsContainer = new VBox(10);
        ScrollPane elementsContainerScroll = new ScrollPane(elementsContainer);

        elementsContainerScroll.setMaxHeight(150);
        elementsContainerScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        elementsContainerScroll.getStyleClass().add("extraction-elements-container");

        // boutons d'action
        HBox buttonsContainer = new HBox(15);

        AppButtonComponent cancelButtonComponent = new AppButtonComponent("Annuler");
        AppButtonComponent validationButtonComponent = new AppButtonComponent("Valider");
        Button cancelButton = (Button) cancelButtonComponent.build();
        Button validationButton = (Button) validationButtonComponent.build();

        validationButton.setDisable(true);
        buttonsContainer.getChildren().addAll(
            cancelButton,
            validationButton
        );

        // définition de la vue
        container.getChildren().addAll(
            title,
            instructionsComponent.build(),
            newButton,
            elementsContainerScroll,
            buttonsContainer
        );

        // chargement des styles
        GithubDirectoryExtractorApplication.addGlobalStyles(scene);
        AppVue.addVueRequiredStyles(this,scene);
        Component.addComponentRequiredStyles(instructionsComponent,scene);
        Component.addComponentRequiredStyles(cancelButtonComponent,scene);

        this.stage.setMinWidth(800);
        this.stage.setMinHeight(400);
        this.stage.setScene(scene);
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
            ResourcesPath.STYLES_VIEWS_EXTRACTION.path
        };
    }
}
