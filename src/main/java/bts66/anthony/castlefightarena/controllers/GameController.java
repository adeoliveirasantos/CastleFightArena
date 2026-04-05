package bts66.anthony.castlefightarena.controllers;

import bts66.anthony.castlefightarena.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

import static bts66.anthony.castlefightarena.App.changeScene;

public class GameController {
    private final Random random = new Random();
    private String blackEffect = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);";
    private String whiteEffect = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,1.8), 30, 0, 0, 0);";
    private Personnage[] personnages = {
            new Elfe("Bot"),
            new Guerrier("Bot"),
            new Nain("Bot"),
            new Sorciere("Bot")
    };
    private Personnage player;
    private Personnage bot;

    @FXML
    private VBox messageBox;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView sword;
    @FXML
    private ImageView playerPersonnage;
    @FXML
    private ImageView botPersonnage;
    @FXML
    private Label playerLifeLabel;
    @FXML
    private Label botLifeLabel;

    public void setPlayer(Personnage player) {
        this.player = player;
        do {
            this.bot = personnages[random.nextInt(3) + 1];
        } while (bot.getClass().equals(player.getClass()));

        this.playerPersonnage.setImage(new Image(player.getImage()));
        this.botPersonnage.setImage(new Image(bot.getImage()));
        for (int i = 0; i < 6; i++) {
            Label placeholder = new Label("");
            messageBox.getChildren().add(placeholder);
        }
    }

    @FXML
    protected void onFight(MouseEvent mouseEvent) {
        if (this.player == null) return;
        if (this.player.getVie() <= 0 || this.bot.getVie() <= 0) return;

        // Application des dégâts au combat
        // 3 Difficultés :
        // 3 à 8 de dégâts
        // 4 à 10 de dégâts
        // 5 à 12 de dégâts
        int playerDammage = random.nextInt(3, 8);
        int botDammage = random.nextInt(3, 8);
        this.player.setVie(this.player.getVie() - botDammage);
        this.bot.setVie(this.bot.getVie() - playerDammage);

        if (this.player.getVie() < 0) {
            this.player.setVie(0);
        }
        if (this.bot.getVie() < 0) {
            this.bot.setVie(0);
        }

        // Chargement des bulles de message
        ArrayList<String> messages = new ArrayList<>();
        Collections.addAll(messages, "Joueur viens d'infliger " + playerDammage + " à Bot.",
                "Bot viens d'infliger " + botDammage + " à Joueur.");

        for (int i = 0; i < 2; i++) {
            Label msg = new Label(messages.get(i));
            msg.getStyleClass().add("title");
            messageBox.getChildren().add(msg);
            messageBox.getChildren().removeFirst();
        }
        Label placeholder = new Label("");
        messageBox.getChildren().add(placeholder);
        messageBox.getChildren().removeFirst();

        // Mise à jour des barres de vie
        if (this.player.getVie() < 40) {
            playerLifeLabel.getStyleClass().add("damage");
        }
        if (this.bot.getVie() < 40) {
            botLifeLabel.getStyleClass().add("damage");
        }

        String playerLifePlaceholder = "";
        for (int i = 0; i < (40 - this.player.getVie()); i++) {
            playerLifePlaceholder = playerLifePlaceholder + " ";
        }
        playerLifeLabel.setText(playerLifePlaceholder);

        String botLifePlaceholder = "";
        for (int i = 0; i < (40 - this.bot.getVie()); i++) {
            botLifePlaceholder = botLifePlaceholder + " ";
        }
        botLifeLabel.setText(botLifePlaceholder);

        // Vérification de la fin du combat
        if (this.player.getVie() <= 0 || this.bot.getVie() <= 0) {
            ImageView blackboard = new ImageView();
            blackboard.setImage(new Image("file:src/main/resources/bts66/anthony/castlefightarena/images/blackboard.png"));
            blackboard.setOpacity(0.5D);
            blackboard.setPreserveRatio(false);
            blackboard.setFitWidth(640);
            blackboard.setFitHeight(480);
            rootPane.getChildren().add(blackboard);

            Label finalMessage = new Label("Combat Terminé !");
            finalMessage.getStyleClass().add("finish");
            finalMessage.setLayoutX(220);
            finalMessage.setLayoutY(80);
            rootPane.getChildren().add(finalMessage);

            Button menuButton = new Button("Retourner au menu");
            menuButton.getStyleClass().add("button");
            menuButton.setLayoutX(230);
            menuButton.setLayoutY(300);
            menuButton.setOnAction(actionEvent -> {
                try {
                    changeScene("main-view.fxml", actionEvent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            rootPane.getChildren().add(menuButton);

            if (this.player.getVie() > this.bot.getVie()) {
                Label gagnantMessage = new Label("Le gagnant est le Joueur !");
                gagnantMessage.getStyleClass().add("finish");
                gagnantMessage.setLayoutX(190);
                gagnantMessage.setLayoutY(160);
                rootPane.getChildren().add(gagnantMessage);
            } else if (this.player.getVie() < this.bot.getVie()) {
                Label gagnantMessage = new Label("Le gagnant est le Robot !");
                gagnantMessage.getStyleClass().add("finish");
                gagnantMessage.setLayoutX(190);
                gagnantMessage.setLayoutY(160);
                rootPane.getChildren().add(gagnantMessage);
            } else {
                Label gagnantMessage = new Label("Egalité entre le Joueur et le Robot !");
                gagnantMessage.getStyleClass().add("finish");
                gagnantMessage.setLayoutX(150);
                gagnantMessage.setLayoutY(160);
                rootPane.getChildren().add(gagnantMessage);
            }
        }
    }

    @FXML
    protected void onTouch(MouseEvent mouseEvent) {
        sword.setStyle(whiteEffect);
    }

    @FXML
    protected void onExit(MouseEvent mouseEvent) {
        sword.setStyle(blackEffect);
    }
}
