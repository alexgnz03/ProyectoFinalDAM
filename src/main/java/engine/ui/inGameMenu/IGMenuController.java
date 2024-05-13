package engine.ui.inGameMenu;

import dbo.PlayerData;
import engine.tienda.TiendaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class IGMenuController {

    private InGameMenu gameMenu;

    public void setGameMenu(InGameMenu gameMenu){
        this.gameMenu = gameMenu;
    }

    Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;}

    @FXML
    private Pane view;

    @FXML
    private ImageView wallpaper;

    @FXML
    private Label dineroLabel;

    public void initialize() {
        try {
            dineroLabel.setText("Dinero: " + PlayerData.cargarDato(7) + "€");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void inventoryAction(ActionEvent event) {
        gameMenu.cargarInventario();

    }

    @FXML
    void statsAction(ActionEvent event) {
        gameMenu.cargarStats();
    }

    @FXML
    void cvAction(ActionEvent event) {
        gameMenu.cargarCV();
    }

    @FXML
    void mostroDexAction(ActionEvent event) {
        gameMenu.cargarMostroDex();
    }

    @FXML
    void configAction(ActionEvent event) {
        gameMenu.cargarConfig();
    }

    @FXML
    void ayudaAction(ActionEvent event) {
        gameMenu.cargarAyuda();
    }

}

