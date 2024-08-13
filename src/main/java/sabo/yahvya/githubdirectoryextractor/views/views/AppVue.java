package sabo.yahvya.githubdirectoryextractor.views.views;

import javafx.stage.Stage;

/**
 * @brief Descripteur de vue de l'application
 */
public interface AppVue {
    /**
     * @return Si la vue doit être ajoutée dans la pile des vues
     */
    public boolean addOnStackOnLoad();

    /**
     * @brief Configure la fenêtre
     * @return Si la configuration réussi
     */
    public boolean configStage();

    /**
     * @brief Charge de contenu stage
     * @return si le chargement réussi
     */
    public boolean loadContent();

    /**
     * @brief Stocke la fenêtre
     * @param stage fenêtre
     * @return this
     */
    public AppVue setStage(Stage stage);
}
