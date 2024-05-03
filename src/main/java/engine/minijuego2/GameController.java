package engine.minijuego2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    @FXML
    private Label labelTecla;
    @FXML
    private Label labelPuntuacion;

    private GameModel gameModel = new GameModel();
    private Timer timer = new Timer();
    private boolean esperandoTecla = false;

    public void initialize() {
        mostrarSiguienteTecla();
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

    private void actualizarPuntuacion() {
        labelPuntuacion.setText("Puntuación: " + gameModel.getPuntuacion());
    }
}
