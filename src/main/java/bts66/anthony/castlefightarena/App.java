package bts66.anthony.castlefightarena;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Castle Fight Arena");
        stage.setScene(scene);
        stage.show();
    }

    public static FXMLLoader changeScene(String fxml, ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setScene(scene);
        return fxmlLoader;
    }

    public static FXMLLoader changeScene(String fxml, MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setScene(scene);
        return fxmlLoader;
    }
}
