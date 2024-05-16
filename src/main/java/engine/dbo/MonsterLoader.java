package engine.dbo;

import engine.combate.Monstruo;
import engine.ui.inGameMenu.MostroDexItem;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonsterLoader {
    private Connection connection;
    private static final String ARCHIVO = System.getProperty("user.home") + "/monsterdata.db";

    public MonsterLoader() {
        try {
            // Verificar si el archivo de la base de datos existe
            File file = new File(ARCHIVO);
            boolean existe = file.exists();

            // Establecer la conexión a la base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:" + ARCHIVO);

            // Si la base de datos no existe, llamar al método para crear las tablas
            if (!existe) {
                crearTablas();
                insertarRegistros();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearTablas() {
        // Script para crear las tablas si no existen
        String script = "CREATE TABLE IF NOT EXISTS Enemy (\n"
                + "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    Nombre TEXT NOT NULL,\n"
                + "    Descripcion TEXT NOT NULL,\n"
                + "    Registrado BOOLEAN\n"
                + ");\n";
        String script2 = "CREATE TABLE IF NOT EXISTS Enemy_Sprites (\n"
                + "    Cod_Sprites INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    Standard_Sprite TEXT,\n"
                + "    Attack_Sprite TEXT,\n"
                + "    Damage_Sprite TEXT,\n"
                + "    Enemy_ID INTEGER,\n"
                + "    FOREIGN KEY (Enemy_ID) REFERENCES Enemy(ID)\n"
                + ");";
        String script3 = "CREATE TABLE IF NOT EXISTS Combat_Stats (\n"
                + "    Cod_Stats INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    Money INTEGER,\n"
                + "    vida REAL,\n"
                + "    vida_maxima REAL,\n"
                + "    Attack REAL,\n"
                + "    Defense REAL,\n"
                + "    Magic_Attack REAL,\n"
                + "    Magic_Defense REAL,\n"
                + "    Cod_Skill INTEGER,\n"
                + "    Enemy_ID INTEGER,\n"
                + "    FOREIGN KEY (Cod_Skill) REFERENCES Skills(Cod_Skill),\n"
                + "    FOREIGN KEY (Enemy_ID) REFERENCES Enemy(ID)\n"
                + ");";
        String script4 = "CREATE TABLE IF NOT EXISTS Skills (\n"
                + "    Cod_Skill INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    Nom_Skill TEXT,\n"
                + "    Damage TEXT,\n"
                + "    Enemy_ID INTEGER,\n"
                + "    FOREIGN KEY (Enemy_ID) REFERENCES Enemy(ID)\n"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            // Ejecutar el script para crear las tablas
            stmt.execute(script);
            System.out.println("Tablas 1 creadas correctamente.");
            stmt.execute(script2);
            System.out.println("Tablas 2 creadas correctamente.");
            stmt.execute(script3);
            System.out.println("Tablas 3 creadas correctamente.");
            stmt.execute(script4);
            System.out.println("Tablas 4 creadas correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear las tablas: " + e.getMessage());
        }
    }

    public Monstruo cargarMonstruo(int id) {
        Monstruo monstruo = null;
        try {
            // Consultar los datos del monstruo en la base de datos
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT e.Nombre, cs.*, es.* FROM Enemy e " +
                            "INNER JOIN Combat_Stats cs ON e.ID = cs.Enemy_ID " +
                            "INNER JOIN Enemy_Sprites es ON e.ID = es.Enemy_ID " +
                            "WHERE cs.Enemy_ID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            // Procesar los resultados y crear una instancia de Monstruo
            if (resultSet.next()) {
                monstruo = new Monstruo();
                monstruo.setName(resultSet.getString("Nombre"));
                monstruo.setMoney(resultSet.getInt("Money"));
                monstruo.setVida(resultSet.getDouble("vida"));
                monstruo.setVida_maxima(resultSet.getDouble("vida_maxima"));
                monstruo.setAtaque(resultSet.getDouble("Attack"));
                monstruo.setDefensa(resultSet.getDouble("Defense"));
                monstruo.setAtaque_magico(resultSet.getDouble("Magic_Attack"));
                monstruo.setDefensa_magica(resultSet.getDouble("Magic_Defense"));
                monstruo.setVelocidad(25);
                monstruo.setSkill(cargarHabilidades(id));
                monstruo.setEnemy_image(new Image(resultSet.getString("Standard_Sprite")));
            }

            // Cerrar los recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monstruo;
    }

    private String[] cargarHabilidades(int id) {
        String[] habilidades = new String[2];
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Skills WHERE Enemy_ID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            // Procesar los resultados y obtener las habilidades del monstruo
            if (resultSet.next()) {
                habilidades[0] = resultSet.getString("Nom_Skill");
                habilidades[1] = resultSet.getString("Damage");
            }

            // Cerrar los recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return habilidades;
    }

    public void insertarRegistros() {
        if (!tablasEstanVacias()) {
            System.out.println("Las tablas ya contienen registros, no se realizarán inserciones.");
            return;
        }

        try {
            // Insertar registros en la tabla Enemy
            String insertEnemy1 = "INSERT INTO Enemy (Nombre, Descripcion, Registrado) VALUES ('Otaku', 'Le gusta ver anime y jugar a videojuegos. No sale de su casa (Como yo desde que empezó el segundo trimestre hasta ahora)', 0)";
            String insertEnemy2 = "INSERT INTO Enemy (Nombre, Descripcion, Registrado) VALUES ('Manola', 'Es Manola no tiene más lore, se llama Manola. Manola.', 0)";
            String insertEnemy3 = "INSERT INTO Enemy (Nombre, Descripcion, Registrado) VALUES ('Johny', 'Johny, guarda la navaja por favor', 0)";
            String insertEnemy4 = "INSERT INTO Enemy (Nombre, Descripcion, Registrado) VALUES ('Solajero', 'Le gusta la cerveza a las 8 de la mañana. Es tímido UwU 👉👈. Sabe posar bien en las fotos 😎.', 0)";
            String insertEnemy5 = "INSERT INTO Enemy (Nombre, Descripcion, Registrado) VALUES ('Furro', 'De este mejor copiar de la wikipedia: Los furros son un fandom basado en el género furry, el cual se centra en el gusto por el contenido que gira o incluye animales antropomórficos, ya sea en novelas, cómics, programas animados, videos o arte en general, lo cual incluye gustos por seres con apariencia humana y rasgos de animales.', 0)";
            String insertEnemy6 = "INSERT INTO Enemy (Nombre, Descripcion, Registrado) VALUES ('Antonio', 'No se quien es ni por qué se decidió que fuera el enemigo más fuerte, pero bueno, lo es.', 0)";

            // Insertar registros en la tabla Enemy_Sprites
            String insertEnemySprites1 = "INSERT INTO Enemy_Sprites (Standard_Sprite, Attack_Sprite, Damage_Sprite, Enemy_ID) VALUES ('Enemies/Enemy2.png', 'Enemies/Enemy2.png', 'Enemies/Enemy2.png', 1)";
            String insertEnemySprites2 = "INSERT INTO Enemy_Sprites (Standard_Sprite, Attack_Sprite, Damage_Sprite, Enemy_ID) VALUES ('Enemies/Enemy3.png', 'Enemies/Enemy3.png', 'Enemies/Enemy3.png', 2)";
            String insertEnemySprites3 = "INSERT INTO Enemy_Sprites (Standard_Sprite, Attack_Sprite, Damage_Sprite, Enemy_ID) VALUES ('Enemies/Enemy1.png', 'Enemies/Enemy1.png', 'Enemies/Enemy1.png', 3)";
            String insertEnemySprites4 = "INSERT INTO Enemy_Sprites (Standard_Sprite, Attack_Sprite, Damage_Sprite, Enemy_ID) VALUES ('Enemies/Enemy4.png', 'Enemies/Enemy4.png', 'Enemies/Enemy4.png', 4)";
            String insertEnemySprites5 = "INSERT INTO Enemy_Sprites (Standard_Sprite, Attack_Sprite, Damage_Sprite, Enemy_ID) VALUES ('Enemies/Enemy5.png', 'Enemies/Enemy5.png', 'Enemies/Enemy5.png', 5)";
            String insertEnemySprites6 = "INSERT INTO Enemy_Sprites (Standard_Sprite, Attack_Sprite, Damage_Sprite, Enemy_ID) VALUES ('Enemies/Enemy6.png', 'Enemies/Enemy6.png', 'Enemies/Enemy6.png', 6)";

            // Insertar registros en la tabla Combat_Stats
            String insertCombatStats1 = "INSERT INTO Combat_Stats (Money, vida, vida_maxima, Attack, Defense, Magic_Attack, Magic_Defense, Cod_Skill, Enemy_ID) VALUES (20, 30, 30, 6, 6, 2, 2, 1, 1)";
            String insertCombatStats2 = "INSERT INTO Combat_Stats (Money, vida, vida_maxima, Attack, Defense, Magic_Attack, Magic_Defense, Cod_Skill, Enemy_ID) VALUES (30, 30, 30, 4, 3, 7, 5, 2, 2)";
            String insertCombatStats3 = "INSERT INTO Combat_Stats (Money, vida, vida_maxima, Attack, Defense, Magic_Attack, Magic_Defense, Cod_Skill, Enemy_ID) VALUES (25, 50, 50, 5, 5, 5, 5, 3, 3)";
            String insertCombatStats4 = "INSERT INTO Combat_Stats (Money, vida, vida_maxima, Attack, Defense, Magic_Attack, Magic_Defense, Cod_Skill, Enemy_ID) VALUES (50, 40, 40, 8, 4, 2, 10, 4, 4)";
            String insertCombatStats5 = "INSERT INTO Combat_Stats (Money, vida, vida_maxima, Attack, Defense, Magic_Attack, Magic_Defense, Cod_Skill, Enemy_ID) VALUES (1, 80, 80, 10, 10, 10, 10, 5, 5)";
            String insertCombatStats6 = "INSERT INTO Combat_Stats (Money, vida, vida_maxima, Attack, Defense, Magic_Attack, Magic_Defense, Cod_Skill, Enemy_ID) VALUES (200, 50, 50, 15, 15, 15, 15, 6, 6)";

            // Insertar registros en la tabla Skills
            String insertSkills1 = "INSERT INTO Skills (Cod_Skill, Nom_Skill, Damage, Enemy_ID) VALUES (1, 'Fire rain', 'High', 1)";
            String insertSkills2 = "INSERT INTO Skills (Cod_Skill, Nom_Skill, Damage, Enemy_ID) VALUES (2, 'Waterstorm', 'High', 2)";
            String insertSkills3 = "INSERT INTO Skills (Cod_Skill, Nom_Skill, Damage, Enemy_ID) VALUES (3, 'Mega Tornado', 'High', 3)";
            String insertSkills4 = "INSERT INTO Skills (Cod_Skill, Nom_Skill, Damage, Enemy_ID) VALUES (4, 'Heavy Rain', 'High', 3)";
            String insertSkills5 = "INSERT INTO Skills (Cod_Skill, Nom_Skill, Damage, Enemy_ID) VALUES (5, 'Beyond Two Souls', 'High', 3)";
            String insertSkills6 = "INSERT INTO Skills (Cod_Skill, Nom_Skill, Damage, Enemy_ID) VALUES (6, 'Detroit Become Human', 'High', 3)";

            // Crear un statement para ejecutar las consultas
            Statement statement = connection.createStatement();

            // Ejecutar las consultas
            statement.executeUpdate(insertEnemy1);
            statement.executeUpdate(insertEnemy2);
            statement.executeUpdate(insertEnemy3);
            statement.executeUpdate(insertEnemy4);
            statement.executeUpdate(insertEnemy5);
            statement.executeUpdate(insertEnemy6);

            statement.executeUpdate(insertEnemySprites1);
            statement.executeUpdate(insertEnemySprites2);
            statement.executeUpdate(insertEnemySprites3);
            statement.executeUpdate(insertEnemySprites4);
            statement.executeUpdate(insertEnemySprites5);
            statement.executeUpdate(insertEnemySprites6);

            statement.executeUpdate(insertCombatStats1);
            statement.executeUpdate(insertCombatStats2);
            statement.executeUpdate(insertCombatStats3);
            statement.executeUpdate(insertCombatStats4);
            statement.executeUpdate(insertCombatStats5);
            statement.executeUpdate(insertCombatStats6);

            statement.executeUpdate(insertSkills1);
            statement.executeUpdate(insertSkills2);
            statement.executeUpdate(insertSkills3);
            statement.executeUpdate(insertSkills4);
            statement.executeUpdate(insertSkills5);
            statement.executeUpdate(insertSkills6);

            // Cerrar la conexión y el statement
            statement.close();
            connection.close();

            System.out.println("Registros insertados correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar registros: " + e.getMessage());
        }
    }

    public void actualizarRegistrado(int id) {
        try {
            // Consultar los datos del monstruo en la base de datos
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Enemy SET Registrado = 1 WHERE ID = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
            System.out.println("Campo Registrado actualizado correctamente para el ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MostroDexItem> obtenerDatosMostroDex() {
        List<MostroDexItem> mostroDex = new ArrayList<>();

        String query = "SELECT " +
                "    e.ID AS ID_Enemy, " +
                "    e.Nombre AS Nombre_Enemy, " +
                "    e.Descripcion AS Descripcion_Enemy, " +
                "    es.Standard_Sprite AS Standard_Sprite_Enemy, " +
                "    cs.Attack AS Attack_Combat_Stats, " +
                "    cs.Defense AS Defense_Combat_Stats, " +
                "    cs.Magic_Attack AS Magic_Attack_Combat_Stats, " +
                "    cs.Magic_Defense AS Magic_Defense_Combat_Stats " +
                "FROM " +
                "    Enemy e " +
                "INNER JOIN " +
                "    Enemy_Sprites es ON e.ID = es.Enemy_ID " +
                "INNER JOIN " +
                "    Combat_Stats cs ON e.ID = cs.Enemy_ID " +
                "WHERE " +
                "    e.Registrado = 1"; // Agregar la condición para mostrar solo enemigos registrados

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // Recorrer el resultado de la consulta
            while (rs.next()) {
                // Obtener los valores de las columnas
                int idEnemy = rs.getInt("ID_Enemy");
                String nombreEnemy = rs.getString("Nombre_Enemy");
                String descripcionEnemy = rs.getString("Descripcion_Enemy");
                String standardSpriteEnemy = rs.getString("Standard_Sprite_Enemy");
                double attackCombatStats = rs.getDouble("Attack_Combat_Stats");
                double defenseCombatStats = rs.getDouble("Defense_Combat_Stats");
                double magicAttackCombatStats = rs.getDouble("Magic_Attack_Combat_Stats");
                double magicDefenseCombatStats = rs.getDouble("Magic_Defense_Combat_Stats");

                MostroDexItem mostro = new MostroDexItem(idEnemy, nombreEnemy, (int) attackCombatStats, (int) defenseCombatStats, (int) magicAttackCombatStats, (int) magicDefenseCombatStats, descripcionEnemy, standardSpriteEnemy);
                mostroDex.add(mostro);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los datos del inventario: " + e.getMessage());
        }

        return mostroDex;
    }

    public int obtenerCantidadRegistrosMostroDex() {
        int cantidadRegistros = 0;

        String query = "SELECT COUNT(*) AS Total FROM Enemy e " +
                "INNER JOIN Enemy_Sprites es ON e.ID = es.Enemy_ID " +
                "INNER JOIN Combat_Stats cs ON e.ID = cs.Enemy_ID " +
                "WHERE e.Registrado = 1";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                cantidadRegistros = rs.getInt("Total");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la cantidad de registros: " + e.getMessage());
        }

        return cantidadRegistros;
    }

    public boolean tablasEstanVacias() {
        boolean vacias = true;
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) AS Total FROM Enemy");
            if (resultSet.next()) {
                int total = resultSet.getInt("Total");
                vacias = total == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vacias;
    }

    // Método para cerrar la conexión a la base de datos
    public void cerrarConexion() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarTablas() {
        // Lista de nombres de tablas
        List<String> tablas = Arrays.asList("Enemy", "Enemy_Sprites", "Combat_Stats", "Skills");

        try (Statement stmt = connection.createStatement()) {
            // Eliminar cada tabla en la lista
            for (String tabla : tablas) {
                stmt.executeUpdate("DROP TABLE IF EXISTS " + tabla);
                System.out.println("Tabla " + tabla + " eliminada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar las tablas: " + e.getMessage());
        }
    }

}

