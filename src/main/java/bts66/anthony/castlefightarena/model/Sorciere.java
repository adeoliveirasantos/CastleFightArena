package bts66.anthony.castlefightarena.model;

public class Sorciere extends Personnage {

    public Sorciere(String nom) {
        super(nom, "file:src/main/resources/bts66/anthony/castlefightarena/images/sorciere.png");
    }

    @Override
    public String frapper(int forceDuCoup) {
        return this.nom + " jette un sort et provoque "+forceDuCoup+" de dégats.";
    }

    @Override
    public void sePresenter() {
        System.out.println("Une sorcière qui peut te transformer en citrouille.");
    }
}
