package application.controller;

import application.model.Plat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class updatePlat {
	private Plat plat;
	

    @FXML
    private Button back;

    @FXML
    private TextField newcategorie;

    @FXML
    private TextArea newdesc;

    @FXML
    private TextField newprix;

    @FXML
    private TextField nommod;

    @FXML
    private Button sendtoaddpromo;

    @FXML
    private Button update;
    
    
    
    public void initData(Plat plat) {
        this.plat = plat;
        
        // Utilisez les données du plat pour initialiser les champs de votre vue
        nommod.setText(plat.getNom_Plat());
        newdesc.setText(plat.getCatégorie());
        newcategorie.setText(plat.getDescription_Plat());
        newprix.setText(String.valueOf(plat.getPrix_Plat()));
    }

    @FXML
    void retourner(ActionEvent event) {
        // Récupérer la scène actuelle
        Scene scene = ((Node) event.getSource()).getScene();
        
        // Récupérer la fenêtre parente de la scène
        Stage stage = (Stage) scene.getWindow();
        
        // Fermer la fenêtre actuelle
        stage.close();
    }

    @FXML
    void sendtoaddpromo(ActionEvent event) {

    }


    @FXML
    void updateplat(ActionEvent event) {
        // Récupérer les valeurs des champs de texte
        String nom = nommod.getText();
        String description = newdesc.getText();
        String categorie = newcategorie.getText();
        Float prix = Float.parseFloat(newprix.getText()); // Convertir en Float
        String image = null; // Obtenez l'image à partir de votre interface utilisateur, si nécessaire
        
        // Appeler la méthode pour mettre à jour le plat dans la base de données
        boolean success = plat.updatePlat(nom, description, categorie, prix, image);

        // Vérifier si la mise à jour du plat a été effectuée avec succès
        if (success) {
            System.out.println("Plat mis à jour avec succès !");
            // Afficher un message à l'utilisateur pour indiquer que la mise à jour a réussi
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Mise à jour de plat");
            alert.setHeaderText(null);
            alert.setContentText("Le plat a été mis à jour avec succès !");
            alert.showAndWait();

            // Fermer la fenêtre de mise à jour du plat si nécessaire
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Erreur lors de la mise à jour du plat !");
            // Afficher un message à l'utilisateur pour indiquer que la mise à jour a échoué
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur lors de la mise à jour de plat");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la mise à jour du plat.");
            alert.showAndWait();
        }
    }

    
   

}
