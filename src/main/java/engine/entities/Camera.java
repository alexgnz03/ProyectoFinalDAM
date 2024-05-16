package engine.entities;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

// La Camera sigue el movimiento del Player por el mapa
public class Camera {

    private AnimationTimer timer;

    public Camera(Pane root, Player player) {
        cameraStart(root, player);
    }

    public void cameraStart(Pane root, Player player) {
        Scale scale = new Scale(1.3, 1.3);
        root.getTransforms().add(scale);

        timer = new AnimationTimer() {
            public void handle(long now) {
                // Establecer el pivote de la transformación de escala en la posición del jugador
                scale.setPivotX(player.getX());
                scale.setPivotY(player.getY());
            }
        };
        this.timer.start();
    }
}