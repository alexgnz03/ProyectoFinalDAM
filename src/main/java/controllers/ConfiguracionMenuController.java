package controllers;

import engine.EffectPlayer;
import engine.MusicPlayerSt;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfiguracionMenuController {
    private Stage stage;

    @FXML
    private Slider volumeSlider;

    public ConfiguracionMenuController() {
        // Constructor sin argumentos
    }

    public void initialize() {
        volumeSlider.setValue(MusicPlayerSt.getVolume() * 100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                MusicPlayerSt.setVolume(volumeSlider.getValue()/100);
            }
        });
    }

    @FXML
    void volverAction(ActionEvent event) {
        try {

            // Cargar la escena del menú principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/MainMenu.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del menú principal
            MainMenuController controller = loader.getController();

            // Pasar el Stage actual al controlador del menú principal
            controller.setStage(stage);

            // Establecer la escena del menú principal en el Stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
