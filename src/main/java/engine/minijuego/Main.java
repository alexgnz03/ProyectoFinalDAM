package engine.minijuego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tablero.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Minijuego de Dianas");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();

        MinijuegoController controlador = loader.getController();
        controlador.actualizarTiempo();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
