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
    private final List<Personnage> personnages = Arrays.asList(
            new Personnage(0, TypePersonnage.ELFE, "Bot", "description", 40, 5, null),
            new Personnage(0, TypePersonnage.GUERRIER, "Bot", "description", 40, 5, null),
            new Personnage(0, TypePersonnage.NAIN, "Bot", "description", 40, 5, null),
            new Personnage(0, TypePersonnage.SORCIERE, "Bot", "description", 40, 5, null)
    );
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
            this.bot = personnages.get(random.nextInt(3) + 1);
        } while (bot.getType().equals(player.getType()));

        this.playerPersonnage.setImage(new Image(player.getType().getImage()));
        this.botPersonnage.setImage(new Image(bot.getType().getImage()));
        for (int i = 0; i < 6; i++) {
            Label placeholder = new Label("");
            messageBox.getChildren().add(placeholder);
        }
        player.setVieCombat(player.getVie());
        bot.setVieCombat(bot.getVie());
    }

    @FXML
    protected void onFight(MouseEvent mouseEvent) {
        if (this.player == null) return;
        if (this.player.getVieCombat() <= 0 || this.bot.getVieCombat() <= 0) return;

        // Application des dégâts au combat
        // 3 Difficultés :
        // 3 à 8 de dégâts
        // 4 à 10 de dégâts
        // 5 à 12 de dégâts
        int playerDammage = random.nextInt(3, 8);
        int botDammage = random.nextInt(3, 8);
        this.player.setVieCombat(this.player.getVieCombat() - botDammage);
        this.bot.setVieCombat(this.bot.getVieCombat() - playerDammage);

        if (this.player.getVieCombat() < 0) {
            this.player.setVieCombat(0);
        }
        if (this.bot.getVieCombat() < 0) {
            this.bot.setVieCombat(0);
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
        if (this.player.getVieCombat() < 40) {
            playerLifeLabel.getStyleClass().add("damage");
        }
        if (this.bot.getVieCombat() < 40) {
            botLifeLabel.getStyleClass().add("damage");
        }

        String playerLifePlaceholder = "";
        for (int i = 0; i < (40 - this.player.getVieCombat()); i++) {
            playerLifePlaceholder = playerLifePlaceholder + " ";
        }
        playerLifeLabel.setText(playerLifePlaceholder);

        String botLifePlaceholder = "";
        for (int i = 0; i < (40 - this.bot.getVieCombat()); i++) {
            botLifePlaceholder = botLifePlaceholder + " ";
        }
        botLifeLabel.setText(botLifePlaceholder);

        // Vérification de la fin du combat
        if (this.player.getVieCombat() <= 0 || this.bot.getVieCombat() <= 0) {
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

            if (this.player.getVieCombat() > this.bot.getVieCombat()) {
                Label gagnantMessage = new Label("Le gagnant est le Joueur !");
                gagnantMessage.getStyleClass().add("finish");
                gagnantMessage.setLayoutX(190);
                gagnantMessage.setLayoutY(160);
                rootPane.getChildren().add(gagnantMessage);
            } else if (this.player.getVieCombat() < this.bot.getVieCombat()) {
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
