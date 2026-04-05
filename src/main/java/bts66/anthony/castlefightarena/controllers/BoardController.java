package bts66.anthony.castlefightarena.controllers;

import bts66.anthony.castlefightarena.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static bts66.anthony.castlefightarena.App.changeScene;

public class BoardController {
    private Personnage[] personnages;
    private Personnage personnage;
    private final String nom;

    public BoardController() {
        this.nom = "Joueur";
        this.personnages = new Personnage[]{
                new Elfe(nom),
                new Guerrier(nom),
                new Nain(nom),
                new Sorciere(nom)
        };
    }

    @FXML
    private Button launch;
    @FXML
    private ImageView elfe;
    @FXML
    private ImageView guerrier;
    @FXML
    private ImageView nain;
    @FXML
    private ImageView sorciere;

    @FXML
    protected void onLaunch(ActionEvent actionEvent) throws IOException {
        if (personnage != null) {
            FXMLLoader game = changeScene("game-view.fxml", actionEvent);
            ((GameController) game.getController()).setPlayer(personnage);
        }
    }

    @FXML
    protected void onRetour(ActionEvent actionEvent) throws IOException {
        changeScene("main-view.fxml", actionEvent);
    }

    @FXML
    protected void onQuit(ActionEvent actionEvent) {
        Platform.exit();
    }

    private void appliquerEffet(ImageView select, ImageView[] images) {
        launch.setStyle("-fx-background-color: #35CC08;");
        select.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,1.8), 30, 0, 0, 0);");
        images[0].setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        images[1].setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        images[2].setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    }

    @FXML
    protected void onClickedElfe(MouseEvent mouseEvent) {
        personnage = personnages[0];
        appliquerEffet(elfe, new ImageView[]{guerrier, nain, sorciere});
    }

    @FXML
    protected void onClickedGuerrier(MouseEvent mouseEvent) {
        personnage = personnages[1];
        appliquerEffet(guerrier, new ImageView[]{elfe, nain, sorciere});
    }

    @FXML
    protected void onClickedNain(MouseEvent mouseEvent) {
        personnage = personnages[2];
        appliquerEffet(nain, new ImageView[]{elfe, guerrier, sorciere});
    }

    @FXML
    protected void onClickedSorciere(MouseEvent mouseEvent) {
        personnage = personnages[3];
        appliquerEffet(sorciere, new ImageView[]{elfe, guerrier, nain});
    }
}
