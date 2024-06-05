package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML
            AnchorPane root = FXMLLoader.load(getClass().getResource("/application/view/Mainapplication.fxml"));

            // Créer une nouvelle scène avec le conteneur racine
            Scene scene = new Scene(root ,979,592);

            // Ajouter une feuille de style à la scène si nécessaire
            scene.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());

            // Définir la scène sur la fenêtre principale et afficher la fenêtre
            primaryStage.setScene(scene);
   
           
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) { 
        launch(args);
    }
}
