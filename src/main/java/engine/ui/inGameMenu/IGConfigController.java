package engine.ui.inGameMenu;

import engine.MusicPlayerSt;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class IGConfigController {

    InGameMenu gameMenu;
    public void setGameMenu(InGameMenu gameMenu){
        this.gameMenu = gameMenu;
    }

    @FXML
    private ImageView backgroundImage;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Pane view;

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
        gameMenu.cargarMenu();
    }
}
