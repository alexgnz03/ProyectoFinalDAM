package engine.minijuego1;

import controllers.PantallaResultadosController;
import engine.world.Maps2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class JuegoController {
    Stage stage;
    Maps2 maps2 = new Maps2();
    public void setStage(Stage stage) {
        this.stage = stage;
    }

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
    private int dinero;

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
        temporizador.stop();
        dinero = modelo.getPuntaje()*10;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultados.fxml"));
        PantallaResultadosController controller = new PantallaResultadosController(modelo.getPuntaje(), dinero);
        controller.setStage(stage);
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Tienda");
        stage.setScene(scene);
        stage.show();
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
