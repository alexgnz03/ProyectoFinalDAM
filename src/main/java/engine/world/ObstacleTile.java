package engine.world;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ObstacleTile extends Rectangle {
    public ObstacleTile(double width, double height, double x, double y) {
        super(width, height);
        this.setFill(Color.rgb(0, 0, 0, 0.0));
        this.setX(x);
        this.setY(y);
    }
}

