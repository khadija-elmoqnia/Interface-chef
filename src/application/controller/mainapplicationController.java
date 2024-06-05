package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class mainapplicationController {

    @FXML
    private Button serviceId;
    
    @FXML
    private Button Menuid;
    
    @FXML
    private Button platId;
    
    

    @FXML
    void Servicebtn(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/service_interface.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle interface
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement du bouton
            Stage stage = (Stage) serviceId.getScene().getWindow();

            // Afficher la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void PlatButoon(ActionEvent event) {
    	
    	try {
            // Charger le fichier FXML de la nouvelle interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Platmain.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle interface
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement du bouton
            Stage stage = (Stage) platId.getScene().getWindow();

            // Afficher la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    @FXML
    void Menubtn(ActionEvent event) {
        try {
            // Charger la vue scene1.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/scene1.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) Menuid.getScene().getWindow();

            // Définir la nouvelle scène dans la fenêtre principale
            
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   

    }


