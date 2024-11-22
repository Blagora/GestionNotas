package sistemanotas.InterefacesGraficas;

import sistemanotas.InterefacesGraficas.Admin.AdminUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.sql.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import sistemanotas.ConexionBD.ConexionDB;
import sistemanotas.Estructura.Docente;
import sistemanotas.Estructura.Estudiante;
import sistemanotas.InterefacesGraficas.Estudiante.EstudianteUI;
import sistemanotas.InterefacesGraficas.docente.DocenteUI;

public class Login extends JFrame {

    private JTextField usuarioField;
    private JPasswordField contrasenaField;

    public Login() {
        setTitle("Login - Sistema de Notas");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar el panel principal con fondo personalizado
        JPanel mainPanel = new FondoPanel(getClass().getResource("/proyecto_images/fondo_login.jpg").getPath());
        mainPanel.setLayout(new GridBagLayout());

        // Configuración del título
        JLabel tituloLabel = new JLabel("Sistema de Notas", JLabel.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 35));
                g2d.setColor(Color.BLACK);
                g2d.drawString(getText(), getInsets().left + 4, getHeight() / 2 + 12);
                g2d.setColor(new Color(52, 75, 241));
                g2d.drawString(getText(), getInsets().left, getHeight() / 2 + 10);
            }
        };
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear etiquetas
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        usuarioLabel.setForeground(new Color(52, 75, 241));

        JLabel contrasenaLabel = new JLabel("Contraseña:");
        contrasenaLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        contrasenaLabel.setForeground(new Color(52, 75, 241));

        // Crear campos de texto
        usuarioField = new JTextField(15);
        contrasenaField = new JPasswordField(15);
        usuarioField.setBorder(BorderFactory.createLineBorder(new Color(52, 75, 241), 2));
        contrasenaField.setBorder(BorderFactory.createLineBorder(new Color(52, 75, 241), 2));

        // Crear botón de login con efecto hover
        JButton loginButton = new JButton("Ingresar");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 122, 204));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 102, 204));
            }
        });

        // Configurar el layout del panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Agregar componentes al panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(tituloLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        mainPanel.add(usuarioLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(usuarioField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(contrasenaLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(contrasenaField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(loginButton, gbc);

        // Acción del botón
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });

        add(mainPanel);
    }

     private void autenticarUsuario() {
        String usuario = usuarioField.getText();
        String contrasena = new String(contrasenaField.getPassword());

        try (Connection conn = ConexionDB.getConnection()) {
            String query = "SELECT id, usuario, contrasena, rol_id FROM usuarios WHERE usuario = ? AND contrasena = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                int rolId = rs.getInt("rol_id");

                switch (rolId) {
                    case 1: // Admin
                        JOptionPane.showMessageDialog(this, "Ingreso como Admin");
                        new AdminUI().setVisible(true);
                        break;

                    case 2: // Docente
                        Docente docente = obtenerDocente(conn, userId);
                        if (docente != null) {
                            JOptionPane.showMessageDialog(this, "Ingreso como Docente");
                            new DocenteUI(contrasena).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(this, "No se encontraron datos del docente.");
                        }
                        break;

                    case 3: // Estudiante
                        Estudiante estudiante = obtenerEstudiante(conn, userId);
                        if (estudiante != null) {
                            JOptionPane.showMessageDialog(this, "Ingreso como Estudiante");
                            new EstudianteUI(contrasena).setVisible(true); // Pasar el objeto Estudiante
                        } else {
                            JOptionPane.showMessageDialog(this, "No se encontraron datos del estudiante.");
                        }
                        break;

                    default:
                        JOptionPane.showMessageDialog(this, "Rol desconocido");
                        break;
                }
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    private Docente obtenerDocente(Connection conn, int userId) {
        String query = "SELECT d.codigo, d.nombre, d.apellido, d.correo, d.area " +
                       "FROM docentes d " +
                       "JOIN usuarios u ON d.usuario_id = u.id " +
                       "WHERE u.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String area = rs.getString("area");

                return new Docente(userId, null, null, 2, codigo, nombre, apellido, correo, area);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener datos del docente.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    return null;
    
    
    }
    private Estudiante obtenerEstudiante(Connection conn, int userId) {
        String query = "SELECT e.codigo, e.nombre, e.apellido, e.correo " +
                       "FROM estudiantes e " +
                       "JOIN usuarios u ON e.usuario_id = u.id " +
                       "WHERE u.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");

                return new Estudiante(userId, null, null, 3, codigo, nombre, apellido, correo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener datos del estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    // Clase para personalizar el panel con fondo difuminado
    private static class FondoPanel extends JPanel {
        private BufferedImage imagenFondo;

        public FondoPanel(String rutaImagen) {
            try {
                ImageIcon originalIcon = new ImageIcon(rutaImagen);
                Image originalImage = originalIcon.getImage();
                BufferedImage bufferedImage = new BufferedImage(
                        originalImage.getWidth(null),
                        originalImage.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2d = bufferedImage.createGraphics();
                g2d.drawImage(originalImage, 0, 0, null);

                // Aplicar filtro de desenfoque
                Kernel kernel = new Kernel(3, 3, new float[]{
                        0.0f, 0.2f, 0.0f,
                        0.2f, 0.2f, 0.2f,
                        0.0f, 0.2f, 0.0f
                });
                ConvolveOp convolve = new ConvolveOp(kernel);
                bufferedImage = convolve.filter(bufferedImage, null);
                this.imagenFondo = bufferedImage;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imagenFondo, 0, 0, null);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
