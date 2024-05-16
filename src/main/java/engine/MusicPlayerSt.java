package engine;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

// Clase para la música, estática
public class MusicPlayerSt {
    private static MediaPlayer mediaPlayer;

    private MusicPlayerSt() {
        // Constructor privado para evitar instanciación externa
    }

    public static void play(String musicFilePath) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        InputStream inputStream = EffectPlayer.class.getResourceAsStream(musicFilePath);
        if (inputStream != null) {
            try {
                URI uri = EffectPlayer.class.getResource(musicFilePath).toURI();
                Media media = new Media(uri.toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
            } catch (URISyntaxException e) {
                System.err.println("Error al construir la URI para el archivo de música: " + e.getMessage());
            }
        } else {
            System.err.println("No se pudo encontrar el archivo de música: " + musicFilePath);
        }
    }

    public static void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public static void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public static double getVolume() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVolume();
        }
        return 0; // Devuelve 0 si el reproductor es nulo
    }
}