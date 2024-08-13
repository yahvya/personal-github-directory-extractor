package sabo.yahvya.githubdirectoryextractor.views.components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import sabo.yahvya.githubdirectoryextractor.resources.utils.ResourcesPath;

/**
 * @brief Texte d'information
 */
public class AppInfoText implements Component{
    /**
     * @brief Texte d'information
     */
    protected String text;

    /**
     * @brief Label d'affichage
     */
    protected Label label;

    /**
     * @param text Texte d'information
     */
    public AppInfoText(String text) {
        this.text = text;
    }

    @Override
    public Node build() {
        this.label = new Label(text);
        this.label.getStyleClass().add("info-text-component");
        this.label.setWrapText(true);

        return this.label;
    }

    @Override
    public double getWidth() {
        return this.label.getWidth();
    }

    @Override
    public double getHeight() {
        return this.label.getHeight();
    }

    @Override
    public String[] getRequiredStyles() {
        return new String[]{
            ResourcesPath.STYLES_COMPONENTS_INFO_TEXT.path
        };
    }
}
