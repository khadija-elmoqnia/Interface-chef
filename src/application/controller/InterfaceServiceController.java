package application.controller;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import application.model.Service;
import java.util.Optional;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class InterfaceServiceController {

    @FXML
    private TextArea DescriptionField;

    @FXML
    private TextField IDField;

    @FXML
    private TextField NomField;

    @FXML
    private TextField PrixField;
    
    @FXML
    private Button promotionid; 

    @FXML
    private TableColumn<Service, String> description;

    @FXML
    private TableColumn<Service, String> id;

    @FXML
    private TableColumn<Service, String> name;

    @FXML
    private TableColumn<Service, String> prix;

    @FXML
    private TableColumn<Service, Void> actionsCol;
    
    @FXML
    private Button enregisterbutton;

    @FXML
    private TableView<Service> servicesTable;

    
   
    private boolean update;
    ObservableList<Service> ServiceList = FXCollections.observableArrayList();
    
    

    @FXML
    void initialize() throws SQLException {
        loadData();
        
    }


  
    @FXML
    void refreshServices() throws SQLException {
        ServiceList.clear();
		ServiceList.addAll(Service.getAllServices());
		servicesTable.setItems(ServiceList);
	
    }


    private void loadData() throws SQLException {
        refreshServices();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        // Création de la colonne personnalisée pour les icônes
        TableColumn<Service, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setPrefWidth(100);

        // Création de la cellule personnalisée
        Callback<TableColumn<Service, Void>, TableCell<Service, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Service, Void> call(final TableColumn<Service, Void> param) {
                final TableCell<Service, Void> cell = new TableCell<>() {
                    private final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/application/images/supprimer.png")));
                    private final ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/application/images/editer.png")));

                    // Affichage des icônes dans la cellule
                    {
                        editIcon.setFitWidth(35);
                        editIcon.setFitHeight(35);

                        deleteIcon.setFitWidth(35);
                        deleteIcon.setFitHeight(35);
                        deleteIcon.setOnMouseClicked(event -> {
                            Service service = getTableView().getItems().get(getIndex());

                            // Alert avant la suppression
                            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmationAlert.setTitle("Confirmation de suppression");
                            confirmationAlert.setHeaderText(null);
                            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce service ?");
                            Optional<ButtonType> result = confirmationAlert.showAndWait();

                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    // Utilisation de la méthode deleteService de la classe Service
                               
                                    service.deleteService();

                                    // Alert après la suppression
                                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                    successAlert.setHeaderText(null);
                                    successAlert.setContentText("Service supprimé avec succès.");
                                    successAlert.showAndWait();

                                    refreshServices();
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    }

                    // Affichage des icônes dans la cellule
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox container = new HBox(5, editIcon, deleteIcon);
                            setGraphic(container);
                        }
                    }
                };
                return cell;
            }
        };

       
        actionsCol.setCellFactory(cellFactory);
        servicesTable.getColumns().add(actionsCol);
    }
    

    @FXML
    void getData(MouseEvent event) {
        Service selectedService = servicesTable.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            NomField.setText(selectedService.getName());
            DescriptionField.setText(selectedService.getDescription());
            PrixField.setText(String.valueOf(selectedService.getPrix()));
            enregisterbutton.setDisable(true);
            
        } 
    }

        
    @FXML
    void save() throws SQLException {
        try {
            String nom = NomField.getText();
            String description = DescriptionField.getText();
            String prix = PrixField.getText();

            if (nom.isEmpty() || description.isEmpty() || prix.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Remplissez tous les champs");
                alert.showAndWait();
            } else {
                double prixValue = Double.parseDouble(prix);
                Service service = new Service(nom, description, prixValue);
                if (!update) {
                    service.createService();
                } else {
                    service.setId(Integer.parseInt(IDField.getText()));
                    service.updateService();
                }
                clear();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setHeaderText(null);
                successAlert.setContentText("Enregistrement effectué avec succès.");
                successAlert.showAndWait();
            }
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Le prix doit être un nombre décimal.");
            alert.showAndWait();
        }
    }

   

    @FXML
    void modifier(MouseEvent event) throws SQLException {
        try {
            Service selectedService = servicesTable.getSelectionModel().getSelectedItem();
            if (selectedService != null) {
                String nom = NomField.getText();
                String description = DescriptionField.getText();
                String prix = PrixField.getText();

                if (nom.isEmpty() || description.isEmpty() || prix.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Remplissez tous les champs");
                    alert.showAndWait();
                } else {
                    double prixValue = Double.parseDouble(prix);
                    selectedService.setName(nom);
                    selectedService.setDescription(description);
                    selectedService.setPrix(prixValue);
                    selectedService.updateService();
                    clear();
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Service modifié avec succès.");
                    successAlert.showAndWait();
                    
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un service à modifier.");
                alert.showAndWait();
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void clear() {
     
        NomField.clear();
        DescriptionField.clear();
        PrixField.clear();
        enregisterbutton.setDisable(false);
    }

    @FXML
    void retourner(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Mainapplication.fxml"));
            Parent root = loader.load();
            Scene scene = enregisterbutton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  


    
}