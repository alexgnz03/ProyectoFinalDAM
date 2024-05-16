package engine.jobs.mcclicker;

import engine.menu.PantallaResultadosController;
import engine.MusicPlayerSt;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ClickerController {
    Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private Label clickCountLabel;
    @FXML
    private Label timerLabel;

    private ClickerModel model;

    private int tiempoRestante;
    private Timeline temporizador;
    private int dinero;
    private int puntuacion;
    public void initialize() {
        double volumen;
        volumen = MusicPlayerSt.getVolume();
        MusicPlayerSt.play("/Music/mcdontMusic.mp3");
        MusicPlayerSt.setVolume(volumen);

        model = new ClickerModel(this);
        tiempoRestante = 60; // 60 segundos

        temporizador = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tiempoRestante--;
            if (tiempoRestante <= 0) {
                mostrarResultados();
            } else {
                timerLabel.setText("Tiempo restante: " + tiempoRestante + " segundos");
            }
        }));
        temporizador.setCycleCount(Timeline.INDEFINITE);
        temporizador.play();
    }

    @FXML
    public void handleClick() {
        model.incrementClickCount();
    }

    public void updateClickCount(int count) {
        clickCountLabel.setText("Clicks: " + count);
        this.puntuacion = count;
    }

    private void mostrarResultados() {
        temporizador.stop();
        dinero = puntuacion/10;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Jobs/resultados.fxml"));
        PantallaResultadosController controller = new PantallaResultadosController(puntuacion, dinero, 1);
        controller.setStage(stage);
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Resultados");
        stage.setScene(scene);
        stage.show();
    }
}
