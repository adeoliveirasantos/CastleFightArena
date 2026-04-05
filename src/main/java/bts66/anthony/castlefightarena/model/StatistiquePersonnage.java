package bts66.anthony.castlefightarena.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StatistiquePersonnage {

    private final StringProperty nom;
    private final IntegerProperty victoires;
    private final IntegerProperty defaites;
    private final IntegerProperty totalPointsVie;

    public StatistiquePersonnage(String nom, int victoires, int defaites, int totalPointsVie) {
        this.nom = new SimpleStringProperty(nom);
        this.victoires = new SimpleIntegerProperty(victoires);
        this.defaites = new SimpleIntegerProperty(defaites);
        this.totalPointsVie = new SimpleIntegerProperty(totalPointsVie);
    }

    // Getters pour les valeurs
    public String getNom() {
        return nom.get();
    }

    public int getVictoires() {
        return victoires.get();
    }

    public int getDefaites() {
        return defaites.get();
    }

    public int getTotalPointsVie() {
        return totalPointsVie.get();
    }

    // Getters pour les propriétés (nécessaires pour PropertyValueFactory)
    public StringProperty nomProperty() {
        return nom;
    }

    public IntegerProperty victoiresProperty() {
        return victoires;
    }

    public IntegerProperty defaitesProperty() {
        return defaites;
    }

    public IntegerProperty totalPointsVieProperty() {
        return totalPointsVie;
    }

    // Setters (optionnels, mais recommandés)
    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public void setVictoires(int victoires) {
        this.victoires.set(victoires);
    }

    public void setDefaites(int defaites) {
        this.defaites.set(defaites);
    }

    public void setTotalPointsVie(int totalPointsVie) {
        this.totalPointsVie.set(totalPointsVie);
    }

    @Override
    public String toString() {
        return String.format("%-15s | Victoires: %3d | Défaites: %3d | Total PV: %5d",
                getNom(), getVictoires(), getDefaites(), getTotalPointsVie());
    }
}
