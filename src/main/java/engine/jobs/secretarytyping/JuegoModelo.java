package engine.jobs.secretarytyping;

import java.util.ArrayList;
import java.util.Random;

public class JuegoModelo {
    private ArrayList<String> palabras;
    private String palabraActual;
    private int puntaje;

    public JuegoModelo() {
        // Inicializa el modelo con algunas palabras
        palabras = new ArrayList<>();
        palabras.add("residencia");
        palabras.add("codigo");
        palabras.add("ciudad");
        palabras.add("nombre");
        palabras.add("apellidos");
        palabras.add("nacionalidad");
        palabras.add("direccion");
        palabras.add("descargar");
        palabras.add("pdf");
        palabras.add("curriculum");
        palabras.add("calle");
        palabras.add("informe");
        palabras.add("representante");
        palabras.add("firma");
        palabras.add("empresa");
        palabras.add("horario");
        palabras.add("apertura");
        palabras.add("clausura");
        palabras.add("prohibir");
        palabras.add("suspenso");
        palabras.add("ordenador");
        palabras.add("imprimir");
        palabras.add("tablon");

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