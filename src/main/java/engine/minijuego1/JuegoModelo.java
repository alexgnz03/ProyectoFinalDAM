package engine.minijuego1;

import java.util.ArrayList;
import java.util.Random;

public class JuegoModelo {
    private ArrayList<String> palabras;
    private String palabraActual;
    private int puntaje;

    public JuegoModelo() {
        // Inicializa el modelo con algunas palabras
        palabras = new ArrayList<>();
        palabras.add("hola");
        palabras.add("mundo");
        palabras.add("planeta");
        palabras.add("nabo");
        palabras.add("mojo");
        palabras.add("pipo");
        palabras.add("cao");
        // Agrega más palabras según sea necesario

        puntaje = 0;
    }

    public void nuevaPalabra() {
        Random rand = new Random();
        palabraActual = palabras.get(rand.nextInt(palabras.size()));
    }

    public boolean verificarPalabra(String palabra) {
        return palabra.equals(palabraActual);
    }

    public void aumentarPuntaje() {
        puntaje++;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public String getPalabraActual() {
        return palabraActual;
    }
}