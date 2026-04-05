package bts66.anthony.castlefightarena.model;

public abstract class Personnage {
    String nom;
    String image;
    int vie;

    public Personnage(String nom, String image) {
        this.nom = nom;
        this.image = image;
        this.vie = 40;
    }

    public String getNom() {
        return nom;
    }

    public String getImage() { return image; }

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public abstract String frapper(int forceDuCoup);

    public abstract void sePresenter();
}
