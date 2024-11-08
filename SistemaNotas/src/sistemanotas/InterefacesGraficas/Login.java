package sistemanotas.InterefacesGraficas;

import sistemanotas.InterefacesGraficas.Admin.AdminUI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import sistemanotas.ConexionBD.ConexionDB;

public class Login extends JFrame {
    private JTextField usuarioField;
    private JPasswordField contrasenaField;

    public Login() {
        setTitle("Login - Sistema de Notas");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        usuarioField = new JTextField(15);
        contrasenaField = new JPasswordField(15);
        JButton loginButton = new JButton("Ingresar");

        panel.add(new JLabel("Usuario:"));
        panel.add(usuarioField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(contrasenaField);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });

        add(panel);
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

}
