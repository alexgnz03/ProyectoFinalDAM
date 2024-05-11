package dbo;

import java.io.*;

public class PlayerData {
    private static final String ARCHIVO = System.getProperty("user.home") + "/datosJugador.dat";

    private static final int NUMERO_DE_DATOS = 8;
    private static final int[] VALORES_POR_DEFECTO = {100, 10, 5, 10, 5, 5, 100, 0};

    // 0.vidaActual
    // 1.ataque
    // 2.defensa
    // 3.ataqueMagico
    // 4.defensaMagica
    // 5.velocidad
    // 6.stamina
    // 7.dinero

    public PlayerData(){
        if (!existeArchivo()) {
            try {
                crearArchivo();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Escribir valores por defecto si el archivo no existe
            try {
                guardarDato(0, 100);
                guardarDato(1, 10);
                guardarDato(2, 5);
                guardarDato(3, 10);
                guardarDato(4, 5);
                guardarDato(5, 5);
                guardarDato(6, 100);
                guardarDato(7, 0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    //Guardar
    public static void guardarDato(int indice, int dato) throws IOException {
        try (RandomAccessFile archivo = new RandomAccessFile(ARCHIVO, "rw")) {
            archivo.seek(indice * 4); // Cada dato ocupa 4 bytes (int)
            archivo.writeInt(dato);
        }
    }

    public static int cargarDato(int indice) throws IOException {
        try (RandomAccessFile archivo = new RandomAccessFile(ARCHIVO, "r")) {
            archivo.seek(indice * 4);
            return archivo.readInt();
        }
    }

    private static boolean existeArchivo() {
        File file = new File(ARCHIVO);
        return file.exists();
    }

    private static void crearArchivo() throws IOException {
        try (RandomAccessFile archivo = new RandomAccessFile(ARCHIVO, "rw")) {
            // No es necesario escribir nada aquí, se creará un archivo vacío
        } catch (IOException ex){

        }
    }

}