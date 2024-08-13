package sabo.yahvya.githubdirectoryextractor.views.views;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sabo.yahvya.githubdirectoryextractor.views.components.Component;

/**
 * @brief Vue de test des composants
 */
public class ComponentTestVue implements AppVue{
    /**
     * @brief Stage
     */
    protected Stage stage;

    /**
     * @brief Composant à afficher
     */
    protected Component component;

    /**
     * @param component composant
     */
    public ComponentTestVue(Component component){
        this.component = component;
    }

    @Override
    public boolean addOnStackOnLoad() {
        return false;
    }

    @Override
    public boolean configStage() {
        this.stage.setTitle("Test de composant");
        return true;
    }

    @Override
    public boolean loadContent() {
        HBox hbox = new HBox();
        Scene scene = new Scene(hbox);

        hbox.getChildren().add(this.component.build());

        Component.addComponentRequiredStyle(this.component,scene);

        this.stage.setScene(scene);
        this.stage.setMaximized(true);
        this.stage.show();

        return true;
    }

    @Override
    public AppVue setStage(Stage stage) {
        this.stage = stage;

        return this;
    }
}
