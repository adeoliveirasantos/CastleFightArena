package bts66.anthony.castlefightarena.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import static bts66.anthony.castlefightarena.App.changeScene;

public class MainController {
    @FXML
    protected void onPlay(ActionEvent actionEvent) throws IOException {
        changeScene("board-view.fxml", actionEvent);
    }

    @FXML
    protected void onScore(ActionEvent actionEvent) throws IOException {
        changeScene("scores-view.fxml", actionEvent);
    }
}
