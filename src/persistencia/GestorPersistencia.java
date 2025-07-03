package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Escritores;
import modelo.Lectores;

public class GestorPersistencia {

    private Connection conn;

    public GestorPersistencia() {
        try {
            // Cambia los datos de conexión a los tuyos reales
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lectores_escritores_db", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            // Lanzamos RuntimeException para que no obligue a catch en Main
            throw new RuntimeException("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }

    public void agregarLector(Lectores lector) {
        String sql = "INSERT INTO lectores (id, nombre) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lector.getId());
            stmt.setString(2, lector.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar lector: " + e.getMessage());
        }
    }

    public void agregarEscritor(Escritores escritor) {
        String sql = "INSERT INTO escritores (id, nombre) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, escritor.getId());
            stmt.setString(2, escritor.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar escritor: " + e.getMessage());
        }
    }

    public List<Lectores> obtenerLectores() {
        List<Lectores> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM lectores";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Lectores(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener lectores: " + e.getMessage());
        }
        return lista;
    }

    public List<Escritores> obtenerEscritores() {
        List<Escritores> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM escritores";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Escritores(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener escritores: " + e.getMessage());
        }
        return lista;
    }
}
