package bts66.anthony.castlefightarena.database;

import bts66.anthony.castlefightarena.model.Personnage;
import bts66.anthony.castlefightarena.model.Statistique;
import bts66.anthony.castlefightarena.model.TypePersonnage;

import java.util.ArrayList;
import java.util.List;

public class PersonnageSeeder {
    private PersonnageSeeder() {
        // static class
    }

    public static void savePersonnages(PersonnageDAO personnageDAO, StatistiqueDAO statistiqueDAO) {
        List<Statistique> stats = new ArrayList<>();
        for (int i = 1; i<5; i++) {
            Statistique statistique = new Statistique(i);
            statistique.setWinRate(0.0f);
            statistique.setVictoires(0);
            statistique.setDefaites(0);
            statistique.setDernierCombat(null);
            statistiqueDAO.save(statistique);
            stats.add(statistique);
        }

        personnageDAO.save(new Personnage(1, TypePersonnage.ELFE, "Alfrid (Elfe)", "Description", 40, 5, stats.getFirst()));
        personnageDAO.save(new Personnage(2, TypePersonnage.GUERRIER, "Mickael (Guerrier)", "Description", 40, 5, stats.get(1)));
        personnageDAO.save(new Personnage(3, TypePersonnage.NAIN, "Béréon (Nain)", "Description", 40, 5, stats.get(2)));
        personnageDAO.save(new Personnage(4, TypePersonnage.SORCIERE, "Méslanges (Sorcière)", "Description", 40, 5, stats.get(3)));
    }

    public static List<Personnage> getPersonnages(PersonnageDAO personnageDAO) {
        List<Personnage> personnages = new ArrayList<>();
        personnages.add(personnageDAO.getById(1));
        personnages.add(personnageDAO.getById(2));
        personnages.add(personnageDAO.getById(3));
        personnages.add(personnageDAO.getById(4));
        return personnages;
    }
}
