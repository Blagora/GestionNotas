package sistemanotas.InterefacesGraficas.docente;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import sistemanotas.ConexionBD.ConexionDB;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionGruposUI extends JFrame {
    private String docenteId;

    public GestionGruposUI(String docenteId) {
        this.docenteId = docenteId;
        setTitle("Gestionar Grupos");
        setSize(500, 350); // Tamaño ajustado para el diseño visual
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Diseño del panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Usamos GridBagLayout para alineación
        panel.setBackground(new Color(240, 248, 255)); // Fondo azul claro

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes

        // Etiqueta de título
        JLabel tituloLabel = new JLabel("Gestión de Grupos");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setForeground(new Color(0, 51, 102)); // Azul oscuro

        // Componentes
        JLabel cursoLabel = new JLabel("Curso:");
        cursoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JComboBox<String> cursoComboBox = new JComboBox<>(getCursos()); // Cargar cursos del docente

        JLabel corteLabel = new JLabel("Corte:");
        corteLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JComboBox<String> corteComboBox = new JComboBox<>(getCortes()); // Cargar cortes para el curso

        JLabel grupoLabel = new JLabel("Nombre del Grupo:");
        grupoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField grupoField = new JTextField(20);

        JLabel porcentajeLabel = new JLabel("Porcentaje:");
        porcentajeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField porcentajeField = new JTextField(5);

        JButton guardarButton = new JButton("Guardar");
        guardarButton.setBackground(new Color(30, 144, 255)); // Azul claro
        guardarButton.setForeground(Color.WHITE);
        guardarButton.setFont(new Font("Arial", Font.BOLD, 14));
        guardarButton.setFocusPainted(false);

        JButton verGruposButton = new JButton("Ver Grupos");
        verGruposButton.setBackground(new Color(34, 139, 34)); // Verde oscuro
        verGruposButton.setForeground(Color.WHITE);
        verGruposButton.setFont(new Font("Arial", Font.BOLD, 14));
        verGruposButton.setFocusPainted(false);

        // Alineación y agregación de componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(tituloLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        panel.add(cursoLabel, gbc);

        gbc.gridx = 1;
        panel.add(cursoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(corteLabel, gbc);

        gbc.gridx = 1;
        panel.add(corteComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(grupoLabel, gbc);

        gbc.gridx = 1;
        panel.add(grupoField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(porcentajeLabel, gbc);

        gbc.gridx = 1;
        panel.add(porcentajeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(guardarButton, gbc);

        gbc.gridy++;
        panel.add(verGruposButton, gbc);

        add(panel);

        // Evento para guardar un grupo con porcentaje
        guardarButton.addActionListener(e -> {
            String curso = (String) cursoComboBox.getSelectedItem();
            String corte = (String) corteComboBox.getSelectedItem();
            String grupoNombre = grupoField.getText().trim();
            String porcentajeStr = porcentajeField.getText().trim();

            if (grupoNombre.isEmpty() || porcentajeStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int porcentaje = Integer.parseInt(porcentajeStr);
            if (porcentaje < 0 || porcentaje > 100) {
                JOptionPane.showMessageDialog(this, "El porcentaje debe estar entre 0 y 100.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Llamar al método para guardar el grupo con su porcentaje
            guardarGrupo(curso, corte, grupoNombre, porcentaje);
        });

        // Evento para ver los grupos
        verGruposButton.addActionListener(e -> {
            String curso = (String) cursoComboBox.getSelectedItem();
            verGrupos(curso);
        });
    }

    private String[] getCursos() {
        List<String> cursos = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection()) {
            String query = "SELECT codigo, nombre FROM cursos WHERE docente_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, docenteId); // Usa el ID del docente
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String curso = rs.getString("codigo") + " - " + rs.getString("nombre");
                cursos.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los cursos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return cursos.toArray(new String[0]); // Convierte la lista a un arreglo
    }

    private String[] getCortes() {
        List<String> cortes = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection()) {
            String query = "SELECT nombre FROM cortes WHERE curso_id = (SELECT id FROM cursos WHERE docente_id = ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, docenteId); // Usa el ID del docente
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cortes.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los cortes.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return cortes.toArray(new String[0]); // Convierte la lista a un arreglo
    }

    private void guardarGrupo(String curso, String corte, String grupoNombre, int porcentaje) {
        try (Connection conn = ConexionDB.getConnection()) {
            String codigoCurso = curso.split(" - ")[0]; // Asume que el formato es "Código - Nombre"
            String nombreCorte = corte; // Corte seleccionado
            int corteId = obtenerCorteId(conn, codigoCurso, nombreCorte);

            if (corteId == -1) {
                JOptionPane.showMessageDialog(this, "Corte no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar que no exista un grupo con el mismo nombre en el mismo corte
            if (existeGrupoEnCorte(conn, corteId, grupoNombre)) {
                JOptionPane.showMessageDialog(this, "Ya existe un grupo con el mismo nombre en este corte.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar que los porcentajes no sumen más de 100% (si hay otros grupos asociados a este corte)
            if (validarPorcentajeTotal(corteId, porcentaje)) {
                JOptionPane.showMessageDialog(this, "El porcentaje total no puede superar el 100%.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insertar el nuevo grupo de notas con su porcentaje
            String query = "INSERT INTO grupos_notas (corte_id, nombre, porcentaje) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, corteId);
                stmt.setString(2, grupoNombre);
                stmt.setInt(3, porcentaje);
                stmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Grupo '" + grupoNombre + "' guardado exitosamente con el porcentaje de " + porcentaje + "%.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el grupo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean existeGrupoEnCorte(Connection conn, int corteId, String grupoNombre) throws SQLException {
        String query = "SELECT 1 FROM grupos_notas WHERE corte_id = ? AND nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, corteId);
            stmt.setString(2, grupoNombre);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Si existe un resultado, significa que ya hay un grupo con el mismo nombre
        }
    }


    private int obtenerCorteId(Connection conn, String codigoCurso, String nombreCorte) throws SQLException {
        String query = "SELECT id FROM cortes WHERE curso_id = (SELECT id FROM cursos WHERE codigo = ?) AND nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codigoCurso);
            stmt.setString(2, nombreCorte);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1; // No se encontró el corte
            }
        }
    }

    private boolean validarPorcentajeTotal(int corteId, int nuevoPorcentaje) {
        int porcentajeTotal = nuevoPorcentaje;
        try (Connection conn = ConexionDB.getConnection()) {
            String query = "SELECT porcentaje FROM grupos_notas WHERE corte_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, corteId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    porcentajeTotal += rs.getInt("porcentaje");
                }
            }

            return porcentajeTotal > 100;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void verGrupos(String curso) {
        String codigoCurso = curso.split(" - ")[0];
        StringBuilder gruposTexto = new StringBuilder("Grupos asociados al curso: " + curso + "\n");

        try (Connection conn = ConexionDB.getConnection()) {
            String query = "SELECT g.nombre, g.porcentaje FROM grupos_notas g " +
                    "JOIN cortes c ON g.corte_id = c.id " +
                    "WHERE c.curso_id = (SELECT id FROM cursos WHERE codigo = ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, codigoCurso);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    gruposTexto.append("- ").append(rs.getString("nombre"))
                            .append(" (").append(rs.getInt("porcentaje")).append("%)\n");
                }
            }

            JOptionPane.showMessageDialog(this, gruposTexto.toString(), "Grupos", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los grupos del curso.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
