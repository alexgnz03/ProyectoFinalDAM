package engine.ui.inGameMenu;

import dbo.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class IGStatsController {

    InGameMenu gameMenu;
    public void setGameMenu(InGameMenu gameMenu){
        this.gameMenu = gameMenu;
    }

    @FXML
    private Label atMagicoLabel;

    @FXML
    private Label atMagicoStat;

    @FXML
    private Label ataqueLabel;

    @FXML
    private Label ataqueStat;

    @FXML
    private ImageView backgroundImage;

    @FXML
    private Label defMagicaLabel;

    @FXML
    private Label defMagicaStat;

    @FXML
    private Label defensaStat;

    @FXML
    private Label defnsaLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Pane view;

    public void initialize() {
        try {
            ataqueStat.setText(String.valueOf(PlayerData.cargarDato(1)));
            atMagicoStat.setText(String.valueOf(PlayerData.cargarDato(3)));
            defensaStat.setText(String.valueOf(PlayerData.cargarDato(2)));
            defMagicaStat.setText(String.valueOf(PlayerData.cargarDato(4)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void volverAction(ActionEvent event) {
        gameMenu.cargarMenu();
    }
}
