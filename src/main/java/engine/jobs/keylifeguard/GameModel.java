package engine.jobs.keylifeguard;

import java.util.Random;

public class GameModel {
    private String[] teclas = {"UP", "DOWN", "LEFT", "RIGHT", "SPACE", "W", "A", "S", "D"};
    private String teclaActual;
    private Random random = new Random();
    private int puntuacion;

    public String getTeclaActual() {
        return teclaActual;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void generarSiguienteTecla() {
        int indice = random.nextInt(teclas.length);
        teclaActual = teclas[indice];
    }

    public void aumentarPuntuacion() {
        puntuacion++;
    }

    public void disminuirPuntuacion() {
        puntuacion = Math.max(0, puntuacion - 2);
    }
}
