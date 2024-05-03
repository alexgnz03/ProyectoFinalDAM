package engine.minijuego2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launch extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameView.fxml"));
        GameController controller = new GameController();
        loader.setController(controller);
        Parent root = loader.load();

        Scene scene = new Scene(root, 300, 300);
        scene.setOnKeyPressed(controller::handleKeyPressed);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Juego de Escritura");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
