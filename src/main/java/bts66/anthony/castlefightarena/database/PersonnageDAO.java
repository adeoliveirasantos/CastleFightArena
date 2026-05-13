package bts66.anthony.castlefightarena.database;

import bts66.anthony.castlefightarena.model.Personnage;
import bts66.anthony.castlefightarena.model.TypePersonnage;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PersonnageDAO implements ModelDAO<Personnage, List<Personnage>> {
    private final StatistiqueDAO statistiqueDAO;

    /**
     * Enregistre le personnage dans la base de données
     *
     * @param personnage Personnage à sauvegarder
     * @return true en cas de succès false sinon
     */
    @Override
    public boolean save(Personnage personnage) {
        String sql = "INSERT INTO personnage (type, nom, description, vie, degats) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personnage.getType().toString());
            stmt.setString(2, personnage.getNom());
            stmt.setString(3, personnage.getDescription());
            stmt.setInt(4, personnage.getVie());
            stmt.setInt(5, personnage.getDegats());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement du personnage : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Met à jour le personnage dans la base de données
     *
     * @param personnage Personnage à mettre à jour
     * @return true en cas de succès false sinon
     */
    @Override
    public boolean update(Personnage personnage) {
        String sql = "UPDATE personnage SET (type, nom, description, vie, degats) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personnage.getType().toString());
            stmt.setString(2, personnage.getNom());
            stmt.setString(3, personnage.getDescription());
            stmt.setInt(4, personnage.getVie());
            stmt.setInt(5, personnage.getDegats());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du personnage : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Supprime l'objet Personnage de la base de données
     *
     * @param id Identifiant du personnage
     * @return true en cas de succès false sinon
     */
    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM personnage WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du personnage : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Récupère l'objet Personnage depuis la base de données
     *
     * @param id Identifiant du personnage
     * @return objet Personnage
     */
    @Override
    public Personnage getById(int id) {
        String sql = "SELECT * FROM personnage WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Personnage(
                        id,
                        TypePersonnage.valueOf(rs.getString("type")),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("vie"),
                        rs.getInt("degats"),
                        statistiqueDAO.getById(rs.getInt("id"))
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du personnage : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupère tous les personnages de la base de données
     *
     * @return liste d'objets Personnage
     */
    @Override
    public List<Personnage> getAll() {
        String sql = "SELECT * FROM personnage";
        List<Personnage> personnages = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                personnages.add(new Personnage(
                        rs.getInt("id"),
                        TypePersonnage.valueOf(rs.getString("type")),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("vie"),
                        rs.getInt("degats"),
                        statistiqueDAO.getById(rs.getInt("id"))
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des personnages : " + e.getMessage());
            e.printStackTrace();
        }
        return personnages;
    }
}
