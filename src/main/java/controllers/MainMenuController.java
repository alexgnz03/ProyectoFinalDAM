package controllers;

import dbo.MonsterLoader;
import dbo.ObjetosData;
import dbo.PlayerData;
import engine.MusicPlayer;
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
    private MusicPlayer musicPlayer;
    private MusicPlayer musicPlayerMundo;
    private Stage configuracionStage;
    private double volume;

    ClassLoader classLoader = getClass().getClassLoader();
    URL resourceURL = classLoader.getResource("FondoMenu.mp4");
    String mediaSource = resourceURL.toExternalForm();
    Media media = new Media(mediaSource);
    MediaPlayer mediaPlayer = new MediaPlayer(media);

    private static final String[] CAMINAR_IMAGENES = {"Down1_HD.png", "Down2_HD.png", "Down3_HD.png"};
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
            musicPlayer.stop();
        });
    }

    public void initialize() {
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        fondoMedia.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        // Cargar las imágenes
        Image[] imagenes = new Image[CAMINAR_IMAGENES.length];
        for (int i = 0; i < CAMINAR_IMAGENES.length; i++) {
            imagenes[i] = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + CAMINAR_IMAGENES[i])));
        }

        // Crear la animación
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(DURACION_FRAME_MILLIS), event -> {
                    indiceImagenActual = (indiceImagenActual + 1) % CAMINAR_IMAGENES.length;
                    animacionIV.setImage(imagenes[indiceImagenActual]);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        musicPlayer = MusicPlayer.getInstance("/Music/MenuMusic.mp3");
        musicPlayer.play();

        musicPlayerMundo = new MusicPlayer("/Music/MainMusic.mp3");
    }

    @FXML
    void jugarAction() {
        musicPlayer.stop();
        mediaPlayer.stop();
        musicPlayerMundo.play();
        try {
            // Cargar la escena del menú de configuración
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/Introduccion.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del menú de configuración
            IntroduccionController controller = loader.getController();

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
            PlayerData.guardarDato(7, 1000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        musicPlayer.stop();
        mediaPlayer.stop();
        musicPlayerMundo.play();
        try {
            // Cargar la escena del menú de configuración
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/Introduccion.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del menú de configuración
            IntroduccionController controller = loader.getController();

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
    void acercaAction(ActionEvent event) {
        try {
            mediaPlayer.stop();
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
            mediaPlayer.stop();
            musicPlayer.stop();
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
    public void setVolume(double volume) {
        // Valida que el volumen esté dentro del rango permitido (entre 0 y 1)
        if (volume >= 0 && volume <= 1) {
            this.volume = volume;
            // Configurar el volumen del musicPlayer si ya ha sido inicializado
            musicPlayer.setVolume(volume);
            musicPlayerMundo.setVolume(volume);

        } else {
            // Si el volumen está fuera del rango, puedes lanzar una excepción o simplemente ignorar la llamada
            System.err.println("El volumen debe estar entre 0 y 1");
        }
    }

    public void mainMenuPantalla(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/MainMenu.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        MainMenuController controller = loader.getController();
        controller.setStage(stage);
        controller.setWorld(new Maps_BSalud());
    }

    public MusicPlayer getMusicPlayerMundo() {
        return musicPlayerMundo;
    }
}