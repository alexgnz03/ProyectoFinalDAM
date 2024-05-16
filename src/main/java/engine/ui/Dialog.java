package engine.ui;

import engine.EffectPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Dialog {

    ImageView dialog = new ImageView(new Image("Player/dialog_box.png"));
    Text dialogText = new Text();
    private int textIndex = 0;
    Pane root;
    Stage stage;
    int dialogImageX = 50;
    int dialogImageY = 620;
    Timeline timeline;
    public Dialog(Pane root, Stage stage, Scene scene) {
        dialog.setLayoutX(dialogImageX);
        dialog.setLayoutY(dialogImageY);
        dialog.setOpacity(0);

        dialogText.setFont(Font.font("Verdana", FontWeight.BOLD, 20)); //TODO Hacer con css
        dialogText.setLayoutX(dialogImageX + 33);
        dialogText.setLayoutY(dialogImageY + 70);

        this.root = root;
        this.stage = stage;
    }

    public void invokeDialog(String dialogLine) {
        dialogText.setText(""); // Limpiar el texto anterior
        textIndex = 0; // Reiniciar el índice
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            if (textIndex <= dialogLine.length()) {
                dialogText.setText(dialogLine.substring(0, textIndex++));
                soundEffect();
            }
        }));
        timeline.setCycleCount(dialogLine.length() + 1);
        timeline.play();
        dialogText.setOpacity(1); // Asegurarse de que el texto sea visible
        dialog.setOpacity(0.7);

        System.out.println("DialogBA " + dialog.getOpacity());
    }

    // Diálogos sin necesidad de pasárselos al npc o element
    public void autoDialog(String dialogLine, int seconds) {
        // Mostrar el cuadro de diálogo
        dialogText.setText(""); // Limpiar el texto anterior
        textIndex = 0; // Reiniciar el índice
        dialogText.setOpacity(1); // Asegurarse de que el texto sea visible
        dialog.setOpacity(0.7);

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            if (textIndex <= dialogLine.length()) {
                dialogText.setText(dialogLine.substring(0, textIndex++));
                soundEffect();
            }
        }));
        timeline.setCycleCount(dialogLine.length() + 1);
        timeline.play();

        // Temporizador para cerrar el cuadro de diálogo después de 2 segundos
        PauseTransition delay = new PauseTransition(Duration.seconds(seconds));
        delay.setOnFinished(event -> closeDialog());
        delay.play();
    }

    public void closeDialog(){
        dialogText.setOpacity(0);
        dialog.setOpacity(0);
        //efectos.stop();
    }

    public void soundEffect(){
        EffectPlayer efectos;
        efectos = new EffectPlayer("/Effects/typeWriting.mp3");
        efectos.play();
        Timeline soundline = new Timeline(new KeyFrame(Duration.seconds(0.15), event -> {
            efectos.stop();
        }));
        soundline.play();
    }

    //Getters y Setters
    public ImageView getDialog() {
        return dialog;
    }

    public void setDialog(ImageView dialog) {
        this.dialog = dialog;
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
}
