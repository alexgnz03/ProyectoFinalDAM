package engine.menu;

import engine.dbo.CVData;
import engine.dbo.MonsterLoader;
import engine.dbo.ObjetosData;
import engine.dbo.PlayerData;
import engine.MusicPlayerSt;
import engine.world.Maps_LaLaguna;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import engine.world.Maps_BSalud;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainMenuController {

    private Stage stage;
    private Maps_BSalud mapsBSalud = new Maps_BSalud();

    private Stage configuracionStage;

    ClassLoader classLoader = getClass().getClassLoader();
    URL resourceURL = classLoader.getResource("Menu/FondoMenu.mp4");
    String mediaSource = resourceURL.toExternalForm();

    private static final String[] CAMINAR_IMAGENES = {"Player/Down1_HD.png", "Player/Down2_HD.png", "Player/Down3_HD.png"};
    private static final int DURACION_FRAME_MILLIS = 200; // Duración de cada frame en milisegundos
    private int indiceImagenActual = 0;

    @FXML
    private Button acercaButton;

    @FXML
    private ImageView animacionIV;

    @FXML
    private Button configuracionButton;

    @FXML
    private Button jugarButton;

    @FXML
    private Button salirButton;

    @FXML
    private MediaView fondoMedia;



    public void setStage(Stage stage) {
        this.stage = stage;

        stage.setOnCloseRequest(event -> {
            MusicPlayerSt.stop();
        });
    }

    public void initialize() {
        // Cargar las imágenes
        Image[] imagenes = new Image[CAMINAR_IMAGENES.length];
        for (int i = 0; i < CAMINAR_IMAGENES.length; i++) {
            imagenes[i] = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + CAMINAR_IMAGENES[i])));
        }

        // Crear la animación que sale al lado de mi nombre
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(DURACION_FRAME_MILLIS), event -> {
                    indiceImagenActual = (indiceImagenActual + 1) % CAMINAR_IMAGENES.length;
                    animacionIV.setImage(imagenes[indiceImagenActual]);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Media media = new Media(mediaSource);

        // Crear MediaPlayer
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        fondoMedia.setMediaPlayer(mediaPlayer);

        if(!PlayerData.existeArchivo()){
            jugarButton.setDisable(true);
        }

    }

    @FXML
    void jugarAction() {
        Maps_LaLaguna maps = new Maps_LaLaguna();
        maps.setY(430);
        maps.setX(315);
        maps.musica();
        maps.setStage(stage);
        maps.casa(stage);

    }

    @FXML
    void nuevoJugarAction(ActionEvent event) {
        ObjetosData objetosData = new ObjetosData();
        objetosData.eliminarTablas();
        objetosData.crearTablas();
        objetosData.insertarDatos();

        MonsterLoader monster = new MonsterLoader();
        monster.eliminarTablas();
        monster.crearTablas();
        monster.insertarRegistros();

        try {
            PlayerData.guardarDato(0, 100);
            PlayerData.guardarDato(1, 10);
            PlayerData.guardarDato(2, 5);
            PlayerData.guardarDato(3, 10);
            PlayerData.guardarDato(4, 5);
            PlayerData.guardarDato(5, 5);
            PlayerData.guardarDato(6, 100);
            PlayerData.guardarDato(7, 0);

            CVData.guardarDato(0, 0);
            CVData.guardarDato(1, 0);
            CVData.guardarDato(2, 0);
            CVData.guardarDato(3, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            // Cargar la escena de la intro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/Introduccion.fxml"));
            IntroduccionController controller = new IntroduccionController(1);
            controller.setStage(stage);
            loader.setController(controller);
            Parent root = loader.load();
            controller.setStage(stage);
            Scene scene = new Scene(root, 800, 800);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void acercaAction(ActionEvent event) {
        try {
            // Cargar la escena del menú de configuración
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/AcercaMenu.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del menú de configuración
            AcercaController controller = loader.getController();

            // Pasar el Stage actual al controlador del menú de configuración
            controller.setStage(stage);

            // Establecer la escena del menú de configuración en el Stage actual
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void configuracionAction(ActionEvent event) {
        try {
            // Cargar la escena del menú de configuración
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/Configuracion.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del menú de configuración
            ConfiguracionMenuController controller = loader.getController();

            // Pasar el Stage actual al controlador del menú de configuración
            controller.setStage(stage);

            // Establecer la escena del menú de configuración en el Stage actual
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void salirAction(ActionEvent event) {
        Stage stage = (Stage) salirButton.getScene().getWindow();
        stage.close();
    }

    public void setWorld(Maps_BSalud mapsBSalud) {
        this.mapsBSalud = mapsBSalud;
    }

}