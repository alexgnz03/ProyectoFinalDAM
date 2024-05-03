package engine.minijuego1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Launch extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/juego.fxml"));
        JuegoController controller = new JuegoController();
        loader.setController(controller);
        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Juego de Escritura");
        primaryStage.show();

        TextField textField = controller.getPalabraTextField();
        textField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                controller.comprobarPalabra();
            }
        });


    }

    public static void main(String[] args) {
        launch(args);
    }
}
