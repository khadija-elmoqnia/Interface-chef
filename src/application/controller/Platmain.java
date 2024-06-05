package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.List;

import application.model.Plat;
import application.model.Promotion;
import javafx.scene.Node;


public class Platmain {
    
	 @FXML
	    private Button refresh;
	 
	 @FXML
	    private Button addplat;
	 
    @FXML
    private TableColumn<Plat, String> columnNamplat;

    @FXML
    private TableColumn<Plat, String> columncategori;

    @FXML
    private TableColumn<Plat, String> columndesc;

    @FXML
    private TableColumn<Plat, Integer> columnprix;

    @FXML
    private TableColumn<Plat,List<Promotion> > columnspromo;
    
    @FXML
    private TableColumn<Plat,String> columnmodifier;
    
    @FXML
    private TableColumn<Plat, String> columnsupprimer;

    @FXML
    private TableView<Plat> table;
    
    
    @FXML
    private void refresh(ActionEvent event) {
    	initialize();
    }
    
    @FXML
    private void initialize() {
        // Obtenir tous les plats à partir de la méthode getAllPlats() de la classe Plat
        List<Plat> platList = Plat.getAllPlats();

        // Effacer les éléments actuels de la TableView
        table.getItems().clear();

        // Ajouter tous les plats à la TableView
        table.getItems().addAll(platList);

        // Définir les valeurs des cellules pour chaque colonne
        columnNamplat.setCellValueFactory(new PropertyValueFactory<>("Nom_Plat"));
        columncategori.setCellValueFactory(new PropertyValueFactory<>("Catégorie"));
        columndesc.setCellValueFactory(new PropertyValueFactory<>("Description_Plat"));
        columnprix.setCellValueFactory(new PropertyValueFactory<>("Prix_Plat"));

        // Définition de la cellule personnalisée pour la colonne de modification
        columnmodifier.setCellFactory(new Callback<TableColumn<Plat, String>, TableCell<Plat, String>>() {
            @Override
            public TableCell<Plat, String> call(TableColumn<Plat, String> param) {
                return new TableCell<Plat, String>() {
                    final Button button = new Button("Modifier");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            // Action à effectuer lors du clic sur le bouton de modification
                            Plat plat = getTableView().getItems().get(getIndex());
                            // Charger la vue de modification et passer les données du plat sélectionné
                            loadUpdatePlatView(plat);
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            }
        });
        

     // Définition de la cellule personnalisée pour la colonne de suppression
        columnsupprimer.setCellFactory(new Callback<TableColumn<Plat, String>, TableCell<Plat, String>>() {
            @Override
            public TableCell<Plat, String> call(TableColumn<Plat, String> param) {
                return new TableCell<Plat, String>() {
                    final Button button = new Button("Supprimer");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            // Afficher une boîte de dialogue de confirmation
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation de suppression");
                            alert.setHeaderText(null);
                            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce plat ?");

                            // Récupérer la réponse de l'utilisateur
                            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

                            // Si l'utilisateur a confirmé la suppression, procéder à la suppression
                            if (result == ButtonType.OK) {
                                Plat plat = getTableView().getItems().get(getIndex());
                                boolean success = plat.deletePlat();
                                
                                // Afficher un message après la suppression
                                Alert resultAlert = new Alert(AlertType.INFORMATION);
                                resultAlert.setTitle("Suppression de plat");
                                resultAlert.setHeaderText(null);
                                if (success) {
                                    resultAlert.setContentText("Le plat a été supprimé avec succès !");
                                } else {
                                    resultAlert.setContentText("Échec de la suppression du plat.");
                                }
                                resultAlert.showAndWait();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            }
        });

     // Définir la cellule personnalisée pour la colonne des promotions
        columnspromo.setCellFactory(new Callback<TableColumn<Plat, List<Promotion>>, TableCell<Plat, List<Promotion>>>() {
            @Override
            public TableCell<Plat, List<Promotion>> call(TableColumn<Plat, List<Promotion>> param) {
                return new TableCell<Plat, List<Promotion>>() {
                    final Button button = new Button("Promotions");

                    {
                    	button.setOnAction((ActionEvent event) -> {
                    	    Plat plat = getTableView().getItems().get(getIndex());
                    	    Promotion newPromotion = new Promotion();
                    	    newPromotion.setIdPlat(plat.getIdPlat());
                    	    
                    	    // Fermer la fenêtre parent
                    	    ((Stage) button.getScene().getWindow()).close();
                    	    
                    	    // Afficher la vue des promotions associées à ce plat
                    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/promotion.fxml"));
                    	    Parent root;
                    	    try {
                    	        root = loader.load();
                    	        PromotionController controller = loader.getController();
                    	        controller.initData(plat); // Passer le plat sélectionné au contrôleur des promotions
                    	        controller.showPromotionsForPlat(plat.getIdPlat()); // Afficher les promotions pour ce plat
                    	        Stage stage = new Stage();
                    	        stage.setScene(new Scene(root));
                    	        stage.show();
                    	    } catch (IOException | SQLException e) {
                    	        e.printStackTrace();
                    	    }
                    	});
                    }


                    @Override
                    protected void updateItem(List<Promotion> item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            }
        });
    }
    
    
 // Méthode pour charger la vue de modification
    private void loadUpdatePlatView(Plat plat) {
        try {
            // Charger le fichier FXML de la vue de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/updatePlat.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la vue de modification
            updatePlat controller = loader.getController();

            // Passer les données du plat sélectionné au contrôleur de la vue de modification
            controller.initData(plat);

            // Afficher la nouvelle vue dans une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void addplat(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/addPlat.fxml"));
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
    
    
    @FXML
    void retourner(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Mainapplication.fxml"));
            Parent root = loader.load();
            Scene scene = addplat.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}