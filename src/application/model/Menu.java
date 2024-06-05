package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Menu {
    private final IntegerProperty IdMenu;
    private final StringProperty NomMenu;
    private final StringProperty DescriptionMenu;
    private List<Plat> plats;

    public Menu() {
        this.IdMenu = new SimpleIntegerProperty(); 
        this.NomMenu = new SimpleStringProperty();
        this.DescriptionMenu = new SimpleStringProperty();
    }

    
    public static void ajouterMenu(String nomMenu, String descriptionMenu, Connection con) throws SQLException {
        String query = "INSERT INTO menu(NomMenu, DescriptionMenu) VALUES (?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, nomMenu);
            pst.setString(2, descriptionMenu);
            pst.executeUpdate();
        }
    }

    public static void modifierMenu(int idMenu, String nomMenu, String descriptionMenu, Connection con) throws SQLException {
        String query = "UPDATE menu SET NomMenu = ?, DescriptionMenu = ? WHERE IdMenu = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, nomMenu);
            pst.setString(2, descriptionMenu);
            pst.setInt(3, idMenu);
            pst.executeUpdate();
        }
    }

    public static void supprimerMenu(int idMenu, Connection con) throws SQLException {
        String query = "DELETE FROM menu WHERE IdMenu = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, idMenu);
            pst.executeUpdate();
        }
    }

    public static ObservableList<Menu> loadMenuData(Connection con) throws SQLException {
        ObservableList<Menu> menuList = FXCollections.observableArrayList();
        String query = "SELECT * FROM menu";
        try (PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setIdMenu(rs.getInt("IdMenu"));
                menu.setNomMenu(rs.getString("NomMenu"));
                menu.setDescriptionMenu(rs.getString("DescriptionMenu"));
                menuList.add(menu);
            }
        }
        return menuList;
    }

    
    public int getIdMenu() {
        return IdMenu.get();
    }

    public void setIdMenu(int id) {
        this.IdMenu.set(id);
    }

    public IntegerProperty idMenuProperty() {
        return IdMenu;
    }

    public String getNomMenu() {
        return NomMenu.get();
    }

    public void setNomMenu(String newNomMenu) {
        NomMenu.set(newNomMenu);
    }

    public String getDescriptionMenu() {
        return DescriptionMenu.get();
    }

    public void setDescriptionMenu(String newDescriptionMenu) {
        DescriptionMenu.set(newDescriptionMenu);
    }

    public List<Plat> getPlats() {
        return plats;
    }

    public void setPlats(List<Plat> plats) {
        this.plats = plats;
    }
}
