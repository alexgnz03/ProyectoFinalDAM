package exec;

import dbo.ObjetosData;
import engine.world.World;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        ObjetosData objetosData = new ObjetosData();
        objetosData.crearTablas();
        objetosData.insertarDatos();

        // Establecer el manejador de excepciones no controladas
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());

        World.main(args);
    }

    static class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.err.println("Error no controlado en el hilo: " + t.getName());
            e.printStackTrace(System.err);
            // Puedes hacer más cosas aquí, como guardar el error en un archivo de registro.
        }
    }
}
