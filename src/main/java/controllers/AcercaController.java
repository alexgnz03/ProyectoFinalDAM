package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AcercaController {

    private Stage stage;


    @FXML
    void volverAction(ActionEvent event) throws IOException {
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
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
