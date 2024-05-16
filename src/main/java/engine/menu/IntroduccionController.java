package engine.menu;

import engine.MusicPlayerSt;
import engine.world.Maps_BSalud;
import engine.world.Maps_LaLaguna;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class IntroduccionController {
    private Stage stage;
    private Maps_LaLaguna maps = new Maps_LaLaguna();

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    @FXML
    private MediaView mediaView;

    int video;
    public IntroduccionController(int video){
        this.video = video;
    }

    public void initialize() {
        MusicPlayerSt.stop();

        switch(video){
            case 1:
                ClassLoader classLoader = getClass().getClassLoader();
                URL resourceURL = classLoader.getResource("Video/introduccionWorking.mp4");
                String mediaSource = resourceURL.toExternalForm();

                Media media = new Media(mediaSource);

                // Crear MediaPlayer
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);

                mediaView.setMediaPlayer(mediaPlayer);

                mediaPlayer.setOnEndOfMedia(() -> {
                    maps.setY(430);
                    maps.setX(315);
                    maps.musica();
                    maps.setStage(stage);
                    maps.casa(stage);
                });
                break;
            case 2:
                ClassLoader classLoader2 = getClass().getClassLoader();
                URL resourceURL2 = classLoader2.getResource("Video/finalVideo.mp4");
                String mediaSource2 = resourceURL2.toExternalForm();

                Media media2 = new Media(mediaSource2);

                // Crear MediaPlayer
                MediaPlayer mediaPlayer2 = new MediaPlayer(media2);
                mediaPlayer2.setAutoPlay(true);

                mediaView.setMediaPlayer(mediaPlayer2);

                mediaPlayer2.setOnEndOfMedia(() -> {
                    try {
                        pantallaFinal();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                break;
        }

    }

    @FXML
    void saltarAction(ActionEvent event) {
        switch(video){
            case 1:
                maps.setY(430);
                maps.setX(315);
                maps.musica();
                maps.setStage(stage);
                maps.casa(stage);
                break;
            case 2:
                try {
                    pantallaFinal();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }

    }

    private void pantallaFinal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/PostFinal.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        PostFinalController controller = loader.getController();
        controller.setStage(stage);
        controller.setWorld(new Maps_BSalud());
        controller.musica();
    }
}
