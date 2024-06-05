package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.model.Plat;
import application.model.Service;
import application.model.Menu;

public class Scene2Controller {

    @FXML
    private Button AjouterPlat, AjouterService;
    @FXML 
    private TableColumn<Plat, String> NomPlatCol;
    @FXML 
    private TableColumn<Plat, Float> PrixPlatCol;
    @FXML
    private TableColumn<Service, String> NomServiceCol;
    @FXML
    private TableColumn<Service, String> PrixServiceCol;
    @FXML
    private TableView<Plat> PlatTable;
    @FXML
    private TableView<Service> ServiceTable;
    @FXML
    private TextField PlatMenu, ServiceMenu;
    @FXML
    private TextField txtDescription, txtNom;

    private Menu menu;
    private ObservableList<Plat> platsList = FXCollections.observableArrayList();
    private ObservableList<Service> servicesList = FXCollections.observableArrayList();
    private Connection connection;

    public void initData(Menu menu) {
        this.menu = menu;
        txtNom.setText(menu.getNomMenu());
        txtDescription.setText(menu.getDescriptionMenu());
        loadPlats();
        loadServices();
    }

    private void loadPlats() {
        platsList.clear();
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM plat WHERE IdMenu = ?");
            pst.setInt(1, menu.getIdMenu());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Plat plat = new Plat(
                    rs.getInt("IdPlat"),
                    rs.getString("NomPlat"),
                    rs.getString("Categorie"),
                    rs.getString("DescriptionPlat"),
                    rs.getFloat("PrixPlat"),
                    rs.getString("Image"),
                    null // Assuming promotions are handled separately
                );
                platsList.add(plat);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        PlatTable.setItems(platsList);
    }

    private void loadServices() {
        servicesList.clear();
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM service WHERE IdMenu = ?");
            pst.setInt(1, menu.getIdMenu());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Service service = new Service(
                    rs.getInt("idService"),
                    rs.getString("NomService"),
                    rs.getString("DescriptionService"),
                    rs.getDouble("PrixService")
                );
                servicesList.add(service);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ServiceTable.setItems(servicesList);
    }

    @FXML
    void AjouterPlat(ActionEvent event) {
        String nomPlat = PlatMenu.getText();
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM plat WHERE NomPlat = ? AND IdMenu IS NULL");
            pst.setString(1, nomPlat);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Plat plat = new Plat(
                    rs.getInt("IdPlat"),
                    rs.getString("NomPlat"),
                    rs.getString("Categorie"),
                    rs.getString("DescriptionPlat"),
                    rs.getFloat("PrixPlat"),
                    rs.getString("Image"),
                    null
                );
                PreparedStatement updatePst = connection.prepareStatement("UPDATE plat SET IdMenu = ? WHERE IdPlat = ?");
                updatePst.setInt(1, this.menu.getIdMenu());
                updatePst.setInt(2, plat.getIdPlat());
                updatePst.executeUpdate();

                platsList.add(plat);
                PlatTable.refresh();
                connection.commit();
            } else {
                System.out.println("No plat found with the name: " + nomPlat + ", or plat is already linked to a menu.");
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                System.out.println("Error rolling back: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    void AjouterService(ActionEvent event) {
        String nomService = ServiceMenu.getText();
        try {
            // Update the query to use the correct column name "name" instead of "NomService"
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM service WHERE NomService = ? AND IdMenu IS NULL");
            pst.setString(1, nomService);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Service service = new Service(
                    rs.getInt("idService"),
                    rs.getString("NomService"),
                    rs.getString("DescriptionService"),
                    rs.getDouble("PrixService")
                );
                PreparedStatement updatePst = connection.prepareStatement("UPDATE service SET IdMenu = ? WHERE idService = ?");
                updatePst.setInt(1, this.menu.getIdMenu());
                updatePst.setInt(2, service.getId());
                updatePst.executeUpdate();

                servicesList.add(service);
                ServiceTable.refresh();
                connection.commit();
            } else {
                System.out.println("Aucun service trouvé qui a comme nom: " + nomService + ", ou bien le service est deja lié avec un menu.");
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                System.out.println("Error rolling back: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void initialize() {
    	NomPlatCol.setCellValueFactory(new PropertyValueFactory<>("Nom_Plat")); 
        PrixPlatCol.setCellValueFactory(new PropertyValueFactory<>("Prix_Plat"));
        NomServiceCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PrixServiceCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        try {
            this.connection.setAutoCommit(false);  // Disable autocommit to manually manage transactions
        } catch (SQLException ex) {
            System.out.println("Error setting autocommit to false: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}