package bts66.anthony.castlefightarena.database;

import bts66.anthony.castlefightarena.model.Personnage;
import bts66.anthony.castlefightarena.model.StatistiquePersonnage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO pour gérer l'enregistrement des résultats de combat
 *
 * @author Utilisateur
 */
public class CombatDAO {

    /**
     * Enregistre le résultat d'un combat dans la base de données
     *
     * @param gagnant Le personnage gagnant
     * @param perdant Le personnage perdant
     * @return true si l'enregistrement a réussi, false sinon
     */
    public static boolean enregistrerResultat(Personnage gagnant, Personnage perdant) {
        String sql = "INSERT INTO resultats_combats (nom_gagnant, nom_perdant, vie_restante_gagnant, date_combat) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, gagnant.getNom());
            stmt.setString(2, perdant.getNom());
            stmt.setInt(3, gagnant.getVie());
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Résultat du combat enregistré : " + gagnant.getNom() + " a vaincu " + perdant.getNom() + " avec " + gagnant.getVie() + " PV restants");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement du résultat : " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Récupère les statistiques de tous les personnages
     *
     * @return Liste des statistiques (victoires, défaites, total PV) par
     * personnage
     */
    public static List<StatistiquePersonnage> getStatistiques() {
        List<StatistiquePersonnage> statistiques = new ArrayList<>();

        String sql = "SELECT nom, "
                + "SUM(victoires) as total_victoires, "
                + "SUM(defaites) as total_defaites, "
                + "SUM(points_vie) as total_points "
                + "FROM ("
                + "    SELECT nom_gagnant as nom, "
                + "           COUNT(*) as victoires, "
                + "           0 as defaites, "
                + "           SUM(vie_restante_gagnant) as points_vie "
                + "    FROM resultats_combats "
                + "    GROUP BY nom_gagnant "
                + "    UNION ALL "
                + "    SELECT nom_perdant as nom, "
                + "           0 as victoires, "
                + "           COUNT(*) as defaites, "
                + "           0 as points_vie "
                + "    FROM resultats_combats "
                + "    GROUP BY nom_perdant"
                + ") AS stats "
                + "GROUP BY nom "
                + "ORDER BY total_victoires DESC, total_points DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            System.out.println("Exécution de la requête SQL pour récupérer les statistiques...");

            int count = 0;
            while (rs.next()) {
                String nom = rs.getString("nom");
                int victoires = rs.getInt("total_victoires");
                int defaites = rs.getInt("total_defaites");
                int totalPoints = rs.getInt("total_points");

                System.out.println("Ligne " + (++count) + ": " + nom + " - V:" + victoires + " D:" + defaites + " PV:" + totalPoints);

                statistiques.add(new StatistiquePersonnage(nom, victoires, defaites, totalPoints));
            }

            System.out.println("Total de " + count + " statistiques récupérées.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des statistiques : " + e.getMessage());
            e.printStackTrace();
        }

        return statistiques;
    }
}
