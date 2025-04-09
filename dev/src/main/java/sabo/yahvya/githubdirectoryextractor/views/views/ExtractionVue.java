package sabo.yahvya.githubdirectoryextractor.views.views;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sabo.yahvya.githubdirectoryextractor.GithubDirectoryExtractorApplication;
import sabo.yahvya.githubdirectoryextractor.githubextractor.extractor.GithubDirectoryExtractor;
import sabo.yahvya.githubdirectoryextractor.githubextractor.extractor.GithubDirectoryExtractorManager;
import sabo.yahvya.githubdirectoryextractor.resources.utils.ResourcesPath;
import sabo.yahvya.githubdirectoryextractor.views.components.AppButtonComponent;
import sabo.yahvya.githubdirectoryextractor.views.components.AppInfoText;
import sabo.yahvya.githubdirectoryextractor.views.components.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @brief Vue d'extraction
 */
public class ExtractionVue implements AppVue{
    /**
     * @brief Hauteur de fenêtre
     */
    protected static final int height = 400;

    /**
     * @brief Largeur de fenêtre
     */
    protected static final int width = 800;

    /**
     * @brief Fenêtre liée
     */
    protected Stage stage;

    /**
     * @brief Si une extraction est en cours
     */
    protected boolean isExtracting;

    /**
     * @brief Bouton de validation
     */
    protected Button validationButton;

    /**
     * @brief Eléments à charger
     */
    protected ObservableList<Node> toLoadElements;

    /**
     * @brief Ligne de log
     */
    protected Label logLine;

    /**
     * @brief Choix de dossier
     */
    protected DirectoryChooser directoryChooser;

    /**
     * @brief Map de configuration de destination
     */
    protected HashMap<HBox,String> directoriesMap;

    /**
     * @brief Gestionnaire d'extraction
     */
    protected GithubDirectoryExtractorManager extractorManager;

    public ExtractionVue(){
        this.isExtracting = false;
        this.directoryChooser = new DirectoryChooser();
        this.directoriesMap = new HashMap<>();
        this.directoryChooser.setTitle("Destination dossier github");
        this.extractorManager = null;
    }

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
        AppInfoText appInstruction = new AppInfoText("Veuillez fournir les liens github des dossiers souhaités (limité aux repos publiques).");

        // bouton d'ajout
        AppButtonComponent newButtonComponent = new AppButtonComponent("Nouveau dossier");
        Button newButton = (Button) newButtonComponent.build();

        // zones des éléments
        VBox elementsContainer = new VBox(20);
        ScrollPane elementsContainerScroll = new ScrollPane(elementsContainer);

        elementsContainerScroll.setPrefHeight(200);
        elementsContainerScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        elementsContainerScroll.getStyleClass().add("extraction-elements-container");

        this.toLoadElements = elementsContainer.getChildren();

        // boutons d'action
        HBox buttonsContainer = new HBox(15);

        AppButtonComponent cancelButtonComponent = new AppButtonComponent("Annuler");
        AppButtonComponent validationButtonComponent = new AppButtonComponent("Lancer");
        Button cancelButton = (Button) cancelButtonComponent.build();
        this.validationButton = (Button) validationButtonComponent.build();

        this.validationButton.setDisable(true);
        buttonsContainer.getChildren().addAll(
            cancelButton,
            this.validationButton
        );

        // ligne de log
        this.logLine = new Label("En attente d'action");
        this.logLine.getStyleClass().add("extraction-log-line");
        this.logLine.setTooltip(new Tooltip("Appuyez pour copier"));
        this.logLine.setOnMouseClicked((e) -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(this.logLine.getText());

            Clipboard
                .getSystemClipboard()
                .setContent(content);
        });

        // définition de la vue
        container.getChildren().addAll(
            title,
            appInstruction.build(),
            newButton,
            elementsContainerScroll,
            buttonsContainer,
            this.logLine
        );

        // chargement des styles
        GithubDirectoryExtractorApplication.addGlobalStyles(scene);
        AppVue.addVueRequiredStyles(this,scene);
        Component.addComponentRequiredStyles(appInstruction,scene);
        Component.addComponentRequiredStyles(cancelButtonComponent,scene);

        // ajout des évènements
        newButton.setOnMouseClicked((e) -> {
            if(this.isExtracting)
                return;

            this.addNewConfiguration();
        });

        // validation et démarrage d'extraction
        this.validationButton.setOnMouseClicked((e) -> {
            if(this.isExtracting)
                return;
            this.startExtraction();
        });

        // annulation d'action
        cancelButton.setOnMouseClicked((e) -> {
            if(this.isExtracting){
                this.stopExtraction();
                return;
            }

            this.stage.close();
        });

        this.stage.setMinWidth(ExtractionVue.width);
        this.stage.setMinHeight(ExtractionVue.height);
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

    /**
     * @brief Ajoute une nouvelle ligne de configuration
     */
    public void addNewConfiguration(){
        HBox configurationLine = new HBox(15);

        // champs de saisie
        TextField uriField = new TextField();
        uriField.setPromptText("Entrez le lien du dossier github");
        uriField.getStyleClass().add("extraction-uri-field");
        uriField.setPrefSize(ExtractionVue.width * 0.65,45);

        // bouton de suppression et choix de dossier
        Button removeButton = (Button) new AppButtonComponent("Retirer").build();
        Button directoryChooserButton = (Button) new AppButtonComponent("Destination").build();

        removeButton.setPrefHeight(43);
        directoryChooserButton.setPrefHeight(43);

        // définition de la ligne
        configurationLine.setAlignment(Pos.CENTER_LEFT);
        configurationLine.getChildren().addAll(
            uriField,
            directoryChooserButton,
            removeButton
        );

        // évènement de suppression
        removeButton.setOnMouseClicked((e) -> {
            if(this.isExtracting)
                return;

            this.toLoadElements.remove(configurationLine);
            this.directoriesMap.remove(configurationLine);

            if(this.directoriesMap.isEmpty())
                this.validationButton.setDisable(true);
        });

        // choix du dossier de destination
        Tooltip buttonTooltip = new Tooltip("Veuillez choisir un dossier (créez le dossier de destination)");

        directoryChooserButton.setTooltip(buttonTooltip);
        directoryChooserButton.setOnMouseClicked((e) -> {
            File chosenDirectory = this.directoryChooser.showDialog(this.stage);

            if(chosenDirectory == null)
                return;

            String path = chosenDirectory.getAbsolutePath();

            buttonTooltip.setText(path);
            this.directoriesMap.put(configurationLine,path);

            if(this.validationButton.isDisabled())
                this.validationButton.setDisable(false);
        });

        this.toLoadElements.addFirst(configurationLine);
    }

    /**
     * @brief Lance l'extraction
     */
    protected void startExtraction(){
        GithubDirectoryExtractorApplication.appLogger.info("Lancement d'extraction");

        this.isExtracting = true;
        validationButton.setText("Extraction en cours ...");

        // construction des configurations
        ArrayList<GithubDirectoryExtractor.ExtractionConfig> extractionConfigs = new ArrayList<>();

        this.directoriesMap.forEach((configLine,dirPath) -> {
            // récupération du lien
            TextField directoryLinkField = (TextField) configLine.getChildren().getFirst();
            String githubDirectoryLink = directoryLinkField.getText();

            extractionConfigs.add(new GithubDirectoryExtractor.ExtractionConfig(
                githubDirectoryLink,
                dirPath,
                (args) -> Platform.runLater(() -> {
                    GithubDirectoryExtractorApplication.appLogger.info(String.format("Extraction du lien <%s> faite", githubDirectoryLink));

                    // suppression dans l'interface de l'élément extrait
                    this.toLoadElements.remove(configLine);
                    this.directoriesMap.remove(configLine);
                })
            ));
        });

        // lancement de l'extraction
        this.extractorManager = new GithubDirectoryExtractorManager(
            extractionConfigs,
            (args) -> {
                String logLine = (String) args[0];
                Platform.runLater(() -> this.logLine.setText(logLine));
            },
            (args) -> Platform.runLater(this::stopExtraction)
        );

        try{
            this.extractorManager.start();
        }
        catch(Throwable e){
            GithubDirectoryExtractorApplication.appLogger.warning("Echec de lancement de l'extracteur");
            this.stopExtraction();
        }
    }

    /**
     * @brief Stoppe le processus d'extraction
     */
    protected void stopExtraction(){
        this.isExtracting = false;
        this.validationButton.setText("Lancer");
        GithubDirectoryExtractorApplication.appLogger.info("Arrêt demandé d'extraction");

        if(this.directoriesMap.isEmpty())
            this.validationButton.setDisable(true);

        if(this.extractorManager == null)
            return;

        this.extractorManager.stopExecution();
        this.extractorManager = null;
    }
}
