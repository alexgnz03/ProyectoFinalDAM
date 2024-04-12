package controllers;

import engine.MusicPlayer;
import engine.combate.peleitas.GameOverController;
import exec.Main;
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
import javafx.stage.Stage;
import engine.world.Maps;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController {

    private Stage stage;
    private Maps maps = new Maps();
    private MusicPlayer musicPlayer;
    private MusicPlayer musicPlayerMundo;
    private Stage configuracionStage;
    private double volume;

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





    public void setStage(Stage stage) {
        this.stage = stage;

        stage.setOnCloseRequest(event -> {
            musicPlayer.stop();
        });
    }

    public void initialize() {
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

        musicPlayerMundo = new MusicPlayer("/Music/Doom.mp3");
    }

    @FXML
    void jugarAction() {
        musicPlayer.stop();
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

    public void setWorld(Maps maps) {
        this.maps = maps;
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
        controller.setWorld(new Maps());
    }

    public MusicPlayer getMusicPlayerMundo() {
        return musicPlayerMundo;
    }
}