package application.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Informations de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/javafx";
    private static final String USER = "root";
    private static final String PASSWORD = "minamina";

    // Méthode pour établir une connexion à la base de données
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Charger le pilote JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Établir la connexion
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions
            throw new SQLException("Erreur lors de la connexion à la base de données");
        }
        return connection;
    }

    // Méthode pour fermer la connexion à la base de données
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer les exceptions
                System.err.println("Erreur lors de la fermeture de la connexion");
            }
        }
    }
}
