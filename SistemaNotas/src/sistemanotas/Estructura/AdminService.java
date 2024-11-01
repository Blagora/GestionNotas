package sistemanotas.Estructura;

import java.sql.*;
import sistemanotas.ConexionBD.ConexionDB;
import sistemanotas.Estructura.*;

public class AdminService {

    public Usuario autenticarUsuario(String usuario, String contrasena) {
        try (Connection conn = ConexionDB.getConnection()) {
            String query = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                int rolId = rs.getInt("rol_id");
                String nombreUsuario = rs.getString("usuario");
                String contrasenaUsuario = rs.getString("contrasena");

                switch (rolId) {
                    case 1: // Admin
                        return new Usuario(id, nombreUsuario, contrasenaUsuario, rolId); // Cambia a Admin si lo necesitas
                    case 2: // Docente
                        return obtenerDocentePorUsuarioId(id);
                    case 3: // Estudiante
                        return obtenerEstudiantePorUsuarioId(id);
                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Estudiante obtenerEstudiantePorUsuarioId(int usuarioId) {
        try (Connection conn = ConexionDB.getConnection()) {
            String query = "SELECT * FROM estudiantes WHERE usuario_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Estudiante(
                    rs.getInt("id"),                 // id
                    rs.getString("usuario"),          // usuario
                    rs.getString("contrasena"),       // contrasena
                    rs.getInt("rol_id"),              // rolId
                    rs.getString("codigo"),           // codigo
                    rs.getString("nombre"),           // nombre
                    rs.getString("apellido"),         // apellido
                    rs.getString("correo"),           // correo
                    rs.getString("fecha_nacimiento")  // fechaNacimiento
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Docente obtenerDocentePorUsuarioId(int usuarioId) {
    try (Connection conn = ConexionDB.getConnection()) {
        String query = "SELECT * FROM docentes WHERE usuario_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Docente(
                rs.getInt("id"),                 // id
                rs.getString("usuario"),          // usuario
                rs.getString("contrasena"),       // contrasena
                rs.getInt("rol_id"),              // rolId
                rs.getString("nombre"),           // nombre
                rs.getString("apellido"),         // apellido
                rs.getString("correo")            // correo
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

}

