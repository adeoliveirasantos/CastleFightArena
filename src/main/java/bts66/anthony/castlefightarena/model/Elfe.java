package bts66.anthony.castlefightarena.model;

public class Elfe extends Personnage {

    public Elfe(String nom) {
        super(nom, "file:src/main/resources/bts66/anthony/castlefightarena/images/elfe.png");
    }

    @Override
    public String frapper(int forceDuCoup) {
        return this.nom + " décoche un flèche et occasionne "+forceDuCoup+ " de dégats.";
    }

    @Override
    public void sePresenter() {
        System.out.println("Une dame elfe qui sait manier l'arc comme personne.");
    }
}
