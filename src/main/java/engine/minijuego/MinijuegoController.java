package engine.minijuego;

import engine.MusicPlayer;
import engine.world.Maps_BSalud;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MinijuegoController implements Initializable {

    @FXML
    private GridPane maingrid;

    @FXML
    private BorderPane content;

    @FXML
    private ImageView barraGris;

    private Puntuacion puntuacion;
    private Diana dianaActual;

    private int dianasAcertadas;

    Stage stage;
    Maps_BSalud mapsBSaludInstance = new Maps_BSalud();

    private MusicPlayer musicPlayer;

    @FXML
    private Label tiempoLabel;

    @FXML
    public void actualizarTiempo() {
        tiempoLabel.setText("Tiempo: " + tiempoRestante + " s");
    }
    @FXML
    private Label puntosLabel;

    @FXML
    private Pane view;

    private BorderPane doomView = new BorderPane();

    private ImageView manoDoom = new ImageView(new Image("DOOM_1_Pistol_viewmodel.png"));

    private int tiempoRestante = 60; // 60 segundos

    public MinijuegoController() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private List<Pair<Integer, Integer>> posicionesOcupadas = new ArrayList<>();

    Image doomGun = new Image("DOOM_1_Pistol_viewmodel.png");
    public void cambiarFondo() {
        // Crea una nueva imagen de fondo (ajusta la ruta según tu proyecto)
        BackgroundSize size = new BackgroundSize(-1, -1, false, false, false, false);
        Image fondoImagen = new Image("doomFondo.png");
        BackgroundImage backgroundImage = new BackgroundImage(fondoImagen, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);

        // Crea un nuevo fondo
        Background fondo = new Background(backgroundImage);

        // Establece el nuevo fondo en el BorderPane
        content.setBackground(fondo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        manoDoom.setX(310);
//        manoDoom.setY(530);
        manoDoom.setFitWidth(196);
        manoDoom.setFitHeight(250);
        apano();
        puntuacion = new Puntuacion();
        puntosLabel.setText("Puntos: " + puntuacion.getPuntos());
        tiempoLabel.setText("Tiempo: " + tiempoRestante + " s"); // Mostrar tiempo inicial
        manoDoom.setImage(doomGun);
        DoomCamera camera = new DoomCamera(content);
        camera.setManoDoom(manoDoom);
        iniciarMinijuego();
        cambiarFondo();

    }


    private void iniciarMinijuego() {
        // Resto del código

        dianaActual = generarDiana();

        // Actualizar el temporizador en la interfaz gráfica
        Timeline temporizadorTimeline = new Timeline(new KeyFrame(Duration.seconds(1.25), event -> {
            tiempoRestante--;
            tiempoLabel.setText("Tiempo: " + tiempoRestante + " s");
            if (tiempoRestante <= 0) {
                finDelJuego();
            }
        }));
        temporizadorTimeline.setCycleCount(tiempoRestante); // Establecer la duración del temporizador
        temporizadorTimeline.play();
    }

    private int obtenerCantidadDianasAcertadas() {
        return dianasAcertadas;
    }

    private void finDelJuego() {
        double habilidad = Math.ceil((double) puntuacion.getPuntos() / 20.0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultadosDoom.fxml"));
        FinalScreen controller = new FinalScreen(puntuacion.getPuntos(), (int) habilidad);
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


    private void mostrarDiana() {
        generarDiana();
    }


    private void clicEnDiana() {
        Image doomGun = new Image("DOOM_1_Pistol_viewmodel.png");
        Image doomGun2 = new Image("DOOM_1_Pistol_viewmodel2.png");
        manoDoom.setImage(doomGun2);
        MusicPlayer efectos;
        efectos = new MusicPlayer("/Effects/DoomPistol.mp3");
        efectos.play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.75), event -> {
            efectos.stop();
        }));
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(0.25), event -> {
            manoDoom.setImage(doomGun);
        }));
        timeline.play();
        timeline2.play();
        puntuacion.sumarPuntos(1);
        puntosLabel.setText("Puntos: " + puntuacion.getPuntos());

        dianasAcertadas++;

        eliminarDiana();
    }

    private void eliminarDiana() {
        StackPane stackPane = obtenerStackPaneDianaActual();

        // Verificar si la diana existe en el GridPane
        if (stackPane != null) {
            // Obtener la ImageView dentro del StackPane
            ImageView imageView = (ImageView) stackPane.getChildren().get(0);

            // Eliminar la ImageView y su contenedor asociado
            stackPane.getChildren().remove(imageView);
            maingrid.getChildren().remove(stackPane);
        }
        // Actualizar la lista de posiciones ocupadas
        posicionesOcupadas.remove(new Pair<>(dianaActual.getColumna(), dianaActual.getFila()));
    }



    private StackPane obtenerStackPaneDianaActual() {
        // Obtener el StackPane asociado a la diana actual desde el GridPane
        for (Node node : maingrid.getChildren()) {
            if (node instanceof StackPane) {
                return (StackPane) node;
            }
        }
        return null;
    }


    private Diana generarDiana() {
        dianaActual = new Diana();

        // Asegurarse de que siempre haya al menos una diana en pantalla
        if (posicionesOcupadas.size() >= maingrid.getChildren().size()) {
            posicionesOcupadas.clear();  // Limpiar las posiciones ocupadas si se ocuparon todas las celdas
        }

        // Verificar si la posición está ocupada
        while (posicionesOcupadas.contains(new Pair<>(dianaActual.getColumna(), dianaActual.getFila()))) {
            dianaActual = new Diana();  // Generar una nueva diana si la posición está ocupada
        }

        ImageView imageView = new ImageView(getClass().getResource("/demon.gif").toExternalForm());
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);

        // Agregar evento de clic a la diana
        imageView.setOnMouseClicked(event -> clicEnDiana());

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(imageView);

        // Agregar la diana al GridPane
        maingrid.add(stackPane, dianaActual.getColumna(), dianaActual.getFila());

        // Centrar la imagen en la posición del GridPane
        GridPane.setHalignment(stackPane, HPos.CENTER);
        GridPane.setValignment(stackPane, VPos.CENTER);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Eliminar la ImageView y su contenedor asociado
            stackPane.getChildren().remove(imageView);
            maingrid.getChildren().remove(stackPane);

            // Generar la siguiente diana después de que la actual desaparezca
            generarNuevaDiana();
        }));
        timeline.play();

        return dianaActual;
    }


    private int calcularVelocidad() {
        // Puedes ajustar la lógica según tus necesidades
        int velocidadBase = 6;  // Velocidad base en segundos
        int velocidad = Math.max(1, velocidadBase - puntuacion.getPuntos() / 50);  // Ajustar según la puntuación
        return velocidad;
    }

    private void generarNuevaDiana() {
        mostrarDiana();
    }

    private void apano(){
        content.setPrefSize(800, 800);
        doomView.setPrefSize(196, 250);
        view.getChildren().add(doomView);
        view.setPrefSize(800, 800);
        doomView.getChildren().add(manoDoom);
//        doomView.toFront();
        doomView.setLayoutX(310);
        doomView.setLayoutY(550);
    }
}