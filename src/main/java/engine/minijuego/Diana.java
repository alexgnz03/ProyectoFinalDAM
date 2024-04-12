package engine.minijuego;

import java.util.concurrent.ThreadLocalRandom;

public class Diana {

    private int fila;
    private int columna;

    public Diana() {
        // Obtener posiciones aleatorias para la nueva diana
        this.fila = getRandomRow();
        this.columna = getRandomColumn();
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    private int getRandomColumn() {
        // Obtener una columna aleatoria en el rango de 0 a 4
        return ThreadLocalRandom.current().nextInt(0, 5);
    }

    private int getRandomRow() {
        // Obtener una fila aleatoria en el rango de 0 a 4
        return ThreadLocalRandom.current().nextInt(0, 5);
    }
}
