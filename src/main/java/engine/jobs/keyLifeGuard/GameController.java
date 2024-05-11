package engine.jobs.keyLifeGuard;

import controllers.PantallaResultadosController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Label tiempoLabel;
    @FXML
    private Label labelTecla;
    @FXML
    private Label labelPuntuacion;

    private GameModel gameModel = new GameModel();
    private Timer timer = new Timer();
    private boolean esperandoTecla = false;

    private int dinero;
    private int tiempoRestante;
    private Timeline temporizador;
    public void initialize() {
        mostrarSiguienteTecla();
        tiempoRestante = 60; // 60 segundos

        temporizador = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tiempoRestante--;
            if (tiempoRestante <= 0) {
                mostrarResultados();
            } else {
                tiempoLabel.setText("Tiempo restante: " + tiempoRestante);
            }
        }));
        temporizador.setCycleCount(Timeline.INDEFINITE);
        temporizador.play();
    }

    private void mostrarSiguienteTecla() {
        gameModel.generarSiguienteTecla();
        labelTecla.setText(gameModel.getTeclaActual());
    }

    public void handleKeyPressed(javafx.scene.input.KeyEvent event) {
        if (esperandoTecla) return;

        String teclaPresionada = event.getCode().toString();
        if (teclaPresionada.equals(gameModel.getTeclaActual())) {
            gameModel.aumentarPuntuacion();
            actualizarPuntuacion();
            esperarTecla();
        } else {
            gameModel.disminuirPuntuacion();
            actualizarPuntuacion();
        }
        System.out.println("Se está presionando la tecla");
    }

    private void esperarTecla() {
        esperandoTecla = true;
        labelTecla.setText(""); // Vaciar el texto de la etiqueta
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    esperandoTecla = false;
                    mostrarSiguienteTecla();
                });
            }
        }, 250);
    }

    private void mostrarResultados() {
        temporizador.stop();
        dinero = gameModel.getPuntuacion()*10;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultados.fxml"));
        PantallaResultadosController controller = new PantallaResultadosController(gameModel.getPuntuacion(), dinero, 2);
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

    private void actualizarPuntuacion() {
        labelPuntuacion.setText("Puntuación: " + gameModel.getPuntuacion());
    }
}
