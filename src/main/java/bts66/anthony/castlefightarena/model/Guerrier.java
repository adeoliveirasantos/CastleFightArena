package bts66.anthony.castlefightarena.model;

public class Guerrier extends Personnage {

    public Guerrier(String nom) {
        super(nom, "file:src/main/resources/bts66/anthony/castlefightarena/images/guerrier.png");
    }

    @Override
    public String frapper(int forceDuCoup) {
        return this.nom + " donne un coup d'épée avec un force de "+forceDuCoup+".";
    }

    @Override
    public void sePresenter() {
        System.out.println("Un combattant qui a son épée pour seule compagne.");
    }
}
