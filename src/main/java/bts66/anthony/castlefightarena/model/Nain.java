package bts66.anthony.castlefightarena.model;

public class Nain extends Personnage {

    public Nain(String nom) {
        super(nom, "file:src/main/resources/bts66/anthony/castlefightarena/images/nain.png");
    }

    @Override
    public String frapper(int forceDuCoup) {
        return this.nom + " frappe avec son marteau et provoque "+forceDuCoup+ " de dégats.";
    }

    @Override
    public void sePresenter() {
        System.out.println("Un nain qui frappe fort, très fort !");
    }
}
