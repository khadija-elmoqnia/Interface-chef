package application.controller;
import application.model.Plat;
import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addPlat {

    @FXML
    private Button addplatb;
    
    @FXML
    private Button back;
    
    @FXML
    private Button sendtoaddpromo;

    @FXML
    private TextField categorie;

    @FXML
    private TextArea desc;

    @FXML
    private TextField newnom;

    @FXML
    private TextField prix;

    

    @FXML
    void addplatbase(ActionEvent event) {
        // Récupérer les valeurs des champs de texte
        String nom = newnom.getText();
        String description = desc.getText();
        String catégorie = categorie.getText();
        Float prixPlat = Float.parseFloat(prix.getText()); // Convertir en Float

        // Créer un nouveau plat
        Plat plat = new Plat();
        plat.setNom_Plat(nom);
        plat.setDescription_Plat(description);
        plat.setCatégorie(catégorie);
        plat.setPrix_Plat(prixPlat);

        // Appeler la méthode pour créer un plat dans la base de données
        boolean success = plat.createPlat();

        // Vérifier si le plat a été ajouté avec succès
        if (success) {
            showAlert("Plat ajouté avec succès !");
            // Réinitialiser les champs de texte après l'ajout du plat
            newnom.clear();
            desc.clear();
            categorie.clear();
            prix.clear();
        } else {
            showAlert("Erreur lors de l'ajout du plat !");
        }
    }

    // Méthode pour afficher une boîte de dialogue avec un message spécifié
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    
    @FXML
    void sendtoaddpromo(ActionEvent event) {

    }
    
    @FXML
    void retourner(ActionEvent event) {
    	   try {
               // Charger le fichier FXML de la nouvelle page
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Platmain.fxml"));
               Parent root = loader.load();
    
               // Créer une nouvelle scène
               Scene scene = new Scene(root);
               
               // Créer une nouvelle fenêtre ou obtenir la fenêtre actuelle
               Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               
               // Afficher la nouvelle scène dans la nouvelle fenêtre
               stage.setScene(scene);
               stage.show();
           } catch (IOException e) {
               e.printStackTrace();
           }
    }
}

