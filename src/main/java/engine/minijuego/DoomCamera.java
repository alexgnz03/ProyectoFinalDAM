package engine.minijuego;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.input.MouseEvent;

public class DoomCamera {

    private AnimationTimer timer;
    private ImageView manoDoom;

    Pane pane = new Pane();

    public DoomCamera(BorderPane root) {
        cameraStart(root);
    }

    public void cameraStart(BorderPane root) {
        Scale scale = new Scale(1.15, 1.15);
        root.getTransforms().add(scale);

        root.addEventFilter(MouseEvent.ANY, event -> {
            Node target = (Node) event.getTarget();
            // Verificar si el elemento afectado por el evento es diferente de manoDoom
            if (!target.equals(manoDoom)) {
                // Establecer el pivote de la transformación de escala en la posición del ratón
                scale.setPivotX(event.getX());
                scale.setPivotY(event.getY());
            }
        });
    }

    // Método para establecer la imagen de la manoDoom
    public void setManoDoom(ImageView manoDoom) {
        this.manoDoom = manoDoom;
    }
}