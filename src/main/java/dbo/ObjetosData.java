package dbo;

import engine.tienda.TiendaItem;
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

        try {
            insertarDatosObjeto("Monster Verde", "Bebida energética clásica y potente para despertarte en un santiamén. El precio es por la inflación.", 20, 20);
            insertarDatosObjeto("Monster Blanco", "Lo mismo que el verde pero es blanco, me da pereza escribir.", 10, 50);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void crearTablas() {
        // Script para crear las tablas si no existen
        String script = "CREATE TABLE IF NOT EXISTS Objeto (\n"
                + "    CodObjeto INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    NomObjeto VARCHAR(80),\n"
                + "    Descripcion VARCHAR(500),\n"
                + "    PrecioObjeto DECIMAL(10,2),\n"
                + "    Estadistica INTEGER\n"
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

    public void insertarDatosObjeto(String nomObjeto, String descripcion, double precioObjeto, int stat) throws SQLException {
        String sql = "INSERT INTO Objeto (NomObjeto, Descripcion, PrecioObjeto, Estadistica) VALUES ('" + nomObjeto + "', '" + descripcion + "', '" + precioObjeto + "', " + stat + ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Datos del objeto insertados correctamente.");
        }
    }

    public void insertarDatosInventario(int codObjeto) throws SQLException {
        // Verificar si ya existe un registro para el CodObjeto en la tabla de inventario
        String selectSql = "SELECT * FROM Inventario WHERE CodObjeto = ?";
        try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
            selectStmt.setInt(1, codObjeto);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                if (resultSet.next()) {
                    // Si el registro existe, actualizar la cantidad sumando 1 al valor existente
                    int cantidadActual = resultSet.getInt("Cantidad");
                    int nuevaCantidad = cantidadActual + 1;
                    String updateSql = "UPDATE Inventario SET Cantidad = ? WHERE CodObjeto = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, nuevaCantidad);
                        updateStmt.setInt(2, codObjeto);
                        updateStmt.executeUpdate();
                    }
                    System.out.println("Cantidad actualizada en el inventario para el objeto con CodObjeto " + codObjeto);
                } else {
                    // Si no existe un registro para el CodObjeto, insertar uno nuevo con cantidad 1
                    String insertSql = "INSERT INTO Inventario (CodObjeto, Cantidad) VALUES (?, 1)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, codObjeto);
                        insertStmt.executeUpdate();
                    }
                    System.out.println("Nuevo registro insertado en el inventario para el objeto con CodObjeto " + codObjeto);
                }
            }
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

        String sql = "SELECT Objeto.CodObjeto, Objeto.NomObjeto, Inventario.Cantidad, Objeto.Descripcion FROM Inventario " +
                "INNER JOIN Objeto ON Inventario.CodObjeto = Objeto.CodObjeto";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int codObjeto = rs.getInt("CodObjeto");
                String nombre = rs.getString("NomObjeto");
                int cantidad = rs.getInt("Cantidad");
                String descripcion = rs.getString("Descripcion");

                InventarioItem item = new InventarioItem(codObjeto, nombre, cantidad, descripcion);
                inventario.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los datos del inventario: " + e.getMessage());
        }

        return inventario;
    }

    public List<TiendaItem> obtenerDatosObjeto() {
        List<TiendaItem> objetos = new ArrayList<>();

        String sql = "SELECT * FROM Objeto";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int codObjeto = rs.getInt("CodObjeto");
                String nomObjeto = rs.getString("NomObjeto");
                String descripcion = rs.getString("Descripcion");
                double precioObjeto = rs.getDouble("PrecioObjeto");

                TiendaItem item = new TiendaItem(codObjeto, nomObjeto, precioObjeto, descripcion);
                objetos.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los datos de Objeto: " + e.getMessage());
        }

        return objetos;
    }

    public int obtenerEstadisticaObjeto(int codObjeto) throws SQLException {
        String consulta = "SELECT Estadistica FROM Objeto WHERE CodObjeto = ?";
        try (PreparedStatement statement = conn.prepareStatement(consulta)) {
            statement.setInt(1, codObjeto);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("Estadistica");
                }
            }
        }
        return 0; // Retornar 0 si no se encuentra la estadística del objeto
    }

    public void actualizarCantidadInventario(int codObjeto, int valor) throws SQLException {
        // Verificar si ya existe un registro para el CodObjeto en la tabla de inventario
        String selectSql = "SELECT * FROM Inventario WHERE CodObjeto = ?";
        try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
            selectStmt.setInt(1, codObjeto);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                if (resultSet.next()) {
                    // Si el registro existe, actualizar la cantidad restando el valor pasado como parámetro
                    int cantidadActual = resultSet.getInt("Cantidad");
                    int nuevaCantidad = cantidadActual - valor;
                    // Asegurarse de que la nueva cantidad no sea menor que cero
                    if (nuevaCantidad < 0) {
                        nuevaCantidad = 0;
                    }
                    String updateSql = "UPDATE Inventario SET Cantidad = ? WHERE CodObjeto = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, nuevaCantidad);
                        updateStmt.setInt(2, codObjeto);
                        updateStmt.executeUpdate();
                    }
                    System.out.println("Cantidad actualizada en el inventario para el objeto con CodObjeto " + codObjeto);
                } else {
                    // Si no existe un registro para el CodObjeto, lanzar una excepción o manejar el caso según lo prefieras
                    throw new SQLException("No existe un registro en el inventario para el objeto con CodObjeto " + codObjeto);
                }
            }
        }
    }

}
