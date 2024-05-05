package controllers;

import dbo.PlayerData;
import engine.minijuego1.JuegoModelo;
import engine.world.Maps2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class PantallaResultadosController {

    Stage stage;
    Maps2 maps2 = new Maps2();
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Label dineroText;

    @FXML
    private ImageView fondo;

    @FXML
    private Label gananciasLabel;

    @FXML
    private Label puntajeLabel;

    @FXML
    private Label puntuacionText;

    @FXML
    private Label euroText;

    @FXML
    private AnchorPane view;

    int puntuacion;
    int dinero;

    public PantallaResultadosController(int puntuacion, int dinero) {
        this.puntuacion = puntuacion;
        this.dinero = dinero;
    }

    public void initialize() {
        puntajeLabel.setText(String.valueOf(puntuacion));
        gananciasLabel.setText(String.valueOf(dinero));
    }

    @FXML
    void salirAction(ActionEvent event) {
        try {
            int stamina = PlayerData.cargarDato(6) - 25;
            int dineroFinal = PlayerData.cargarDato(7) + dinero;
            PlayerData.guardarDato(7, dineroFinal);
            if (PlayerData.cargarDato(6) > 0) {
                PlayerData.guardarDato(6, stamina);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        maps2.setStage(stage);
        maps2.trinidad02(stage);
        maps2.timerStart();
    }

}
