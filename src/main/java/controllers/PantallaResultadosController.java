package controllers;

import dbo.PlayerData;
import engine.world.Maps_LaLaguna;
import engine.world.Maps_BSalud;
import engine.world.Maps_Teresitas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PantallaResultadosController {

    Stage stage;
    Maps_LaLaguna mapsLaLaguna = new Maps_LaLaguna();
    Maps_BSalud mapsBSalud = new Maps_BSalud();
    Maps_Teresitas mapsTeresitas = new Maps_Teresitas();
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
    int i;

    public PantallaResultadosController(int puntuacion, int dinero, int i) {
        this.puntuacion = puntuacion;
        this.dinero = dinero;
        this.i = i;
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
        switch(i){
            case 1:
                mapsLaLaguna.setStage(stage);
                mapsLaLaguna.setX(315);
                mapsLaLaguna.setY(580);
                mapsLaLaguna.mcdont(stage);
                mapsLaLaguna.timerStart();
                break;
            case 2:
                mapsTeresitas.setStage(stage);
                mapsTeresitas.teresitas06(stage);
                mapsTeresitas.timerStart();
                break;
            case 3:
                mapsBSalud.setStage(stage);
                mapsBSalud.lobbyInstituto(stage);
                mapsBSalud.timerStart();
                break;
        }

    }

}
