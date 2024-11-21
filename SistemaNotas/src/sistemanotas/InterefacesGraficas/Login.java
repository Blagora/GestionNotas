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
import sistemanotas.ConexionBD.ConexionDB;


public class Login extends JFrame {

    private JTextField usuarioField;
    private JPasswordField contrasenaField;

    public Login() {
        setTitle("Login - Sistema de Notas");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar el panel principal con fondo personalizado
        JPanel mainPanel = new FondoPanel("C:\\Users\\Admin\\Pictures\\proyecto_images\\uan_login.png");
        mainPanel.setLayout(new GridBagLayout());

      JLabel tituloLabel = new JLabel("Sistema de Notas", JLabel.CENTER) {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Configuración de la fuente
        g2d.setFont(new Font("Arial Black", Font.BOLD, 30));

        // Dibujar sombra
        g2d.setColor(Color.BLACK); // Color de la sombra
        g2d.drawString(getText(), getInsets().left + 4, getHeight() / 2 + 12); // Sombra ligeramente desplazada

        // Dibujar texto principal
        g2d.setColor(new Color(135, 206, 250)); // Azul claro (puedes personalizarlo)
        g2d.drawString(getText(), getInsets().left, getHeight() / 2 + 10);
    }
};// Agregar un borde para evitar el recorte
tituloLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        usuarioLabel.setForeground(Color.BLACK);

        JLabel contrasenaLabel = new JLabel("Contraseña:");
        contrasenaLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        contrasenaLabel.setForeground(Color.BLACK);

        usuarioField = new JTextField(15);
        contrasenaField = new JPasswordField(15);

        JButton loginButton = new JButton("Ingresar");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Configurar layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Agregar componentes al panel principal
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

        // Agregar acción al botón
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
            String query = "SELECT rol_id FROM usuarios WHERE usuario = ? AND contrasena = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int rolId = rs.getInt("rol_id");
                switch (rolId) {
                    case 1: // Admin
                        JOptionPane.showMessageDialog(this, "Ingreso como Admin");
                        new AdminUI().setVisible(true);
                        break;
                    case 2: // Docente
                        JOptionPane.showMessageDialog(this, "Ingreso como Docente");
                        break;
                    case 3: // Estudiante
                        JOptionPane.showMessageDialog(this, "Ingreso como Estudiante");
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
        }
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
                g2d.dispose();

                // Aplicar desenfoque
                imagenFondo = aplicarDesenfoque(bufferedImage);
            } catch (Exception e) {
                System.err.println("No se pudo cargar o procesar la imagen de fondo: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenFondo != null) {
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        }

       private BufferedImage aplicarDesenfoque(BufferedImage image) {
    float[] kernelData = {
        1 / 256f, 4 / 256f, 6 / 256f, 4 / 256f, 1 / 256f,
        4 / 256f, 16 / 256f, 24 / 256f, 16 / 256f, 4 / 256f,
        6 / 256f, 24 / 256f, 36 / 256f, 24 / 256f, 6 / 256f,
        4 / 256f, 16 / 256f, 24 / 256f, 16 / 256f, 4 / 256f,
        1 / 256f, 4 / 256f, 6 / 256f, 4 / 256f, 1 / 256f
    };
    Kernel kernel = new Kernel(5, 5, kernelData);
    ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    return convolveOp.filter(image, null);
}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
