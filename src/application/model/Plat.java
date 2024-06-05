package application.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Plat {
    private Integer IdPlat;
   	private String Nom_Plat;
    private String Catégorie;
    private String Description_Plat;
    private Float Prix_Plat;
    private String Image;
    private List<Promotion> List_Promotion;

    // Constructeur
    public Plat(Integer IdPlat, String Nom_Plat, String Catégorie, String Description_Plat, Float Prix_Plat, String Image, List<Promotion> List_Promotion) {
        this.IdPlat = IdPlat;
        this.Nom_Plat = Nom_Plat;
        this.Catégorie = Catégorie;
        this.Description_Plat = Description_Plat;
        this.Prix_Plat = Prix_Plat;
        this.Image = Image;
        this.List_Promotion = List_Promotion;
    }
    
    public Integer getIdPlat() {
		return IdPlat;
	}



	public void setIdPlat(Integer idPlat) {
		IdPlat = idPlat;
	}
    

    public Plat() {
		// TODO Auto-generated constructor stub
	}



	public Plat(String nom) {
		// TODO Auto-generated constructor stub
	}



	public String getNom_Plat() {
		return Nom_Plat;
	}



	public void setNom_Plat(String nom_Plat) {
		Nom_Plat = nom_Plat;
	}



	public String getCatégorie() {
		return Catégorie;
	}



	public void setCatégorie(String catégorie) {
		Catégorie = catégorie;
	}



	public String getDescription_Plat() {
		return Description_Plat;
	}



	public void setDescription_Plat(String description_Plat) {
		Description_Plat = description_Plat;
	}



	public Float getPrix_Plat() {
		return Prix_Plat;
	}



	public void setPrix_Plat(Float prix_Plat) {
		Prix_Plat = prix_Plat;
	}



	public String getImage() {
		return Image;
	}



	public void setImage(String image) {
		Image = image;
	}



	public List<Promotion> getList_Promotion() {
		return List_Promotion;
	}



	public void setList_Promotion(List<Promotion> list_Promotion) {
		List_Promotion = list_Promotion;
	}



	// Méthode pour vérifier si un plat existe
    public boolean existPlat(String Nom) {
        // Connexion à la base de données
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Plat WHERE NomPlat = ?")) {
            preparedStatement.setString(1, Nom);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Méthode pour créer un plat
    public boolean createPlat() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Plat (NomPlat, Categorie, DescriptionPlat, PrixPlat, Image) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, this.Nom_Plat);
            preparedStatement.setString(2, this.Catégorie);
            preparedStatement.setString(3, this.Description_Plat);
            preparedStatement.setFloat(4, this.Prix_Plat);
            preparedStatement.setString(5, this.Image);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour supprimer un plat
    public boolean deletePlat() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Plat WHERE NomPlat = ?")) {
            preparedStatement.setString(1, this.Nom_Plat);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour lire un plat
    public Plat readPlat(String nom) {
        Plat plat = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Plat WHERE NomPlat = ?")) {
            preparedStatement.setString(1, nom);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer IdPlat = resultSet.getInt("IdPlat");
                String Nom_Plat = resultSet.getString("NomPlat");
                String Catégorie = resultSet.getString("Categorie");
                String Description_Plat = resultSet.getString("DescriptionPlat");
                Float Prix_Plat = resultSet.getFloat("PrixPlat");
                String Image = resultSet.getString("Image");
                // List_Promotion needs to be fetched separately, as it's a separate table
                List<Promotion> List_Promotion = getPromotionsForPlat(IdPlat);
                plat = new Plat(IdPlat, Nom_Plat, Catégorie, Description_Plat, Prix_Plat, Image, List_Promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plat;
    }

    // Méthode pour mettre à jour un plat
    public boolean updatePlat(String nom, String description, String categorie, Float prix, String image) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Plat SET Categorie = ?, DescriptionPlat = ?, PrixPlat = ?, Image = ? WHERE NomPlat = ?")) {
            preparedStatement.setString(1, categorie);
            preparedStatement.setString(2, description);
            preparedStatement.setFloat(3, prix);
            preparedStatement.setString(4, image);
            preparedStatement.setString(5, this.Nom_Plat);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour récupérer tous les plats
    public static  List<Plat> getAllPlats() {
        List<Plat> platList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Plat")) {
            while (resultSet.next()) {
                Integer IdPlat = resultSet.getInt("IdPlat");
                String Nom_Plat = resultSet.getString("NomPlat");
                String Catégorie = resultSet.getString("Categorie");
                String Description_Plat = resultSet.getString("DescriptionPlat");
                Float Prix_Plat = resultSet.getFloat("PrixPlat");
                String Image = resultSet.getString("Image");
                // List_Promotion needs to be fetched separately, as it's a separate table
                List<Promotion> List_Promotion = getPromotionsForPlat(IdPlat);
                Plat plat = new Plat(IdPlat, Nom_Plat, Catégorie, Description_Plat, Prix_Plat, Image, List_Promotion);
                platList.add(plat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return platList;
    }

 // Méthode pour récupérer les promotions pour un plat donné
    private static List<Promotion> getPromotionsForPlat(Integer IdPlat) {
        List<Promotion> promotionList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Promotion WHERE IdPlat = ?")) {
            preparedStatement.setInt(1, IdPlat);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer IdPromo = resultSet.getInt("IdPromo");
                Date dateDebut = resultSet.getDate("DateDebut");
                Date dateFin = resultSet.getDate("DateFin");
                Float pourcentageReduction = resultSet.getFloat("PourcentageReduction");
                Float prixOriginal = resultSet.getFloat("PrixOriginal");
                String codePromo = resultSet.getString("CodePromo");
                Float NouvPrix =resultSet.getFloat("NouvPrix");

                // Créer un objet Promotion avec les détails récupérés
                Promotion promotion = new Promotion(IdPromo, dateDebut, dateFin, pourcentageReduction, codePromo, NouvPrix);
                // Ajouter la promotion à la liste des promotions
                promotionList.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotionList;
    }



	@Override
	public String toString() {
	    StringBuilder stringBuilder = new StringBuilder();
	    stringBuilder.append("Plat [Nom_Plat=").append(Nom_Plat)
	                 .append(", Catégorie=").append(Catégorie)
	                 .append(", Description_Plat=").append(Description_Plat)
	                 .append(", Prix_Plat=").append(Prix_Plat)
	                 .append(", Image=").append(Image)
	                 .append(", Promotions=");
	    if (List_Promotion != null) {
	        for (Promotion promotion : List_Promotion) {
	            stringBuilder.append("\n").append(promotion);
	        }
	    } else {
	        stringBuilder.append("Aucune promotion disponible");
	    }
	    stringBuilder.append("]");
	    return stringBuilder.toString();
	}


}