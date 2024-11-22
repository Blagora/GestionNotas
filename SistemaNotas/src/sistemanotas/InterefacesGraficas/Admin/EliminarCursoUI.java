package sistemanotas.InterefacesGraficas.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import sistemanotas.Estructura.AdminService;

public class EliminarCursoUI extends JFrame {
    private AdminService adminService;

    public EliminarCursoUI(AdminService adminService) {
        this.adminService = adminService;

        // Configuración básica de la ventana
        setTitle("Eliminar Curso");
        setSize(450, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con fondo azul claro
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(173, 216, 230)); // Fondo azul claro

        // Etiqueta de título estilizada
        JLabel label = new JLabel("Seleccione un curso para eliminar:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(0, 102, 204)); // Azul fuerte
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(label, BorderLayout.NORTH);

        // Obtener lista de cursos
        List<String> cursos = adminService.obtenerTodosLosCursos();
        String[] cursosArray = cursos.toArray(new String[0]);

        // Lista con diseño moderno
        JList<String> listaCursos = new JList<>(cursosArray);
        listaCursos.setFont(new Font("Arial", Font.PLAIN, 14));
        listaCursos.setSelectionBackground(new Color(0, 153, 76)); // Fondo verde al seleccionar
        listaCursos.setSelectionForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(listaCursos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Cursos disponibles"));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botón "Eliminar" estilizado
        JButton eliminarButton = new JButton("Eliminar Curso");
        eliminarButton.setFont(new Font("Arial", Font.BOLD, 14));
        eliminarButton.setBackground(new Color(0, 153, 76)); // Verde
        eliminarButton.setForeground(Color.WHITE);
        eliminarButton.setFocusPainted(false);
        eliminarButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Acción del botón "Eliminar"
        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cursoSeleccionado = listaCursos.getSelectedValue();

                if (cursoSeleccionado != null) {
                    // Obtener el código del curso por su nombre
                    String codigoCurso = adminService.obtenerCodigoCursoPorNombre(cursoSeleccionado);

                    // Eliminar el curso
                    boolean eliminado = adminService.eliminarCurso(codigoCurso);

                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Curso eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // Cierra la ventana después de eliminar
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar el curso. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un curso.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Panel para el botón
        JPanel botonPanel = new JPanel();
        botonPanel.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        botonPanel.add(eliminarButton);
        panel.add(botonPanel, BorderLayout.SOUTH);

        // Agregar panel a la ventana
        add(panel);
    }
}

