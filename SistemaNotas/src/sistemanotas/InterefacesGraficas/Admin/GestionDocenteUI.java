package sistemanotas.InterefacesGraficas.Admin;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import sistemanotas.Estructura.AdminService;

public class GestionDocenteUI extends JFrame {

    private AdminService adminService;
    private String codigoDocente;
    private JPanel mainPanel;

    public GestionDocenteUI(String codigoDocente) {
        this.codigoDocente = codigoDocente;
        this.adminService = new AdminService();

        setTitle("Gestión - Admin");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar el panel principal con fondo personalizado
        URL rutaImagen = getClass().getResource("/proyecto_images/gestion_docente.jpg"); // Usamos getResource
        if (rutaImagen != null) {
            mainPanel = new FondoPanel(rutaImagen);
        } else {
            System.err.println("No se encontró la imagen de fondo, se usará un panel por defecto.");
            mainPanel = new JPanel();
        }
        mainPanel.setLayout(new BorderLayout());

        // Título estilizado con borde negro en las letras
        JLabel titleLabel = new JLabel("Gestion Docente", JLabel.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Configuración de la fuente
                g2d.setFont(new Font("Arial Black", Font.BOLD, 40));

                // Borde negro alrededor del texto
                g2d.setColor(Color.BLACK);
                g2d.drawString(getText(), 5, getHeight() / 2 + 10);

                // Texto principal en azul
                g2d.setColor(new Color(26, 184, 243));
                g2d.drawString(getText(), 3, getHeight() / 2 + 8);
            }
        };

        // Agregar el JLabel al panel principal
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Crear botones estilizados
        JButton editarDocenteButton = crearBoton("Editar Docente");
        JButton eliminarDocenteButton = crearBoton("Eliminar Docente");
        JButton asignarCursoButton = crearBoton("Asignar Curso");
        JButton eliminarCursoButton = crearBoton("Eliminar Curso");
        JButton salirButton = crearBoton("Salir");

        // Agregar listeners
        editarDocenteButton.addActionListener(e -> abrirEditarDocente());
        eliminarDocenteButton.addActionListener(e -> eliminarDocente());
        asignarCursoButton.addActionListener(e -> abrirAsignarCurso());
        eliminarCursoButton.addActionListener(e -> eliminarCursoDocente());
        salirButton.addActionListener(e -> dispose());

        // Añadir botones al panel
        gbc.gridy = 0; buttonPanel.add(editarDocenteButton, gbc);
        gbc.gridy = 1; buttonPanel.add(eliminarDocenteButton, gbc);
        gbc.gridy = 2; buttonPanel.add(asignarCursoButton, gbc);
        gbc.gridy = 3; buttonPanel.add(eliminarCursoButton, gbc);
        gbc.gridy = 4; buttonPanel.add(salirButton, gbc);

        // Agregar el panel de botones al panel principal
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.setBackground(new Color(0, 102, 204));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void abrirEditarDocente() {
        new EditarDocenteUI(codigoDocente).setVisible(true);
    }

    private void eliminarDocente() {
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea eliminar este docente?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean eliminado = adminService.eliminarDocente(codigoDocente);
            if (eliminado) {
                JOptionPane.showMessageDialog(null, "Docente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar docente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirAsignarCurso() {
        new AsignarCursoUI(codigoDocente).setVisible(true);
    }

    private void eliminarCursoDocente() {
        List<String> cursosAsignados = adminService.obtenerCursosPorDocente(codigoDocente);

        if (cursosAsignados == null || cursosAsignados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El docente no tiene cursos asignados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] cursosArray = cursosAsignados.toArray(new String[0]);
        String cursoSeleccionado = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione el curso a eliminar:",
                "Eliminar Curso del Docente",
                JOptionPane.PLAIN_MESSAGE,
                null,
                cursosArray,
                cursosArray[0]
        );

        if (cursoSeleccionado != null && !cursoSeleccionado.isEmpty()) {
            String codigoCurso = adminService.obtenerCodigoCursoPorNombre(cursoSeleccionado);
            boolean eliminado = adminService.eliminarCursoDeDocente(codigoCurso, codigoDocente);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Curso eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el curso. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se seleccionó ningún curso.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Clase para personalizar el panel con fondo
    private static class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel(URL rutaImagen) {
            if (rutaImagen != null) {
                imagenFondo = new ImageIcon(rutaImagen).getImage();
            } else {
                System.err.println("La URL de la imagen de fondo es nula.");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenFondo != null) {
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestionDocenteUI("DOC123").setVisible(true));
    }
}
