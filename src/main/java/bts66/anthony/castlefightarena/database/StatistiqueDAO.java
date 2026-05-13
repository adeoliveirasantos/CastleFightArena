package bts66.anthony.castlefightarena.database;

import bts66.anthony.castlefightarena.model.Statistique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatistiqueDAO implements ModelDAO<Statistique, List<Statistique>> {

    /**
     * Enregistre une statistique dans la base de données
     *
     * @param statistique Statistique à sauvegarder
     * @return true en cas de succès false sinon
     */
    @Override
    public boolean save(Statistique statistique) {
        String sql = "INSERT INTO statistique (id, win_rate, victoires, defaites, dernier_combat) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, statistique.getId());
            stmt.setFloat(2, statistique.getWinRate());
            stmt.setInt(3, statistique.getVictoires());
            stmt.setInt(4, statistique.getDefaites());
            stmt.setDate(5, statistique.getDernierCombat());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement du résultat : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Met à jour les statistiques dans la base de données
     *
     * @param statistique Statistique à mettre à jour
     * @return true en cas de succès false sinon
     */
    @Override
    public boolean update(Statistique statistique) {
        String sql = "UPDATE statistique SET (win_rate, victoires, defaites, dernier_combat) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, statistique.getWinRate());
            stmt.setInt(2, statistique.getVictoires());
            stmt.setInt(3, statistique.getDefaites());
            stmt.setDate(4, statistique.getDernierCombat());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour des résultats : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Supprime l'objet Statistique de la base de données
     *
     * @param id Identifiant du statistique
     * @return true en cas de succès false sinon
     */
    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM statistique WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression des résultats : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Récupère l'objet Statistique depuis la base de données
     *
     * @param id Identifiant du statistique
     * @return objet Statistique
     */
    @Override
    public Statistique getById(int id) {
        String sql = "SELECT * FROM statistique WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Statistique stats = new Statistique(id);
                stats.setWinRate(rs.getFloat("win_rate"));
                stats.setVictoires(rs.getInt("victoires"));
                stats.setDefaites(rs.getInt("defaites"));
                stats.setDernierCombat(rs.getDate("dernier_combat"));
                return stats;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des résultats : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupère toutes les statistiques de la base de données
     *
     * @return liste d'objets Statistique
     */
    @Override
    public List<Statistique> getAll() {
        String sql = "SELECT * FROM statistique";
        List<Statistique> statistiques = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Statistique stats = new Statistique(rs.getInt("id"));
                stats.setWinRate(rs.getFloat("win_rate"));
                stats.setVictoires(rs.getInt("victoires"));
                stats.setDefaites(rs.getInt("defaites"));
                stats.setDernierCombat(rs.getDate("dernier_combat"));
                statistiques.add(stats);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des statistiques : " + e.getMessage());
            e.printStackTrace();
        }
        return statistiques;
    }
}
