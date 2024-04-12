package engine;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class MusicPlayer {
    private static MusicPlayer instance;
    private MediaPlayer mediaPlayer;
    private double volume = 0.5; // Volumen predeterminado

    public MusicPlayer(String musicFilePath) {
        // Cargar el archivo de música
        Media media = new Media(Objects.requireNonNull(MusicPlayer.class.getResource(musicFilePath)).toExternalForm());
        // Crear el MediaPlayer
        mediaPlayer = new MediaPlayer(media);
        // Configurar para que la música se reproduzca en bucle
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        // Establecer el volumen inicial
        mediaPlayer.setVolume(volume);
    }

    public static synchronized MusicPlayer getInstance(String musicFilePath) {
        if (instance == null) {
            instance = new MusicPlayer(musicFilePath);
        }
        return instance;
    }

    public void play() {
        // Reproducir la música
        mediaPlayer.play();
    }

    public void stop() {
        // Detener la reproducción de la música
        mediaPlayer.stop();
    }

    public void pause() {
        // Pausar la reproducción de la música
        mediaPlayer.pause();
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double v) {
        if (v >= 0 && v <= 1) {
            volume = v;
            mediaPlayer.setVolume(volume);
        } else {
            throw new IllegalArgumentException("El volumen debe estar entre 0 y 1");
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public static void setInstance(MusicPlayer instance) {
        MusicPlayer.instance = instance;
    }

    public static MusicPlayer getInstance() {
        return instance;
    }
}

