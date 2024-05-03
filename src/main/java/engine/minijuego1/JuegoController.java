package engine.minijuego1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;


public class JuegoController {
    @FXML
    private Label palabraLabel;
    @FXML
    private TextField palabraTextField;
    @FXML
    private Label puntajeLabel;
    @FXML
    private Label tiempoLabel;

    private JuegoModelo modelo;
    private Timeline temporizador;
    private int tiempoRestante;

    public void initialize() {
        modelo = new JuegoModelo();
        nuevaPalabra();

        tiempoRestante = 60; // 60 segundos

        temporizador = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tiempoRestante--;
            if (tiempoRestante <= 0) {
                mostrarResultados();
            } else {
                tiempoLabel.setText("Tiempo restante: " + tiempoRestante + " segundos");
            }
        }));
        temporizador.setCycleCount(Timeline.INDEFINITE);
        temporizador.play();
    }

    @FXML
    public void comprobarPalabra() {
        String palabraIngresada = palabraTextField.getText();
        if (modelo.verificarPalabra(palabraIngresada)) {
            modelo.aumentarPuntaje();
        }
        puntajeLabel.setText("Puntaje: " + modelo.getPuntaje());
        nuevaPalabra();
        palabraTextField.clear();
    }

    private void nuevaPalabra() {
        modelo.nuevaPalabra();
        palabraLabel.setText(modelo.getPalabraActual());
    }

    private void mostrarResultados() {
        Stage stage = (Stage) palabraLabel.getScene().getWindow();
        stage.close(); // Cerrar la ventana actual

        // Mostrar los resultados en una nueva ventana
        // Aquí deberías abrir una nueva ventana o mostrar la puntuación de alguna manera
    }

    public Label getPalabraLabel() {
        return palabraLabel;
    }

    public void setPalabraLabel(Label palabraLabel) {
        this.palabraLabel = palabraLabel;
    }

    public TextField getPalabraTextField() {
        return palabraTextField;
    }

    public void setPalabraTextField(TextField palabraTextField) {
        this.palabraTextField = palabraTextField;
    }

    public Label getPuntajeLabel() {
        return puntajeLabel;
    }

    public void setPuntajeLabel(Label puntajeLabel) {
        this.puntajeLabel = puntajeLabel;
    }

    public JuegoModelo getModelo() {
        return modelo;
    }

    public void setModelo(JuegoModelo modelo) {
        this.modelo = modelo;
    }
}
