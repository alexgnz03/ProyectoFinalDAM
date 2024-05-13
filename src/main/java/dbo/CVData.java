package dbo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CVData {
    private static final String ARCHIVO = System.getProperty("user.home") + "/cvData.dat";

    private static final int NUMERO_DE_DATOS = 4;
    private static final int[] VALORES_POR_DEFECTO = {0, 0, 0, 0};

    // 0.mcClicker
    // 1.keyLifeGuard
    // 2.secretaryTyping
    // 3.doom2Mini

    public CVData(){
        if (!existeArchivo()) {
            try {
                crearArchivo();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Escribir valores por defecto si el archivo no existe
            try {
                guardarDato(0, 0);
                guardarDato(1, 0);
                guardarDato(2, 0);
                guardarDato(3, 0);
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