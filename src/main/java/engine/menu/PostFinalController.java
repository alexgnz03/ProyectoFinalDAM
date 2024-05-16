package engine.menu;

import engine.MusicPlayerSt;
import engine.world.Maps_BSalud;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PostFinalController {
    private Stage stage;
    private Maps_BSalud mapsBSalud = new Maps_BSalud();

    private MainMenuController menu = new MainMenuController();

    public void initialize() {

    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }
    @FXML
    void jugarAction(ActionEvent event) {
        Platform.exit();
    }

    public void setWorld(Maps_BSalud mapsBSalud) {
        this.mapsBSalud = mapsBSalud;
    }

    public void musica(){
        double volumen;
        volumen = MusicPlayerSt.getVolume();
        MusicPlayerSt.play("/Music/lalagunaMusic.mp3");
        MusicPlayerSt.setVolume(volumen);
    }
}
