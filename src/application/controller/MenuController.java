package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import application.model.DatabaseConnection;
import application.model.Menu;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML private TableView<Menu> tableMenu;
    @FXML private TableColumn<Menu, Number> IdMenuCol;
    @FXML private TableColumn<Menu, String> NomMenuCol;
    @FXML private TableColumn<Menu, String> DescriptionMenuCol;
    @FXML private TextField txtNom;
    @FXML private TextField txtDescription;
    @FXML private Button btnAjouter, btnModifier, btnSupprimer, btnConsulter;

    private Connection con;

    @FXML
    void Ajouter(ActionEvent event) {
        try {
            Menu.ajouterMenu(txtNom.getText(), txtDescription.getText(), con);
            refreshTable();
            clearFields();
        } catch (Exception ex) {
            showAlert("Error", "An error occurred while adding the menu: " + ex.getMessage());
        }
    }

    @FXML
    void Modifier(ActionEvent event) {
        Menu selectedMenu = tableMenu.getSelectionModel().getSelectedItem();
        if (selectedMenu != null) {
            try {
                Menu.modifierMenu(selectedMenu.getIdMenu(), txtNom.getText(), txtDescription.getText(), con);
                refreshTable();
                clearFields();
            } catch (Exception ex) {
                showAlert("Error", "An error occurred while modifying the menu: " + ex.getMessage());
            }
        } else {
            showAlert("Selection Error", "Please select a menu to modify.");
        }
    }

    @FXML
    void Supprimer(ActionEvent event) {
        Menu selectedMenu = tableMenu.getSelectionModel().getSelectedItem();
        if (selectedMenu != null) {
            try {
                Menu.supprimerMenu(selectedMenu.getIdMenu(), con);
                refreshTable();
                clearFields();
            } catch (Exception ex) {
                showAlert("Error", "An error occurred while deleting the menu: " + ex.getMessage());
            }
        } else {
            showAlert("Selection Error", "Please select a menu to delete.");
        }
    }

    private void refreshTable() {
        try {
            tableMenu.setItems(Menu.loadMenuData(con));
        } catch (Exception ex) {
            showAlert("Error", "Failed to load menu data: " + ex.getMessage());
        }
    }

    private void clearFields() {
        txtNom.clear();
        txtDescription.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IdMenuCol.setCellValueFactory(new PropertyValueFactory<>("idMenu"));
        NomMenuCol.setCellValueFactory(new PropertyValueFactory<>("nomMenu"));
        DescriptionMenuCol.setCellValueFactory(new PropertyValueFactory<>("descriptionMenu"));
        try {
            con = DatabaseConnection.getConnection(); 
            refreshTable();
        } catch (Exception ex) {
            showAlert("Connection Error", "Unable to connect to the database: " + ex.getMessage());
        }
        tableMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtNom.setText(newSelection.getNomMenu());
                txtDescription.setText(newSelection.getDescriptionMenu());
            }
        });
    }
    
    @FXML
    private void Consulter(ActionEvent event) {
        Menu selectedMenu = tableMenu.getSelectionModel().getSelectedItem();
        if (selectedMenu != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/scene2.fxml"));
                Parent root = loader.load();

                // Get the controller for scene2
                Scene2Controller controller = loader.getController();
                controller.setConnection(this.con);  // Pass the connection to Scene2Controller
                controller.initData(selectedMenu);   // Initialize data with selected menu

                // Show scene2
                Stage stage = new Stage();
                stage.setTitle("Détails du Menu");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Erreur", "Aucun menu sélectionné!");
        }
    }
    
    @FXML
    void retourner(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Mainapplication.fxml"));
            Parent root = loader.load();
            Scene scene = btnAjouter.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
