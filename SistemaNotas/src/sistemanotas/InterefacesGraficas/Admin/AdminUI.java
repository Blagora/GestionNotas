package sistemanotas.InterefacesGraficas.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import sistemanotas.Estructura.AdminService;
import sistemanotas.InterefacesGraficas.Login;

public class AdminUI extends JFrame {

    private AdminService adminService;

    public AdminUI() {
        adminService = new AdminService();

        setTitle("Panel de Administración - Admin");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar el panel principal con fondo personalizado
        JPanel mainPanel = new FondoPanel("C:\\Users\\Admin\\Pictures\\proyecto_images\\imagen_admin.jpg");
        mainPanel.setLayout(new BorderLayout());

 // Título estilizado con borde negro en las letras
JLabel titleLabel = new JLabel("Panel de Administración", JLabel.CENTER) {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Configuración de la fuente
        g2d.setFont(new Font("Arial Black", Font.BOLD, 40));

        // Borde negro alrededor del texto
        g2d.setColor(Color.BLACK); // Color de borde
        g2d.drawString(getText(), 5, getHeight() / 2 + 10); // Desplazamiento para borde

        // Texto principal en azul
        g2d.setColor(new Color(26, 184, 243)); // Color del texto
        g2d.drawString(getText(), 3, getHeight() / 2 + 8); // Desplazamiento para el texto
    }
};

        // Agregar el JLabel al panel principal
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Permitir transparencia para mostrar el fondo
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Crear los botones estilizados
        JButton gestionarEstudiantesButton = crearBoton("Gestionar Estudiantes");
        JButton agregarEstudianteButton = crearBoton("Agregar Nuevo Estudiante");
        JButton gestionarDocentesButton = crearBoton("Gestionar Docentes");
        JButton agregarDocenteButton = crearBoton("Agregar Nuevo Docente");
        JButton crearCursoButton = crearBoton("Crear Nuevo Curso");
        JButton eliminarCursoButton = crearBoton("Eliminar Curso");
        JButton salirButton = crearBoton("Salir");

        // Agregar listeners
        gestionarEstudiantesButton.addActionListener(e -> abrirValidarEstudiantes());
        agregarEstudianteButton.addActionListener(e -> abrirAgregarEstudiante());
        gestionarDocentesButton.addActionListener(e -> abrirValidarDocentes());
        agregarDocenteButton.addActionListener(e -> abrirAgregarDocente());
        crearCursoButton.addActionListener(e -> abrirCrearCurso());
        eliminarCursoButton.addActionListener(e -> abrirEliminarCurso());
        salirButton.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        // Añadir botones al panel
        gbc.gridy = 0; buttonPanel.add(gestionarEstudiantesButton, gbc);
        gbc.gridy = 1; buttonPanel.add(agregarEstudianteButton, gbc);
        gbc.gridy = 2; buttonPanel.add(gestionarDocentesButton, gbc);
        gbc.gridy = 3; buttonPanel.add(agregarDocenteButton, gbc);
        gbc.gridy = 4; buttonPanel.add(crearCursoButton, gbc);
        gbc.gridy = 5; buttonPanel.add(eliminarCursoButton, gbc);
        gbc.gridy = 6; buttonPanel.add(salirButton, gbc);

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

    private void abrirValidarEstudiantes() {
        new ValidarEstudianteUI().setVisible(true);
    }

    private void abrirAgregarEstudiante() {
        new AgregarEstudianteUI().setVisible(true);
    }

    private void abrirValidarDocentes() {
        new ValidarDocenteUI().setVisible(true);
    }

    private void abrirAgregarDocente() {
        new AgregarDocenteUI().setVisible(true);
    }

    private void abrirCrearCurso() {
        new CrearCursoUI(adminService).setVisible(true);
    }

    private void abrirEliminarCurso() {
        new EliminarCursoUI(adminService).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminUI().setVisible(true));
    }

    // Clase para personalizar el panel con fondo
    private static class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel(String rutaImagen) {
            try {
                imagenFondo = new ImageIcon(rutaImagen).getImage();
            } catch (Exception e) {
                System.err.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
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
}

