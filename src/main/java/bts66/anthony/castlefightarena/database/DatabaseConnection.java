package bts66.anthony.castlefightarena.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe Singleton pour gérer la connexion à la base de données MySQL
 *
 * @author Utilisateur
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/castlefightarena";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private static DatabaseConnection instance = null;
    private Connection connection = null;

    /**
     * Constructeur privé pour empêcher l'instanciation directe
     */
    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    /**
     * Obtient l'instance unique de DatabaseConnection (Singleton)
     *
     * @return l'instance unique de DatabaseConnection
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {

            if (instance == null) {
                instance = new DatabaseConnection();
            }

        }
        return instance;
    }

    /**
     * Obtient la connexion à la base de données
     *
     * @return Connection object
     * @throws SQLException si la connexion échoue ou est fermée
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Reconnexion à la base de données réussie !");
        }
        return connection;
    }

    /**
     * Ferme la connexion à la base de données
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connexion fermée.");
                connection = null;
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}
