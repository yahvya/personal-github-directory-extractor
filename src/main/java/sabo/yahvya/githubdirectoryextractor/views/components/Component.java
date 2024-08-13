package sabo.yahvya.githubdirectoryextractor.views.components;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;

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
    static void addComponentRequiredStyle(Component component, Scene scene){
        ObservableList<String> styles = scene.getStylesheets();

        for(String stylesheetPath : component.getRequiredStyles()){
            if(!styles.contains(stylesheetPath))
                styles.add(stylesheetPath);
        }
    }
}
