package application.controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import application.model.Promotion;
import application.model.DatabaseConnection;
import application.model.Plat;

public class PromotionController  {
	
		private Plat selectedPlat;

	    @FXML	
	    private TableColumn<Promotion, String> colNompl;

	    @FXML
	    private TableColumn<Promotion, String> colcodepromo;

	    @FXML
	    private TableColumn<Promotion, Date> coldtdeb;

	    @FXML
	    private TableColumn<Promotion, Date> coldtfin;

	    @FXML
	    private TableColumn<Promotion, Float> colpourprix;

	    @FXML
	    private TableColumn<Promotion, Float> colprixorg;
	    
	    @FXML
	    private TableColumn<Promotion, Float> colnouvprix;

	    @FXML
	    private Button button1;

	    @FXML
	    private Button button2;

	    @FXML
	    private Button button3;
	    

	    @FXML
	    private ButtonBar buttonbar;

	    @FXML
	    private TextField fcodeprom;

	    @FXML
	    private DatePicker fdatedeb;

	    @FXML
	    private DatePicker fdatefin;

	    @FXML
	    private Pane form;

	    @FXML
	    private TextField fpourprom;


	    @FXML
	    private TableView<Promotion> table;
	    
	  


    PreparedStatement st = null;
    ResultSet rs = null;

    public void initialize() {
        try {
        	getAllPromotions();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      

    }
    public void initData(Plat plat) {
        selectedPlat = plat; 
        try {
            showPromotionsForPlat(plat.getIdPlat()); 
        } catch (SQLException e) {
            e.printStackTrace();
           
        }
    }

    private ObservableList<Promotion> getPromotionsForPlat(int platId) throws SQLException {
        ObservableList<Promotion> promotions = FXCollections.observableArrayList();
        String query = "SELECT * FROM Promotion WHERE IdPlat = ?";
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, platId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setIdPromo(rs.getInt("IdPromo"));
                promotion.setDateDebut(rs.getDate("DateDebut"));
                promotion.setDateFin(rs.getDate("DateFin"));
                promotion.setPourcentageReduction(rs.getFloat("PourcentageReduction"));
                promotion.setCodePromo(rs.getString("CodePromo"));
                promotion.setNouvPrix(rs.getFloat("NouvPrix"));
                promotion.setIdPlat(rs.getInt("IdPlat"));
                promotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; 
        }
        return promotions;
    }

    public void showPromotionsForPlat(int platId) throws SQLException {
        ObservableList<Promotion> list = FXCollections.observableArrayList();
        String query = "SELECT promotion.*, plat.NomPlat, plat.PrixPlat " +
                       "FROM promotion " +
                       "JOIN plat ON promotion.IdPlat = plat.IdPlat " +
                       "WHERE promotion.IdPlat = ?";
        Connection con = DatabaseConnection.getConnection();
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, platId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setIdPromo(rs.getInt("IdPromo"));
                promotion.setDateDebut(rs.getDate("DateDebut"));
                promotion.setDateFin(rs.getDate("DateFin"));
                promotion.setPourcentageReduction(rs.getFloat("PourcentageReduction"));
                promotion.setCodePromo(rs.getString("CodePromo"));
                promotion.setIdPlat(rs.getInt("IdPlat"));
                promotion.setNomPlat(rs.getString("NomPlat"));
                promotion.setPrixOriginal(rs.getFloat("PrixPlat"));
                promotion.setNouvPrix(rs.getFloat("NouvPrix"));
                list.add(promotion);   
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; 
        }
        table.setItems(list);
    }
    
    

    public void getAllPromotions() throws SQLException {
        ObservableList<Promotion> list =  getPromotionsForPlat(0);
        table.setItems(list);
        colNompl.setCellValueFactory(new PropertyValueFactory<Promotion, String>("NomPlat"));
        coldtdeb.setCellValueFactory(new PropertyValueFactory<Promotion, Date>("DateDebut"));
        coldtfin.setCellValueFactory(new PropertyValueFactory<Promotion, Date>("DateFin"));
        colpourprix.setCellValueFactory(new PropertyValueFactory<Promotion, Float>("PourcentageReduction"));
        colprixorg.setCellValueFactory(new PropertyValueFactory<Promotion, Float>("PrixOriginal"));
        colcodepromo.setCellValueFactory(new PropertyValueFactory<Promotion, String>("CodePromo"));
        colnouvprix.setCellValueFactory(new PropertyValueFactory<Promotion, Float>("NouvPrix"));
    }
    
    @FXML
    public void refresh(MouseEvent event) {
        try {
        	int idPlat = selectedPlat.getIdPlat();
          
            if (selectedPlat != null) {
                ObservableList<Promotion> promotions = FXCollections.observableArrayList();
                String query = "SELECT promotion.*, plat.NomPlat, plat.PrixPlat " +
                               "FROM promotion " +
                               "JOIN plat ON promotion.IdPlat = plat.IdPlat " +
                               "WHERE promotion.IdPlat = ?";
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement st = con.prepareStatement(query);
                st.setInt(1, selectedPlat.getIdPlat());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Promotion promotion = new Promotion();
                    promotion.setIdPromo(rs.getInt("IdPromo"));
                    promotion.setDateDebut(rs.getDate("DateDebut"));
                    promotion.setDateFin(rs.getDate("DateFin"));
                    promotion.setPourcentageReduction(rs.getFloat("PourcentageReduction"));
                    promotion.setCodePromo(rs.getString("CodePromo"));
                    promotion.setIdPlat(rs.getInt("IdPlat"));
                    promotion.setNomPlat(rs.getString("NomPlat"));
                    promotion.setPrixOriginal(rs.getFloat("PrixPlat"));
                    promotion.setNouvPrix(rs.getFloat("NouvPrix"));
                    promotions.add(promotion);
                }
                table.setItems(promotions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    void getData(MouseEvent event) {
        Promotion promotion = table.getSelectionModel().getSelectedItem();


        java.util.Date dateDebutUtil = promotion.getDateDebut();
        java.sql.Date dateDebutSQL = new java.sql.Date(dateDebutUtil.getTime());
        LocalDate dateDebut = dateDebutSQL.toLocalDate();
        fdatedeb.setValue(dateDebut);


        java.util.Date dateFinUtil = promotion.getDateFin();
        java.sql.Date dateFinSQL = new java.sql.Date(dateFinUtil.getTime());
        LocalDate dateFin = dateFinSQL.toLocalDate();
        fdatefin.setValue(dateFin);


        fpourprom.setText(String.valueOf(promotion.getPourcentageReduction()));
        fcodeprom.setText(String.valueOf(promotion.getCodePromo()));
        button1.setDisable(true);
    }
    
    void clear(){
        fdatedeb.setValue(null);
        fdatefin.setValue(null);


        fpourprom.setText(null);
        fcodeprom.setText(null);
        button1.setDisable(false);

    }
    @FXML
    void effacer(ActionEvent event) {
        clear();
    }

    @FXML
    void retourner(MouseEvent event) {
        // Récupérer la fenêtre principale à partir de l'événement
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        try {
            // Charger le fichier FXML de la page d'accueil
            Parent root = FXMLLoader.load(getClass().getResource("/application/view/Platmain.fxml"));

            // Remplacer la scène actuelle par la nouvelle scène avec la vue de la page d'accueil
            Scene scene = new Scene(root, 979,592);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void createPromotion(ActionEvent event) throws SQLException {
        String insert = "INSERT INTO promotion (DateDebut, DateFin, PourcentageReduction, CodePromo, NouvPrix, IdPlat) VALUES (?, ?, ?, ?, ?,?)";
        Connection con = DatabaseConnection.getConnection();
        try {
            st = con.prepareStatement(insert);
            st.setDate(1, java.sql.Date.valueOf(fdatedeb.getValue()));
            st.setDate(2, java.sql.Date.valueOf(fdatefin.getValue()));

            // Vérifier que les champs ne sont pas vides
            if (!fpourprom.getText().isEmpty() && !fcodeprom.getText().isEmpty()) {
                float pourcentageReduction = Float.parseFloat(fpourprom.getText());
                String codePromo = String.valueOf(fcodeprom.getText()); 
                
                // Récupérer l'ID du plat sélectionné
                int idPlat = selectedPlat.getIdPlat();
                
                // Calculer le nouveau prix en utilisant le pourcentage de réduction et le prix original
                float prixOriginal = selectedPlat.getPrix_Plat(); 
                float nouveauPrix = prixOriginal - (prixOriginal * (pourcentageReduction / 100));
                
                
                st.setFloat(3, pourcentageReduction);
                st.setString(4, codePromo);
                st.setFloat(5, nouveauPrix); 
                st.setInt(6, idPlat); 
                clear();
                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(AlertType.INFORMATION, "Ajout réussi", "Nouvelle promotion ajoutée avec succès.");
                } else {
                    showAlert(AlertType.WARNING, "Aucune modification", "Aucune promotion ajoutée. Aucune ligne affectée dans la base de données.");
                }
            } else {
                showAlert(AlertType.ERROR, "Champs vides", "Veuillez remplir tous les champs .");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'insertion de la promotion : " + e.getMessage());
            showAlert(AlertType.ERROR, "Erreur d'insertion", "Une erreur s'est produite lors de l'insertion de la promotion.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Erreur de format de nombre lors de la conversion : " + e.getMessage());
            showAlert(AlertType.ERROR, "Erreur de format", "Veuillez entrer des valeurs valides pour le pourcentage de réduction et le code promo.");
        }
    }



    @FXML
    void updatePromotion(ActionEvent event) throws SQLException {
        Promotion promotion = table.getSelectionModel().getSelectedItem();
        if (promotion != null) {
            String update = "UPDATE promotion SET DateDebut = ?, DateFin = ?, PourcentageReduction = ?, CodePromo = ?, NouvPrix = ?  WHERE IdPromo = ?";
            Connection con = DatabaseConnection.getConnection();
            try {
                st = con.prepareStatement(update);

               
                st.setDate(1, java.sql.Date.valueOf(fdatedeb.getValue()));
                st.setDate(2, java.sql.Date.valueOf(fdatefin.getValue()));
                float pourcentageReduction = Float.parseFloat(fpourprom.getText());
                String codePromo = String.valueOf(fcodeprom.getText());
                float prixOriginal = selectedPlat.getPrix_Plat(); 
                float nouveauPrix = prixOriginal - (prixOriginal * (pourcentageReduction / 100));

                st.setFloat(3, pourcentageReduction);
                st.setString(4, codePromo);
                st.setFloat(5, nouveauPrix);
                st.setInt(6, promotion.getIdPromo()); 

                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(AlertType.INFORMATION, "Modification réussie", "Promotion modifiée avec succès.");
                } else {
                    showAlert(AlertType.WARNING, "Aucune modification", "Aucune promotion modifiée. Aucune ligne affectée dans la base de données.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de la modification de la promotion : " + e.getMessage());
                showAlert(AlertType.ERROR, "Erreur de modification", "Une erreur s'est produite lors de la modification de la promotion.");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.err.println("Erreur de format de nombre lors de la conversion : " + e.getMessage());
                showAlert(AlertType.ERROR, "Erreur de format", "Veuillez entrer des valeursvalides pour le pourcentage de réduction et le code promo.");
            }
        } else {
            showAlert(AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner une promotion à modifier.");
        }
    }

    @FXML
    void deletePromotion(ActionEvent event) throws SQLException {
        Promotion promotion = table.getSelectionModel().getSelectedItem();
        if (promotion != null) {
            String delete = "DELETE FROM promotion WHERE IdPromo = ?";
            Connection con = DatabaseConnection.getConnection();
            try {
                st = con.prepareStatement(delete);
                st.setInt(1, promotion.getIdPromo());
                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(AlertType.INFORMATION, "Promotion supprimée", "Promotion supprimée avec succès.");
                } else {
                    showAlert(AlertType.INFORMATION, "Promotion non supprimée", "Aucune promotion supprimée. Aucune ligne affectée dans la base de données.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de la suppression de la promotion : " + e.getMessage());
            }
        } else {
            showAlert(AlertType.WARNING, "Sélection requise", "Veuillez sélectionner une promotion à supprimer.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}