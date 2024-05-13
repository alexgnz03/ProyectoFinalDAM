package controllers;

import engine.EffectPlayer;
import engine.MusicPlayerSt;
import engine.world.Maps_BSalud;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    public void postFinalPantalla(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/PostFinal.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        PostFinalController controller = loader.getController();
        controller.setStage(stage);
        controller.setWorld(new Maps_BSalud());
    }

    public void MainMenu(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/MainMenu.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        MainMenuController controller = loader.getController();
        controller.setStage(stage);
        controller.setWorld(new Maps_BSalud());
    }

    public void musica(){
        double volumen;
        volumen = MusicPlayerSt.getVolume();
        MusicPlayerSt.play("/Music/lalagunaMusic.mp3");
        MusicPlayerSt.setVolume(volumen);
    }
}
