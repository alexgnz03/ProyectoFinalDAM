package engine.world;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MapSelector {

    @FXML
    private ImageView backImage;

    @FXML
    private AnchorPane view;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    void maps1Action(ActionEvent event) {
        Maps maps = new Maps();

        maps.setY(65);
        maps.setX(550);
        maps.setStage(stage);
        maps.paradaGuagua(stage);

    }

    @FXML
    void maps2Action(ActionEvent event) {
        Maps2 maps2 = new Maps2();

        maps2.setY(65);
        maps2.setX(550);
        maps2.setStage(stage);
        maps2.intercambiador(stage);
    }

    //Getters and Setters

    public Stage getStage() {
        return stage;
    }

    public ImageView getBackImage() {
        return backImage;
    }

    public void setBackImage(ImageView backImage) {
        this.backImage = backImage;
    }

    public AnchorPane getView() {
        return view;
    }

    public void setView(AnchorPane view) {
        this.view = view;
    }
}
