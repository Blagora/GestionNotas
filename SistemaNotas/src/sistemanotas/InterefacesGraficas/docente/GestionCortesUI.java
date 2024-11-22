package sistemanotas.InterefacesGraficas.docente;

import sistemanotas.ConexionBD.ConexionDB;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionCortesUI extends JFrame {
    private String docenteId;
    private JTextField[] porcentajeFields; // Campos para los porcentajes de cada corte
    private JPanel porcentajePanel;

    public GestionCortesUI(String docenteId) {
        this.docenteId = docenteId;
        setTitle("Gestionar Cortes");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Diseño con un fondo azul
        JPanel panel = new FondoPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título de la ventana
        JLabel titleLabel = new JLabel("Gestión de Cortes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        // Componente de Curso
        JLabel cursoLabel = new JLabel("Curso:");
        cursoLabel.setForeground(Color.WHITE);
        JComboBox<String> cursoComboBox = new JComboBox<>(getCursos());
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(cursoLabel, gbc);

        gbc.gridx = 1;
        panel.add(cursoComboBox, gbc);

        // Componente de Número de Cortes
        JLabel corteLabel = new JLabel("Número de Cortes:");
        corteLabel.setForeground(Color.WHITE);
        JComboBox<Integer> corteComboBox = new JComboBox<>(new Integer[]{3, 4});
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(corteLabel, gbc);

        gbc.gridx = 1;
        panel.add(corteComboBox, gbc);

        // Panel para los porcentajes de cada corte
        porcentajePanel = new JPanel();
        porcentajePanel.setBackground(new Color(0, 64, 128)); // Fondo más oscuro para el panel de porcentajes
        porcentajeFields = new JTextField[4]; // Máximo de 4 cortes
        for (int i = 0; i < 4; i++) {
            JLabel label = new JLabel("Corte " + (i + 1) + ":");
            label.setForeground(Color.WHITE);
            porcentajeFields[i] = new JTextField(5);
            porcentajeFields[i].setBackground(Color.WHITE);
            porcentajeFields[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
            porcentajePanel.add(label);
            porcentajePanel.add(porcentajeFields[i]);
        }

        // Mostrar u ocultar campos según el número de cortes
        corteComboBox.addActionListener(e -> actualizarCamposPorcentaje((Integer) corteComboBox.getSelectedItem()));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(porcentajePanel, gbc);

        // Botón de Guardar
        JButton guardarButton = new JButton("Guardar");
        guardarButton.setBackground(new Color(0, 123, 255)); // Azul brillante
        guardarButton.setForeground(Color.WHITE);
        guardarButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 4;
        panel.add(guardarButton, gbc);

        // Evento para guardar los cortes
        guardarButton.addActionListener(e -> {
            String curso = (String) cursoComboBox.getSelectedItem();
            int numCortes = (Integer) corteComboBox.getSelectedItem();

            // Leer los porcentajes de cada corte
            double[] porcentajes = new double[numCortes];
            double sumaPorcentajes = 0;

            for (int i = 0; i < numCortes; i++) {
                try {
                    porcentajes[i] = Double.parseDouble(porcentajeFields[i].getText());
                    sumaPorcentajes += porcentajes[i];
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese un porcentaje válido para todos los cortes.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Validar que la suma de los porcentajes sea 100
            if (sumaPorcentajes != 100) {
                JOptionPane.showMessageDialog(this, "Los porcentajes no suman 100%.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                definirCortes(curso, numCortes, porcentajes);
            }
        });

        // Añadir panel al frame
        add(panel);
    }

    private void actualizarCamposPorcentaje(int numCortes) {
        for (int i = 0; i < porcentajeFields.length; i++) {
            porcentajeFields[i].setVisible(i < numCortes); // Mostrar solo los campos necesarios
        }
        porcentajePanel.revalidate();
        porcentajePanel.repaint();
    }

    public String[] getCursos() {
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

    private void definirCortes(String curso, int numCortes, double[] porcentajes) {
        // Extraer el código del curso seleccionado
        String codigoCurso = curso.split(" - ")[0]; // Asume que el formato es "Código - Nombre"

        try (Connection conn = ConexionDB.getConnection()) {
            conn.setAutoCommit(false); // Inicio de transacción

            // Eliminar cortes anteriores para el curso
            String deleteQuery = "DELETE FROM cortes WHERE curso_id = (SELECT id FROM cursos WHERE codigo = ?)";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, codigoCurso);
                deleteStmt.executeUpdate();
            }

            // Insertar los nuevos cortes con los porcentajes
            String insertQuery = "INSERT INTO cortes (curso_id, nombre, porcentaje) VALUES ((SELECT id FROM cursos WHERE codigo = ?), ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                for (int i = 0; i < numCortes; i++) {
                    insertStmt.setString(1, codigoCurso);
                    insertStmt.setString(2, "Corte " + (i + 1)); // Nombrar los cortes "Corte 1", "Corte 2", ...
                    insertStmt.setDouble(3, porcentajes[i]); // Asignar porcentaje específico
                    insertStmt.addBatch(); // Agregar al batch
                }
                insertStmt.executeBatch(); // Ejecutar en lote
            }

            conn.commit(); // Confirmar la transacción
            JOptionPane.showMessageDialog(this, "Cortes definidos para el curso: " + curso);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al definir los cortes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Panel con fondo azul
    class FondoPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(0, 123, 255)); // Azul de fondo
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
