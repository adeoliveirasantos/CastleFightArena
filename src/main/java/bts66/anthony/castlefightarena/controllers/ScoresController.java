package bts66.anthony.castlefightarena.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bts66.anthony.castlefightarena.database.CombatDAO;
import bts66.anthony.castlefightarena.model.StatistiquePersonnage;
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
    private TableView<StatistiquePersonnage> scoresTableView;

    @FXML
    private TableColumn<StatistiquePersonnage, String> nomColumn;

    @FXML
    private TableColumn<StatistiquePersonnage, Integer> victoiresColumn;

    @FXML
    private TableColumn<StatistiquePersonnage, Integer> defaitesColumn;

    @FXML
    private TableColumn<StatistiquePersonnage, Integer> totalPVColumn;

    @FXML
    private TableColumn<StatistiquePersonnage, String> ratioColumn;

    @FXML
    private Label totalCombatsLabel;

    private ObservableList<StatistiquePersonnage> statistiquesData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurer les colonnes du TableView
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        victoiresColumn.setCellValueFactory(cellData -> cellData.getValue().victoiresProperty().asObject());
        defaitesColumn.setCellValueFactory(cellData -> cellData.getValue().defaitesProperty().asObject());
        totalPVColumn.setCellValueFactory(cellData -> cellData.getValue().totalPointsVieProperty().asObject());

        // Colonne calculée pour le ratio
        ratioColumn.setCellValueFactory(cellData -> {
            StatistiquePersonnage stat = cellData.getValue();
            int victoires = stat.getVictoires();
            int defaites = stat.getDefaites();
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

        List<StatistiquePersonnage> statistiques = CombatDAO.getStatistiques();

        System.out.println("Nombre de statistiques reçues : " + statistiques.size());

        if (statistiques.isEmpty()) {
            System.out.println("ATTENTION : Aucune statistique n'a été récupérée de la base de données !");
        }

        statistiquesData = FXCollections.observableArrayList(statistiques);
        scoresTableView.setItems(statistiquesData);

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
