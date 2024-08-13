package sabo.yahvya.githubdirectoryextractor.views.views;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sabo.yahvya.githubdirectoryextractor.GithubDirectoryExtractorApplication;

import java.net.URL;

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

    /**
     * @return chemin des fichiers style requis
     */
    public String[] getRequiredStyles();

    /**
     * @brief Ajoute les styles du composant
     * @param vue la vue
     * @param scene scene
     */
    static void addVueRequiredStyles(AppVue vue, Scene scene){
        ObservableList<String> styles = scene.getStylesheets();

        for(String stylesheetPath : vue.getRequiredStyles()){
            if(!styles.contains(stylesheetPath)){
                URL url = GithubDirectoryExtractorApplication.appResourceLoader.getResource(stylesheetPath);

                if(url != null)
                    styles.add(url.toString());
            }
        }
    }
}
