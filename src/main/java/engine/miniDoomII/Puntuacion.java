package engine.miniDoomII;

public class Puntuacion {
    private int puntos;

    public Puntuacion() {
        this.puntos = 0;
    }

    public int getPuntos() {
        return puntos;
    }

    public void sumarPuntos(int cantidad) {
        puntos += cantidad;
    }
}
