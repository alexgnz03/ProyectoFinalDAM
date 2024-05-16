package engine.miniDoomII;

import engine.dbo.CVData;
import engine.dbo.PlayerData;
import engine.world.Maps_BSalud;
import engine.world.Maps_LaLaguna;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class FinalScreen {

    Stage stage;
    Maps_LaLaguna mapsLaLaguna = new Maps_LaLaguna();
    Maps_BSalud mapsBSalud = new Maps_BSalud();
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    long seed = System.currentTimeMillis();
    Random random = new Random(seed);
    // Generar un número entero aleatorio entre 1 y 4 (ambos inclusive)
    int numeroAleatorio = random.nextInt(4) + 1;



    @FXML
    private Label euroText;

    @FXML
    private ImageView fondo;

    @FXML
    private Label habilidadLabel;

    @FXML
    private Label puntajeLabel;

    @FXML
    private AnchorPane view;

    private int escenario;

    public FinalScreen(int puntuacion, int habilidad, int escenario) {
        this.puntuacion = puntuacion;
        this.habilidad = habilidad;
        this.escenario = escenario;
    }

    public void initialize() {
        puntajeLabel.setText(String.valueOf("Puntuación: " + puntuacion));
        habilidadLabel.setText(String.valueOf("Puntos de habilidad: " + habilidad));
    }

    int puntuacion;
    int habilidad;

    @FXML
    void salirAction(ActionEvent event) {
        try {
            if(puntuacion > CVData.cargarDato(3)){
                CVData.guardarDato(3, puntuacion);
            }
            int habilidadFinal = PlayerData.cargarDato(numeroAleatorio) + habilidad;
            PlayerData.guardarDato(numeroAleatorio, habilidadFinal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        switch (escenario){
            case 1:
                mapsLaLaguna.setStage(stage);
                mapsLaLaguna.musica();
                mapsLaLaguna.arcade(stage);
                mapsLaLaguna.timerStart();
                break;
            case 2:
                mapsBSalud.setStage(stage);
                mapsBSalud.musica();
                mapsBSalud.arcade(stage);
                mapsBSalud.timerStart();
                break;
        }

    }

}
