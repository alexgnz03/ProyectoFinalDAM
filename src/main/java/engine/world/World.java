package engine.world;

import java.util.LinkedList;

import engine.entities.ObstacleTile;
import engine.menu.MainMenuController;
import engine.MusicPlayerSt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class World extends Application {
    private LinkedList<ObstacleTile> barrier;
    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    //Constructor

    public World() {

        this.barrier = new LinkedList<>();
    }


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/MainMenu.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene); // Primero asigna la escena al Stage

        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);

        primaryStage.show(); // Muestra la ventana

        MainMenuController controller = loader.getController();
        controller.setStage(primaryStage); // Luego configura el controlador
        controller.setWorld(new Maps_BSalud());

        MusicPlayerSt.play("/Music/mainmenuMusic.mp3");
        MusicPlayerSt.setVolume(0.8);
    }


}
