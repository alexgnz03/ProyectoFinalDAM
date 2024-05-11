package engine.world;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    void bsaludAction(ActionEvent event) {
        Maps_BSalud mapsBSalud = new Maps_BSalud();

        mapsBSalud.setY(65);
        mapsBSalud.setX(550);
        mapsBSalud.setStage(stage);
        mapsBSalud.paradaGuagua(stage);

    }

    @FXML
    void lalagunaAction(ActionEvent event) {
        Maps_LaLaguna mapsLaLaguna = new Maps_LaLaguna();

        mapsLaLaguna.setY(65);
        mapsLaLaguna.setX(550);
        mapsLaLaguna.setStage(stage);
        mapsLaLaguna.intercambiador(stage);
    }

    @FXML
    void teresitasAction(ActionEvent event) {
        Maps_Teresitas mapsTeresitas = new Maps_Teresitas();

        mapsTeresitas.setY(205);
        mapsTeresitas.setX(350);
        mapsTeresitas.setStage(stage);
        mapsTeresitas.teresitas01(stage);
    }

    @FXML
    void aeropuertoAction(ActionEvent event) {
        Maps_LaLaguna mapsLaLaguna = new Maps_LaLaguna();

        mapsLaLaguna.setY(715);
        mapsLaLaguna.setX(375);
        mapsLaLaguna.setStage(stage);
        mapsLaLaguna.aeropuerto(stage);
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
