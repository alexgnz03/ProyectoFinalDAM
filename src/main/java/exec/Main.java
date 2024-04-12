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

        World.main(args);
    }
}
