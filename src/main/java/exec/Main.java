package exec;

import dbo.MonsterLoader;
import dbo.ObjetosData;
import dbo.PlayerData;
import engine.world.World;

import java.io.IOException;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        ObjetosData objetosData = new ObjetosData();
        objetosData.crearTablas();

        MonsterLoader monster = new MonsterLoader();
        monster.crearTablas();

//        try {
//            PlayerData.guardarDato(0, 100);
//            PlayerData.guardarDato(1, 10);
//            PlayerData.guardarDato(2, 5);
//            PlayerData.guardarDato(3, 10);
//            PlayerData.guardarDato(4, 5);
//            PlayerData.guardarDato(5, 5);
//            PlayerData.guardarDato(6, 100);
//            PlayerData.guardarDato(7, 0);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        World.main(args);
    }
}
