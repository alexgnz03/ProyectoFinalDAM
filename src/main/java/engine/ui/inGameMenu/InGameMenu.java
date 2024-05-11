package engine.ui.inGameMenu;

import controllers.MainMenuController;
import engine.MusicPlayer;
import engine.tienda.TiendaController;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class InGameMenu {

    ImageView smartphone = new ImageView(new Image("GUI/smartphone.png"));
    Text dialogText = new Text();
    Stage mainStage;
    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;}

    private int textIndex = 0;
    Pane root;
    Stage stage;
    int dialogImageX = 465;
    int dialogImageY = 800;
    int finalX = 325;
    int finalY = -(dialogImageY-330);
    Timeline timeline;
    private Boolean menuOpen = false;
    private AnimationTimer timer;
    private Pane menuPane;
    public InGameMenu(Pane root, Stage stage, Scene scene) {
        smartphone.setLayoutX(dialogImageX);
        smartphone.setLayoutY(dialogImageY);
        smartphone.setOpacity(0);
        smartphone.setFitWidth(325);
        smartphone.setFitHeight(550);
        smartphone.setPreserveRatio(true);


        dialogText.setFont(Font.font("Verdana", FontWeight.BOLD, 20)); //TODO Hacer con css
        dialogText.setLayoutX(dialogImageX + 33);
        dialogText.setLayoutY(dialogImageY);

        cargarMenu();
        menuPane.setVisible(menuOpen);
        menuPane.setLayoutX(465+2.5);
        menuPane.setLayoutY(800+finalY+10);

        this.root = root;
        this.stage = stage;
    }

    public void invokeInGameMenu() {
        menuOpen = true;
        dialogText.setOpacity(1); // Asegurarse de que el texto sea visible
        smartphone.setOpacity(1);
        // Crear una transición de desplazamiento
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.3), smartphone);
        transition.setToY(finalY); // Establecer la posición final en Y

        transition.setOnFinished(event -> {
            // Este código se ejecutará cuando la transición haya terminado
            menuPane.setVisible(menuOpen);
        });

        // Iniciar la animación
        transition.play();

        System.out.println("InGameMenu Opacity: " + smartphone.getOpacity());
    }

    public void closeInGameMenu(){
        menuOpen = false;
        menuPane.setVisible(menuOpen);
        // Crear una transición de desplazamiento
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.3), smartphone);
        transition.setToY(500); // Establecer la posición final en Y

        // Iniciar la animación
        transition.play();


        //efectos.stop();
    }

    public void soundEffect(){
        MusicPlayer efectos;
        efectos = new MusicPlayer("/Effects/typeWriting.mp3");
        efectos.play();
        Timeline soundline = new Timeline(new KeyFrame(Duration.seconds(0.15), event -> {
            efectos.stop();
        }));
        soundline.play();
    }

    public void cargarMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InGameMenu.fxml"));

            IGMenuController controller = new IGMenuController();
            loader.setController(controller);
            controller.setGameMenu(this);
            controller.setStage(stage);
            if (menuPane == null) {
                menuPane = loader.load();
            } else {
                Pane homePane = loader.load();
                menuPane.getChildren().setAll(homePane);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarInventario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InventoryMenu.fxml"));
            IGInventoryController controller = new IGInventoryController();
            loader.setController(controller);
            controller.setGameMenu(this);
            Pane inventarioPane = loader.load();
            menuPane.getChildren().setAll(inventarioPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarStats() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/playerInfoMenu.fxml"));
            IGStatsController controller = new IGStatsController();
            loader.setController(controller);
            controller.setGameMenu(this);
            Pane statsPane = loader.load();
            menuPane.getChildren().setAll(statsPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarMostroDex() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MostroDexMenu.fxml"));
            IGMostrodexController controller = new IGMostrodexController();
            loader.setController(controller);
            controller.setGameMenu(this);
            Pane statsPane = loader.load();
            menuPane.getChildren().setAll(statsPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //Getters y Setters
    public ImageView getSmartphone() {
        return smartphone;
    }

    public void setSmartphone(ImageView smartphone) {
        this.smartphone = smartphone;
    }

    public Text getDialogText() {
        return dialogText;
    }

    public void setDialogText(Text dialogText) {
        this.dialogText = dialogText;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Boolean getMenuOpen() {
        return menuOpen;
    }

    public void setMenuOpen(Boolean menuOpen) {
        this.menuOpen = menuOpen;
    }

    public Pane getMenuPane() {
        return menuPane;
    }

    public void setMenuPane(Pane menuPane) {
        this.menuPane = menuPane;
    }
}
