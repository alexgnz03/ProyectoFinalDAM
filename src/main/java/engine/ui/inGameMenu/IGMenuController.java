package engine.ui.inGameMenu;

import engine.tienda.TiendaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    void configAction(ActionEvent event) {

    }

    @FXML
    void inventoryAction(ActionEvent event) {
        System.out.println("Mostrando inventario");
        gameMenu.cargarInventario();

    }

    @FXML
    void statsAction(ActionEvent event) {
        gameMenu.cargarStats();
    }

    @FXML
    void salirAction(ActionEvent event) {

    }

    @FXML
    void mostroDexAction(ActionEvent event) {
        gameMenu.cargarMostroDex();
    }

}

