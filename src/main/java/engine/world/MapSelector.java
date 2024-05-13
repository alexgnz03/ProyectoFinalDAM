package engine.world;

import engine.MusicPlayerSt;
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

    public void initialize() {
        double volumen;
        volumen = MusicPlayerSt.getVolume();
        MusicPlayerSt.play("/Music/mapselectorMusic.mp3");
        MusicPlayerSt.setVolume(volumen);
    }


    @FXML
    void bsaludAction(ActionEvent event) {
        Maps_BSalud mapsBSalud = new Maps_BSalud();

        mapsBSalud.setY(65);
        mapsBSalud.setX(550);
        mapsBSalud.musica();
        mapsBSalud.setStage(stage);
        mapsBSalud.paradaGuagua(stage);

    }

    @FXML
    void lalagunaAction(ActionEvent event) {
        Maps_LaLaguna mapsLaLaguna = new Maps_LaLaguna();

        mapsLaLaguna.setY(550);
        mapsLaLaguna.setX(115);
        mapsLaLaguna.musica();
        mapsLaLaguna.setStage(stage);
        mapsLaLaguna.intercambiador(stage);
    }

    @FXML
    void teresitasAction(ActionEvent event) {
        Maps_Teresitas mapsTeresitas = new Maps_Teresitas();

        mapsTeresitas.setY(550);
        mapsTeresitas.setX(410);
        mapsTeresitas.musica();
        mapsTeresitas.setStage(stage);
        mapsTeresitas.teresitas01(stage);
    }

    @FXML
    void aeropuertoAction(ActionEvent event) {
        Maps_LaLaguna mapsLaLaguna = new Maps_LaLaguna();

        double volumen;
        volumen = MusicPlayerSt.getVolume();
        MusicPlayerSt.play("/Music/airportMusic.mp3");
        MusicPlayerSt.setVolume(volumen);

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
