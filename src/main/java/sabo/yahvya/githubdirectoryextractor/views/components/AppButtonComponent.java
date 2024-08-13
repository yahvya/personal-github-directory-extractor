package sabo.yahvya.githubdirectoryextractor.views.components;

import javafx.scene.Node;
import javafx.scene.control.Button;
import sabo.yahvya.githubdirectoryextractor.resources.utils.ResourcesPath;

/**
 * @brief Bouton de l'application
 */
public class AppButtonComponent implements Component{
    /**
     * @brief Texte du bouton
     */
    protected String text;

    /**
     * @brief Bouton du composant
     */
    protected Button button;

    /**
     * @param text Texte du bouton
     */
    public AppButtonComponent(String text){
        this.text = text;
    }

    @Override
    public Node build() {
        this.button = new Button(this.text);
        this.button.getStyleClass().add("app-button-component");

        return this.button;
    }

    @Override
    public double getWidth() {
        return this.button.getWidth();
    }

    @Override
    public double getHeight() {
        return this.button.getHeight();
    }

    @Override
    public String[] getRequiredStyles(){
        return new String[]{
            ResourcesPath.STYLES_COMPONENTS_APP_BUTTON.path
        };
    }
}
