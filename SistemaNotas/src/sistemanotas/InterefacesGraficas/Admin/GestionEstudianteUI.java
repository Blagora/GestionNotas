package sistemanotas.InterefacesGraficas.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import sistemanotas.Estructura.AdminService;

public class GestionEstudianteUI extends JFrame {

    private String codigoEstudiante;
    private AdminService adminService;

    public GestionEstudianteUI(String codigoEstudiante) {

        this.adminService = new AdminService();
        this.codigoEstudiante = codigoEstudiante;

        setTitle("Gestión de Estudiantes");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con color de fondo azul claro
        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230)); // Azul claro
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Márgenes

        // Crear botones
        JButton editarEstudianteButton = new JButton("Editar Estudiante");
        JButton eliminarEstudianteButton = new JButton("Eliminar Estudiante");
        JButton asignarCursoButton = new JButton("Asignar Curso");
        JButton eliminarCursoButton = new JButton("Eliminar Curso");

        // Estilo de botones
        editarEstudianteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        eliminarEstudianteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        asignarCursoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        eliminarCursoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        editarEstudianteButton.setFont(new Font("Arial", Font.BOLD, 14));
        eliminarEstudianteButton.setFont(new Font("Arial", Font.BOLD, 14));
        asignarCursoButton.setFont(new Font("Arial", Font.BOLD, 14));
        eliminarCursoButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Espaciado entre botones
        panel.add(editarEstudianteButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(eliminarEstudianteButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(asignarCursoButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(eliminarCursoButton);

        add(panel);

        // Asignar acciones a los botones
        asignarCursoButton.addActionListener(e -> asignarCurso());
        eliminarCursoButton.addActionListener(e -> eliminarCurso());
        editarEstudianteButton.addActionListener(e -> abrirEditarEstudiante());
        eliminarEstudianteButton.addActionListener(e -> eliminarEstudiante());
    }

    private void asignarCurso() {
        List<String> cursos = adminService.obtenerCursosDisponibles();

        if (cursos == null || cursos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay cursos disponibles para asignar.", "Información", JOptionPane.INFORMATION_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Error al asignar el curso. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarCurso() {
        List<String> cursosAsignados = adminService.obtenerCursosAsignados(codigoEstudiante);

        if (cursosAsignados == null || cursosAsignados.isEmpty()) {
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
                JOptionPane.showMessageDialog(this, "Error al eliminar el curso. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirEditarEstudiante() {
        new EditarEstudianteUI(codigoEstudiante).setVisible(true);
    }

    private void eliminarEstudiante() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este estudiante?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

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
}
