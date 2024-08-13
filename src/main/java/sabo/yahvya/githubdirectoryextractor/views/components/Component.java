package sabo.yahvya.githubdirectoryextractor.views.components;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import sabo.yahvya.githubdirectoryextractor.GithubDirectoryExtractorApplication;

import java.net.URL;

/**
 * @brief Description des composants
 */
public interface Component {
    /**
     * @return le composant
     */
    public Node build();

    /**
     * @return la largeur du composant
     */
    public double getWidth();

    /**
     * @return la hauteur du composant
     */
    public double getHeight();

    /**
     * @return chemin des fichiers style requis
     */
    public String[] getRequiredStyles();

    /**
     * @brief Ajoute les styles du composant
     * @param component composant
     * @param scene scene
     */
    static void addComponentRequiredStyles(Component component, Scene scene){
        ObservableList<String> styles = scene.getStylesheets();

        for(String stylesheetPath : component.getRequiredStyles()){
            if(!styles.contains(stylesheetPath)){
                URL url = GithubDirectoryExtractorApplication.appResourceLoader.getResource(stylesheetPath);

                if(url != null)
                    styles.add(url.toString());
            }
        }
    }
}
