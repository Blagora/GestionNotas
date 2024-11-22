package sistemanotas.InterefacesGraficas.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import sistemanotas.Estructura.AdminService;

public class AsignarCursoUI extends JFrame {

    private String codigoDocente;
    private AdminService adminService;
    private JComboBox<String> cursosComboBox;
    private JButton asignarButton;

    public AsignarCursoUI(String codigoDocente) {
        this.codigoDocente = codigoDocente;
        adminService = new AdminService();
        
        setTitle("Asignar Curso al Docente");
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Personalización de estilo
        Font fieldFont = new Font("Arial", Font.PLAIN, 14); // Fuente para los campos
        Border fieldBorder = BorderFactory.createLineBorder(new Color(0, 51, 153), 2); // Borde azul

        // Crear componentes estilizados
        cursosComboBox = new JComboBox<>();
        cursosComboBox.setFont(fieldFont);
        cursosComboBox.setBorder(fieldBorder);

        asignarButton = new JButton("Asignar Curso");
        asignarButton.setFont(new Font("Arial", Font.BOLD, 14));
        asignarButton.setBackground(new Color(0, 153, 51)); // Botón verde
        asignarButton.setForeground(Color.WHITE);
        asignarButton.setFocusPainted(false);

        // Cargar los cursos disponibles en el combo box
        cargarCursos();

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240)); // Fondo claro
        panel.setLayout(new GridBagLayout()); // Usar GridBagLayout para un diseño más ordenado

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Agregar los componentes al panel con sus posiciones
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Selecciona un curso:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(cursosComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(asignarButton, gbc);

        add(panel);

        // Acción para asignar el curso
        asignarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarCurso();
            }
        });
    }

    // Cargar los cursos disponibles en el combo box
    private void cargarCursos() {
        List<String> cursos = adminService.obtenerCursosDisponibles(); // Método que recupera los cursos
        for (String curso : cursos) {
            cursosComboBox.addItem(curso);
        }
    }

    // Asignar el curso seleccionado al docente
    private void asignarCurso() {
        String cursoSeleccionado = (String) cursosComboBox.getSelectedItem();

        if (cursoSeleccionado != null && !cursoSeleccionado.isEmpty()) {
            // Obtén el código del curso para asignarlo al docente
            String codigoCurso = obtenerCodigoCurso(cursoSeleccionado);

            // Verificar si el docente ya tiene asignado el curso
            boolean cursoAsignado = adminService.verificarCursoAsignado(codigoDocente, codigoCurso);

            if (cursoAsignado) {
                JOptionPane.showMessageDialog(this, "Este curso ya ha sido asignado al docente.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Asigna el curso al docente
                boolean asignado = adminService.asignarCursoADocente(codigoCurso, codigoDocente);

                if (asignado) {
                    JOptionPane.showMessageDialog(this, "Curso asignado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Cierra la ventana tras asignar el curso
                } else {
                    JOptionPane.showMessageDialog(this, "Error al asignar el curso. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un curso.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obtener el código del curso basado en su nombre
    private String obtenerCodigoCurso(String nombreCurso) {
        // Aquí deberías implementar un método que recupere el código de un curso dado su nombre
        return adminService.obtenerCodigoCursoPorNombre(nombreCurso);
    }
}
