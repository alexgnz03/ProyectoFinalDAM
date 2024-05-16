package engine.jobs.secretarytyping;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JuegoVista {
    private Stage stage;
    private Scene scene;
    private Label palabraLabel;
    private TextField palabraTextField;
    private Label puntajeLabel;
    private Button comenzarButton;
    private Button salirButton;

    public JuegoVista(Stage stage) {
        this.stage = stage;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        palabraLabel = new Label();
        palabraTextField = new TextField();
        puntajeLabel = new Label();
        comenzarButton = new Button("Comenzar");
        salirButton = new Button("Salir");

        VBox root = new VBox();
        root.getChildren().addAll(palabraLabel, palabraTextField, puntajeLabel, comenzarButton, salirButton);

        scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Juego de Escritura");
    }

    public void mostrar() {
        stage.show();
    }

    public void actualizarPalabra(String palabra) {
        palabraLabel.setText(palabra);
    }

    public String obtenerPalabraIngresada() {
        return palabraTextField.getText();
    }

    public void limpiarTextField() {
        palabraTextField.clear();
    }

    public void actualizarPuntaje(int puntaje) {
        puntajeLabel.setText("Puntaje: " + puntaje);
    }

    public void manejarFinJuego() {
        comenzarButton.setDisable(true);
        salirButton.setDisable(false);
    }

    public void agregarEventoComenzar(Runnable handler) {
        comenzarButton.setOnAction(e -> handler.run());
    }

    public void agregarEventoSalir(Runnable handler) {
        salirButton.setOnAction(e -> handler.run());
    }
}
