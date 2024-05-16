package engine.ui.inGameMenu;

import engine.dbo.CVData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class IGCVController {

    InGameMenu gameMenu;
    public void setGameMenu(InGameMenu gameMenu){
        this.gameMenu = gameMenu;
    }

    @FXML
    private ImageView backgroundImage;

    @FXML
    private Label doomStat;

    @FXML
    private Label mcdontStat;

    @FXML
    private Label secretarioStat;

    @FXML
    private Label socorristaStat;

    @FXML
    private Pane view;

    public void initialize() {
        try {
            mcdontStat.setText(String.valueOf(CVData.cargarDato(0)));
            socorristaStat.setText(String.valueOf(CVData.cargarDato(1)));
            secretarioStat.setText(String.valueOf(CVData.cargarDato(2)));
            doomStat.setText(String.valueOf(CVData.cargarDato(3)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void volverAction(ActionEvent event) {
        gameMenu.cargarMenu();
    }
}
