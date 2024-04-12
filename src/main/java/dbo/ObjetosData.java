package dbo;

import engine.ui.inGameMenu.InventarioItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObjetosData {
    private Connection conn;

    public ObjetosData() {
        // Crear la conexión a la base de datos en el constructor
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:objetosdata.db");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public void crearTablas() {
        // Script para crear las tablas si no existen
        String script = "CREATE TABLE IF NOT EXISTS Objeto (\n"
                + "    CodObjeto INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    NomObjeto VARCHAR(80),\n"
                + "    Descripcion VARCHAR(500),\n"
                + "    PrecioObjeto DECIMAL(10,2)\n"
                + ");\n";
        String script2 = "CREATE TABLE IF NOT EXISTS Inventario (\n"
                + "    CodInventario INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    CodObjeto INTEGER,\n"
                + "    Cantidad INTEGER,\n"
                + "    FOREIGN KEY (CodObjeto) REFERENCES Objeto(CodObjeto)\n"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            // Ejecutar el script para crear las tablas
            stmt.execute(script);
            System.out.println("Tablas 1 creadas correctamente.");
            stmt.execute(script2);
            System.out.println("Tablas 2 creadas correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear las tablas: " + e.getMessage());
        }
    }

    public void cerrarConexion() {
        // Cerrar la conexión a la base de datos
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public void insertarDatosObjeto(String nomObjeto, String descripcion, double precioObjeto) throws SQLException {
        String sql = "INSERT INTO Objeto (NomObjeto, Descripcion, PrecioObjeto) VALUES ('" + nomObjeto + "', '" + descripcion + "', " + precioObjeto + ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Datos del objeto insertados correctamente.");
        }
    }

    public void insertarDatosInventario(int codObjeto, int cantidad) throws SQLException {
        String sql = "INSERT INTO Inventario (CodObjeto, Cantidad) VALUES (" + codObjeto + ", " + cantidad + ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Datos del inventario insertados correctamente.");
        } catch (SQLException ex){
            System.out.println(ex);
        }
    }

    public void mostrarDatosObjeto() {
        String sql = "SELECT * FROM Objeto";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int codObjeto = rs.getInt("CodObjeto");
                String nomObjeto = rs.getString("NomObjeto");
                String descripcion = rs.getString("Descripcion");
                double precioObjeto = rs.getDouble("PrecioObjeto");
                System.out.println("CodObjeto: " + codObjeto + ", NomObjeto: " + nomObjeto + ", Descripcion: " + descripcion + ", PrecioObjeto: " + precioObjeto);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar los datos de Objeto: " + e.getMessage());
        }
    }
    public void mostrarDatosInventario() {
        String sql = "SELECT * FROM Inventario";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int codInventario = rs.getInt("CodInventario");
                int codObjeto = rs.getInt("CodObjeto");
                int cantidad = rs.getInt("Cantidad");
                System.out.println("CodInventario: " + codInventario + ", CodObjeto: " + codObjeto + ", Cantidad: " + cantidad);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar los datos de Inventario: " + e.getMessage());
        }
    }

    public List<InventarioItem> obtenerDatosInventario() {
        List<InventarioItem> inventario = new ArrayList<>();

        String sql = "SELECT Objeto.NomObjeto, Inventario.Cantidad, Objeto.Descripcion FROM Inventario " +
                "INNER JOIN Objeto ON Inventario.CodObjeto = Objeto.CodObjeto";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nombre = rs.getString("NomObjeto");
                int cantidad = rs.getInt("Cantidad");
                String descripcion = rs.getString("Descripcion");

                InventarioItem item = new InventarioItem(nombre, cantidad, descripcion);
                inventario.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los datos del inventario: " + e.getMessage());
        }

        return inventario;
    }

}
