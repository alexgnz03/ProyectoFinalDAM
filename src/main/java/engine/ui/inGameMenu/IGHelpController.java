package engine.ui.inGameMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class IGHelpController {

    InGameMenu gameMenu;
    public void setGameMenu(InGameMenu gameMenu){
        this.gameMenu = gameMenu;
    }

    @FXML
    private ImageView backgroundImage;

    @FXML
    private Pane view;

    public void initialize() {

    }

    @FXML
    void volverAction(ActionEvent event) {
        gameMenu.cargarMenu();
    }
}
