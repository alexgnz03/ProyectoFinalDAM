package engine.ui;

import engine.dbo.PlayerData;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerState {

    static ProgressBar health = new ProgressBar();
    static ProgressBar stamina = new ProgressBar();
    Pane root;
    Stage stage;
    PlayerData playerData = new PlayerData();
    public PlayerState(Pane root, Stage stage, Scene scene) {

        scene.getStylesheets().add(getClass().getResource("/Css/healthBar.css").toExternalForm());

        health.getStyleClass().add("health-bar");
        stamina.getStyleClass().add("stamina-bar");

        health.setLayoutX(50);
        health.setLayoutY(28);
        health.setPrefWidth(180);
        health.setPrefHeight(25);
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

        stamina.setLayoutX(50);
        stamina.setLayoutY(53);
        stamina.setPrefWidth(180);
        stamina.setPrefHeight(12);

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
