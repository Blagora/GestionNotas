package sistemanotas.InterefacesGraficas.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import sistemanotas.Estructura.AdminService;

public class GestionEstudianteUI extends JFrame {

    private String codigoEstudiante;
    private AdminService adminService;

    public GestionEstudianteUI(String codigoEstudiante) {
        this.adminService = new AdminService();
        this.codigoEstudiante = codigoEstudiante;

        setTitle("Gestión de Estudiantes");
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Estilo general
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0, 102, 204)); // Fondo azul
        add(mainPanel);

        // Estilo común para los botones
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Border buttonBorder = BorderFactory.createLineBorder(Color.WHITE, 2);

        // Crear botones estilizados
        JButton editarEstudianteButton = crearBoton("Editar Estudiante", buttonFont, buttonBorder, new Color(0, 153, 51), Color.WHITE);
        JButton eliminarEstudianteButton = crearBoton("Eliminar Estudiante", buttonFont, buttonBorder, new Color(204, 0, 0), Color.WHITE);
        JButton asignarCursoButton = crearBoton("Asignar Curso", buttonFont, buttonBorder, new Color(0, 153, 153), Color.WHITE);
        JButton eliminarCursoButton = crearBoton("Eliminar Curso", buttonFont, buttonBorder, new Color(153, 51, 204), Color.WHITE);

        // Crear etiquetas estilizadas
        JLabel tituloLabel = new JLabel("Gestión de Estudiantes");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Posicionar componentes
        tituloLabel.setBounds(50, 30, 400, 50);

        int buttonX = 150, buttonWidth = 200, buttonHeight = 50;
        int yOffset = 80;

        editarEstudianteButton.setBounds(buttonX, 100 + yOffset * 0, buttonWidth, buttonHeight);
        eliminarEstudianteButton.setBounds(buttonX, 100 + yOffset * 1, buttonWidth, buttonHeight);
        asignarCursoButton.setBounds(buttonX, 100 + yOffset * 2, buttonWidth, buttonHeight);
        eliminarCursoButton.setBounds(buttonX, 100 + yOffset * 3, buttonWidth, buttonHeight);

        // Agregar componentes al panel
        mainPanel.add(tituloLabel);
        mainPanel.add(editarEstudianteButton);
        mainPanel.add(eliminarEstudianteButton);
        mainPanel.add(asignarCursoButton);
        mainPanel.add(eliminarCursoButton);

        // Acciones de los botones
        editarEstudianteButton.addActionListener(e -> abrirEditarEstudiante());
        eliminarEstudianteButton.addActionListener(e -> eliminarEstudiante());
        asignarCursoButton.addActionListener(e -> asignarCurso());
        eliminarCursoButton.addActionListener(e -> eliminarCurso());
    }

    private JButton crearBoton(String texto, Font font, Border border, Color background, Color foreground) {
        JButton boton = new JButton(texto);
        boton.setFont(font);
        boton.setBorder(border);
        boton.setBackground(background);
        boton.setForeground(foreground);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void asignarCurso() {
        List<String> cursos = adminService.obtenerCursosDisponibles(); // Cursos disponibles

        if (cursos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay cursos disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] cursosArray = cursos.toArray(new String[0]);
        String cursoSeleccionado = (String) JOptionPane.showInputDialog(this,
                "Selecciona un curso",
                "Asignar Curso",
                JOptionPane.PLAIN_MESSAGE,
                null,
                cursosArray,
                cursosArray[0]);

        if (cursoSeleccionado != null) {
            String codigoCurso = adminService.obtenerCodigoCursoPorNombre(cursoSeleccionado);
            boolean asignado = adminService.asignarCursoAEstudiante(codigoEstudiante, codigoCurso);

            if (asignado) {
                JOptionPane.showMessageDialog(this, "Curso asignado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al asignar el curso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarCurso() {
        List<String> cursosAsignados = adminService.obtenerCursosAsignados(codigoEstudiante);

        if (cursosAsignados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El estudiante no tiene cursos asignados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] cursosArray = cursosAsignados.toArray(new String[0]);
        String cursoSeleccionado = (String) JOptionPane.showInputDialog(this,
                "Selecciona el curso a eliminar",
                "Eliminar Curso",
                JOptionPane.PLAIN_MESSAGE,
                null,
                cursosArray,
                cursosArray[0]);

        if (cursoSeleccionado != null) {
            String codigoCurso = adminService.obtenerCodigoCursoPorNombre(cursoSeleccionado);
            boolean eliminado = adminService.eliminarCursoDeEstudiante(codigoEstudiante, codigoCurso);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Curso eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el curso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarEstudiante() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este estudiante?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = adminService.eliminarEstudiante(codigoEstudiante);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Estudiante eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirEditarEstudiante() {
        new EditarEstudianteUI(codigoEstudiante).setVisible(true);
    }
}
