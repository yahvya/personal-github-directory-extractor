package sabo.yahvya.githubdirectoryextractor.views.views;

import javafx.stage.Stage;

/**
 * @brief Vue d'erreur
 */
public class ErrorVue implements AppVue{
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
        return true;
    }

    @Override
    public boolean loadContent() {
        this.stage.show();

        return true;
    }

    @Override
    public AppVue setStage(Stage stage) {
        this.stage = stage;

        return this;
    }
}
