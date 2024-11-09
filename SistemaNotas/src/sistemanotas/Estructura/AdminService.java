package sistemanotas.Estructura;

import java.sql.*;
import sistemanotas.ConexionBD.ConexionDB;

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
                    rs.getString("correo")             // correo
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
                rs.getString("correo"),           // correo
                rs.getString("area")            // area
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return null;
    }
    
    public boolean agregarEstudiante(String codigo, String nombre, String apellido, String correo) {
        String insertUsuarioQuery = "INSERT INTO usuarios (usuario, contrasena, rol_id) VALUES (?, ?, ?)";
        String insertEstudianteQuery = "INSERT INTO estudiantes (codigo, usuario_id, nombre, apellido, correo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection()) {
            // Primero, insertamos el usuario
            PreparedStatement stmtUsuario = conn.prepareStatement(insertUsuarioQuery, Statement.RETURN_GENERATED_KEYS);
            stmtUsuario.setString(1, correo);  // El correo como usuario
            stmtUsuario.setString(2, codigo);  // El código como contraseña
            stmtUsuario.setInt(3, 3);             // El rol de estudiante es 3

            int rowsInsertedUsuario = stmtUsuario.executeUpdate();

            if (rowsInsertedUsuario > 0) {
                // Obtener el ID del usuario insertado
                ResultSet generatedKeys = stmtUsuario.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int usuarioId = generatedKeys.getInt(1); // Obtener el ID generado

                    // Ahora insertamos el estudiante
                    PreparedStatement stmtEstudiante = conn.prepareStatement(insertEstudianteQuery);
                    stmtEstudiante.setString(1, codigo);       // Código del estudiante
                    stmtEstudiante.setInt(2, usuarioId);       // ID del usuario insertado
                    stmtEstudiante.setString(3, nombre);       // Nombre del estudiante
                    stmtEstudiante.setString(4, apellido);     // Apellido del estudiante
                    stmtEstudiante.setString(5, correo);       // Correo del estudiante

                    int rowsInsertedEstudiante = stmtEstudiante.executeUpdate();
                    return rowsInsertedEstudiante > 0; // Retorna true si se insertó correctamente
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false en caso de error
    }
    
    public boolean agregarDocente(String codigo, String nombre, String apellido, String correo, String area) {
        String insertUsuarioQuery = "INSERT INTO usuarios (usuario, contrasena, rol_id) VALUES (?, ?, ?)";
        String insertDocenteQuery = "INSERT INTO docentes (codigo, usuario_id, nombre, apellido, correo, area) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection()) {
            // Primero, insertamos el usuario
            PreparedStatement stmtUsuario = conn.prepareStatement(insertUsuarioQuery, Statement.RETURN_GENERATED_KEYS);
            stmtUsuario.setString(1, correo);  // El correo como usuario
            stmtUsuario.setString(2, codigo);  // El código como contraseña
            stmtUsuario.setInt(3, 2);             // El rol de docente es 2

            int rowsInsertedUsuario = stmtUsuario.executeUpdate();

            if (rowsInsertedUsuario > 0) {
                // Obtener el ID del usuario insertado
                ResultSet generatedKeys = stmtUsuario.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int usuarioId = generatedKeys.getInt(1); // Obtener el ID generado

                    // Ahora insertamos el estudiante
                    PreparedStatement stmtDocente = conn.prepareStatement(insertDocenteQuery);
                    stmtDocente.setString(1, codigo);       // Código del docente
                    stmtDocente.setInt(2, usuarioId);       // ID del usuario insertado
                    stmtDocente.setString(3, nombre);       // Nombre del docente
                    stmtDocente.setString(4, apellido);     // Apellido del docente
                    stmtDocente.setString(5, correo);       // Correo del docente
                    stmtDocente.setString(6, area);       // Area del docente
                    
                    int rowsInsertedDocente = stmtDocente.executeUpdate();
                    return rowsInsertedDocente > 0; // Retorna true si se insertó correctamente
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false en caso de error
    }

    public Estudiante obtenerEstudiante(String codigo) {
    String query = "SELECT e.codigo, e.nombre, e.apellido, e.correo " +
                   "FROM estudiantes e " +
                   "JOIN usuarios u ON e.usuario_id = u.id " +
                   "WHERE e.codigo = ?";

    try (Connection conn = ConexionDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, codigo);  // Asigna el código al parámetro de la consulta
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            // Extrae los datos del ResultSet
            String codigoEstudiante = rs.getString("codigo");
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String correo = rs.getString("correo");

            // Crea el objeto Estudiante usando valores predeterminados para parámetros adicionales
            return new Estudiante(
                0,                  // id
                "",                 // usuario
                "",                 // contrasena
                0,                  // rolId
                codigoEstudiante,   // código
                nombre,             // nombre
                apellido,           // apellido
                correo             // correo
            );
        }
        } catch (SQLException e) {
            e.printStackTrace();  // Maneja la excepción (puedes agregar un mejor manejo si lo prefieres)
        }

        return null; // Retorna null si no se encuentra el estudiante o si ocurre un error
    }
    
    public boolean actualizarEstudiante(String codigo, String nombre, String apellido, String correo) {
        String query = "UPDATE estudiantes SET nombre = ?, apellido = ?, correo = ? WHERE codigo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombre);   // Asigna el nombre al primer parámetro
            stmt.setString(2, apellido); // Asigna el apellido al segundo parámetro
            stmt.setString(3, correo);   // Asigna el correo al tercer parámetro
            stmt.setString(4, codigo);   // Usa el código para identificar al estudiante

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Retorna true si se actualizó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de la excepción (puedes mejorar el manejo si lo deseas)
        }

        return false; // Retorna false en caso de error
    }

}
