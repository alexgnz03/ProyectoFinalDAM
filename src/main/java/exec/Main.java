package exec;

import engine.dbo.MonsterLoader;
import engine.dbo.ObjetosData;
import engine.world.World;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        // Solo se crean si no existen ya
        ObjetosData objetosData = new ObjetosData();
        objetosData.crearTablas();

        MonsterLoader monster = new MonsterLoader();
        monster.crearTablas();

        World.main(args);
    }
}
