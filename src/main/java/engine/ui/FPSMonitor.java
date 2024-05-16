package engine.ui;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Esta clase es puramente para el testeo, con el método de abajo podemos ver los fps del juego
public class FPSMonitor {

    public FPSMonitor(Stage stage) {
        mostrarFPS(stage);
    }

    // Método para mostrar los FPS
    public void mostrarFPS(Stage stage) {
        // Crear un objeto Text para mostrar los FPS
        Text fpsText = new Text();
        fpsText.setFill(Color.WHITE);
        fpsText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        // Crear un nuevo HBox para contener el Text
        HBox fpsBox = new HBox(fpsText);
        fpsBox.setPadding(new Insets(10));
        fpsBox.setAlignment(Pos.TOP_RIGHT);

        // Agregar el HBox al root de la escena
        ((Pane) stage.getScene().getRoot()).getChildren().add(fpsBox);

        // Crear un AnimationTimer para actualizar los FPS
        AnimationTimer fpsTimer = new AnimationTimer() {
            private long lastTime = 0;
            private int frames = 0;

            @Override
            public void handle(long now) {
                // Calcular los FPS
                frames++;
                if (now - lastTime >= 1_000_000_000) {
                    double fps = frames / ((now - lastTime) / 1_000_000_000.0);
                    fpsText.setText(String.format("FPS: %.2f", fps));
                    lastTime = now;
                    frames = 0;
                }
            }
        };

        // Iniciar el AnimationTimer
        fpsTimer.start();
    }
}
