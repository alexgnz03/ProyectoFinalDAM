package controllers;

import engine.world.Maps;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class IntroduccionController {
    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;

    }

    private Maps maps = new Maps();

    @FXML
    void jugarAction(ActionEvent event) {
        maps.setY(65);
        maps.setX(550);
        maps.setStage(stage);
        maps.paradaGuagua(stage);
    }


}
