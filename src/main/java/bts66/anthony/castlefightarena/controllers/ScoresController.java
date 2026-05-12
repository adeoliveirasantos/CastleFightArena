package bts66.anthony.castlefightarena.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bts66.anthony.castlefightarena.database.StatistiqueDAO;
import bts66.anthony.castlefightarena.model.Personnage;
import bts66.anthony.castlefightarena.model.Statistique;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static bts66.anthony.castlefightarena.App.changeScene;

public class ScoresController implements Initializable {

    @FXML
    private TableView<Statistique> scoresTableView;

    @FXML
    private TableColumn<Personnage, String> nomColumn;

    @FXML
    private TableColumn<Personnage, Integer> victoiresColumn;

    @FXML
    private TableColumn<Personnage, Integer> defaitesColumn;

    @FXML
    private TableColumn<Personnage, Integer> totalPVColumn;

    @FXML
    private TableColumn<Personnage, String> ratioColumn;

    @FXML
    private Label totalCombatsLabel;

    private ObservableList<Personnage> statistiquesData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurer les colonnes du TableView
        /*
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        victoiresColumn.setCellValueFactory(cellData -> cellData.getValue().victoiresProperty().asObject());
        defaitesColumn.setCellValueFactory(cellData -> cellData.getValue().defaitesProperty().asObject());
        totalPVColumn.setCellValueFactory(cellData -> cellData.getValue().totalPointsVieProperty().asObject());
        */

        // Colonne calculée pour le ratio
        ratioColumn.setCellValueFactory(cellData -> {
            Personnage stat = cellData.getValue();
            int victoires = stat.getStatistique().getVictoires();
            int defaites = stat.getStatistique().getDefaites();
            String ratio;
            if (defaites == 0) {
                ratio = victoires > 0 ? victoires + " - 0" : "Aucun combat";
            } else {
                ratio = String.format("%d - %d (%.2f%%)", victoires, defaites,
                        (victoires * 100.0 / (victoires + defaites)));
            }
            return new javafx.beans.property.SimpleStringProperty(ratio);
        });

        // Charger les données
        loadStatistiques();
    }

    private void loadStatistiques() {
        System.out.println("Chargement des statistiques...");

        List<Statistique> statistiques = new StatistiqueDAO().getAll();

        System.out.println("Nombre de statistiques reçues : " + statistiques.size());

        if (statistiques.isEmpty()) {
            System.out.println("ATTENTION : Aucune statistique n'a été récupérée de la base de données !");
        }

        // scoresTableView.setItems((ObservableList<Statistique>) statistiques);
        System.out.println("Statistiques chargées dans le TableView");

        // Calculer le total de combats
        int totalCombats = statistiques.stream()
                .mapToInt(stat -> stat.getVictoires() + stat.getDefaites())
                .sum() / 2; // Diviser par 2 car chaque combat compte pour 2 (1 victoire + 1 défaite)

        totalCombatsLabel.setText("Total de combats : " + totalCombats);

        System.out.println("Total de combats calculé : " + totalCombats);
    }

    @FXML
    protected void refreshAction() {
        loadStatistiques();
    }

    @FXML
    protected void onRetour(ActionEvent actionEvent) throws IOException {
        changeScene("main-view.fxml", actionEvent);
    }

    @FXML
    protected void onQuit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
