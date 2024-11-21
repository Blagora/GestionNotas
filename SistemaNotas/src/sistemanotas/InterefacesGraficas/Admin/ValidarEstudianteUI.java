package sistemanotas.InterefacesGraficas.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import sistemanotas.ConexionBD.ConexionDB;

public class ValidarEstudianteUI extends JFrame {
    private JTextField usernameField;
    private JButton validateButton;
    private JLabel resultLabel;

    // Constructor para configurar el JFrame
    public ValidarEstudianteUI() {
        setTitle("Validación de Estudiante");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar el panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Márgenes

        // Crear etiqueta de título
        JLabel titleLabel = new JLabel("Validar Estudiante");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear campo de entrada de usuario
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(173, 216, 230)); // Fondo igual al principal
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel userLabel = new JLabel("Código:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField = new JTextField(15);

        inputPanel.add(userLabel);
        inputPanel.add(usernameField);

        // Crear botón de validación
        validateButton = new JButton("Validar Usuario");
        validateButton.setFont(new Font("Arial", Font.BOLD, 14));
        validateButton.setBackground(new Color(0, 123, 255));
        validateButton.setForeground(Color.WHITE);
        validateButton.setFocusPainted(false);
        validateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear etiqueta de resultado
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Añadir componentes al panel principal
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre título y entrada
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre entrada y botón
        mainPanel.add(validateButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre botón y resultado
        mainPanel.add(resultLabel);

        // Añadir el panel principal al JFrame
        add(mainPanel);

        // Agregar evento al botón de validar
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateUser();
            }
        });
    }

    // Método para validar el usuario en la base de datos
    private void validateUser() {
        String codigo = usernameField.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String searchEstudianteQuery = "SELECT * FROM estudiantes WHERE codigo = ?";
        try (Connection conn = ConexionDB.getConnection()) {
            PreparedStatement stmtSearchUsuario = conn.prepareStatement(searchEstudianteQuery);
            stmtSearchUsuario.setString(1, codigo);
            ResultSet rs = stmtSearchUsuario.executeQuery();

            if (rs.next()) {
                new GestionEstudianteUI(codigo).setVisible(true);
                dispose(); // Cerrar la ventana actual
            } else {
                JOptionPane.showMessageDialog(this, "Código inválido. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
