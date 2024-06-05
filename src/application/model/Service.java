package application.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Service {
    private int idService;
    private String nom_service;
    private String description_service;
    private double prix_service;
    private List<Service> List_Promotion;
    // Constructeur sans l'ID
    public Service(String name, String description, double prix , List<Service> List_Promotion) {
        this.nom_service = name;
        this.description_service = description;
        this.prix_service = prix;
        this.List_Promotion = List_Promotion;
    }
    
    public Service(String name, String description, double prix ) {
        this.nom_service = name;
        this.description_service = description;
        this.prix_service = prix;

    }
    
    public Service(int id, String name, String description, double prix , List<Service> List_Promotion) {
        this.idService = id;
        this.nom_service = name;
        this.description_service = description;
        this.prix_service = prix;
        this.List_Promotion = List_Promotion;
    }
    
    public Service(int id, String name, String description, double prix  ) {
    	 this.idService = id;
        this.nom_service = name;
        this.description_service = description;
        this.prix_service = prix;
     
    }

    public Service(int id) {
        this.idService = id;
    }

    public Service(LocalDate dateDebut, LocalDate dateFin, double pourcentageReduction, int codePromo) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
        return idService;
    }

    public void setId(int id) {
        this.idService = id;
    }

    public String getName() {
        return nom_service;
    }

    public void setName(String name) {
        this.nom_service = name;
    }

    public String getDescription() {
        return description_service;
    }

    public void setDescription(String description) {
        this.description_service = description;
    }

    public double getPrix() {
        return prix_service;
    }

    public void setPrix(double prix) {
        this.prix_service = prix;
    }
    
    
    
    public List<Service> getList_Promotion() {
		return List_Promotion;
	}

	public void setList_Promotion(List<Service> list_Promotion) {
		List_Promotion = list_Promotion;
	}

	public boolean createService() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO service(NomService, DescriptionService, PrixService) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, this.nom_service);
            preparedStatement.setString(2, this.description_service);
            preparedStatement.setDouble(3, this.prix_service);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateService() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE service SET NomService = ?, DescriptionService = ?, PrixService = ? WHERE IdService = ?")) {
            preparedStatement.setString(1, this.nom_service);
            preparedStatement.setString(2, this.description_service);
            preparedStatement.setDouble(3, this.prix_service);
            preparedStatement.setInt(4, this.idService);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteService() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM service WHERE IdService = ?")) {
            preparedStatement.setInt(1, this.idService);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Service> getAllServices() {
        ObservableList<Service> serviceList = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM service");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("IdService");
                String name = resultSet.getString("NomService");
                String description = resultSet.getString("DescriptionService");
                double price = resultSet.getDouble("PrixService");
                Service service = new Service(id, name, description, price);
                serviceList.add(service);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return serviceList;
    }
    
    
    
   

	

}
