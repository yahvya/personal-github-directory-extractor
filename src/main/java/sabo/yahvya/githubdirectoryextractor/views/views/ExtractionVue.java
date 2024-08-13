package sabo.yahvya.githubdirectoryextractor.views.views;

import javafx.stage.Stage;
import sabo.yahvya.githubdirectoryextractor.resources.utils.ResourcesPath;

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
        return false;
    }

    @Override
    public boolean configStage() {
        return false;
    }

    @Override
    public boolean loadContent() {
        return false;
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
