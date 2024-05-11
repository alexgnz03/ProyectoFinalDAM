package controllers;

import engine.world.Maps_BSalud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class IntroduccionController {
    private Stage stage;
    private Maps_BSalud mapsBSalud = new Maps_BSalud();

    public void setStage(Stage stage) {
        this.stage = stage;

    }
    @FXML
    void jugarAction(ActionEvent event) {
        mapsBSalud.setY(65);
        mapsBSalud.setX(550);
        mapsBSalud.setStage(stage);
        mapsBSalud.paradaGuagua(stage);
    }
}
