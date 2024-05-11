package engine.ui;

import dbo.PlayerData;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerState {

    static ProgressBar health = new ProgressBar();
    static ProgressBar stamina = new ProgressBar();
    ImageView dialog = new ImageView(new Image("dialog_box.png"));
    Text dialogText = new Text();
    Pane root;
    Stage stage;
    Timeline timeline;

    PlayerData playerData = new PlayerData();
    public PlayerState(Pane root, Stage stage, Scene scene) {

//        try {
//            PlayerData.guardarDato(80, 0);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        health.setLayoutX(83);
        health.setLayoutY(28);
        health.setPrefWidth(156);
        health.setPrefHeight(18);
        try {
            health.setProgress((double) PlayerData.cargarDato(0) /100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("pipo: " + PlayerData.cargarDato(6) /100);
            stamina.setProgress((double) PlayerData.cargarDato(6) /100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stamina.setLayoutX(83);
        stamina.setLayoutY(46);
        stamina.setPrefWidth(156);
        stamina.setPrefHeight(9);

        this.root = root;
        this.stage = stage;
    }

    public static void actualizarSalud(int nuevoValor) {
        health.setProgress((double) nuevoValor /100);
    }

    public static void actualizarStamina(double nuevoValor) {
        stamina.setProgress(nuevoValor/100);
    }


    //Getters y Setters

    public static ProgressBar getHealth() {
        return health;
    }

    public void setHealth(ProgressBar health) {
        this.health = health;
    }

    public ProgressBar getStamina() {
        return stamina;
    }

    public void setStamina(ProgressBar stamina) {
        this.stamina = stamina;
    }
}
