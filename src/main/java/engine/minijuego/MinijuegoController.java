package engine.minijuego;

import engine.MusicPlayer;
import engine.world.Maps;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import net.sf.jasperreports.engine.*;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class MinijuegoController implements Initializable {

    @FXML
    private GridPane maingrid;

    private Puntuacion puntuacion;
    private Diana dianaActual;

    private int dianasAcertadas;

    Stage stage;
    Maps mapsInstance = new Maps();

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
    private BorderPane view;

    @FXML
    private ImageView manoDoom;


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
        Image fondoImagen = new Image("doomFondo.png");
        BackgroundImage backgroundImage = new BackgroundImage(fondoImagen, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        // Crea un nuevo fondo
        Background fondo = new Background(backgroundImage);

        // Establece el nuevo fondo en el BorderPane
        view.setBackground(fondo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        puntuacion = new Puntuacion();
        puntosLabel.setText("Puntos: " + puntuacion.getPuntos());
        tiempoLabel.setText("Tiempo: " + tiempoRestante + " s"); // Mostrar tiempo inicial
        manoDoom.setImage(doomGun);
        iniciarMinijuego();
        cambiarFondo();

    }


    private void iniciarMinijuego() {
        // Resto del código

        dianaActual = generarDiana();
        mostrarDiana();

        // Actualizar el temporizador en la interfaz gráfica
        Timeline temporizadorTimeline = new Timeline(new KeyFrame(Duration.seconds(1.25), event -> {
            tiempoRestante--;
            tiempoLabel.setText("Tiempo: " + tiempoRestante + " s");
            if (tiempoRestante <= 0) {
                mapsInstance.arcade(stage);
                int dianasAcertadas = obtenerCantidadDianasAcertadas();
                generarInforme(puntuacion.getPuntos(), dianasAcertadas);
            }
        }));
        temporizadorTimeline.setCycleCount(tiempoRestante); // Establecer la duración del temporizador
        temporizadorTimeline.play();
    }

    private int obtenerCantidadDianasAcertadas() {
        return dianasAcertadas;
    }

    private void finDelJuego() {
        musicPlayer.stop();
        mapsInstance.arcade(stage);
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
        puntuacion.sumarPuntos(50);
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

            // Eliminar la ImageView y su contenedor asociado con Platform.runLater
            Platform.runLater(() -> {
                stackPane.getChildren().remove(imageView);
                maingrid.getChildren().remove(stackPane);
            });
        }
        // Actualizar la lista de posiciones ocupadas
        posicionesOcupadas.remove(new Pair<>(dianaActual.getColumna(), dianaActual.getFila()));

    }

    public static void generarInforme(int puntuacion, int dianasAcertadas) {
        try {
            // Cargar el diseño del informe desde un archivo .jrxml
            InputStream jrxmlInputStream = MinijuegoController.class.getClassLoader().getResourceAsStream("reports/Diana.jrxml");

            // Compilar el diseño del informe a un objeto JasperReport
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);

            // Recopilar los datos necesarios para el informe
            Map<String, Object> parameters = new HashMap<>();
            Date fechaGeneracion = new Date();
            parameters.put("puntuacion", puntuacion);
            parameters.put("dianasAcertadas", dianasAcertadas);
            parameters.put("fecha_generacion", fechaGeneracion);

            // Llenar y generar el informe con parámetros y utilizando un JREmptyDataSource para evitar ambigüedades
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Definir la ruta del archivo PDF en el escritorio
            String userHome = System.getProperty("user.home");
            String pathToFile = userHome + "/Desktop/puntuacion.pdf";

            // Exportar el informe a un archivo PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, pathToFile);

        } catch (JRException e) {
            e.printStackTrace();
        }
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

        Image imagenDiana = new Image("/diana.png");
        ImageView imageView = new ImageView(imagenDiana);
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

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
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
}