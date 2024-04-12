package engine.ui.inGameMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class IGMenuController {

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

    }

    @FXML
    void salirAction(ActionEvent event) {

    }

}

