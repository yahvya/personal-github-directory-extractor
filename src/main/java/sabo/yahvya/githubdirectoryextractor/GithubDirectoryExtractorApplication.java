package sabo.yahvya.githubdirectoryextractor;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @brief Application d'extraction
 */
public class GithubDirectoryExtractorApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Initialisation");
        primaryStage.show();
    }
}
