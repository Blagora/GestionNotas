
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

public class ValidarDocenteUI extends JFrame {

    private JTextField usernameField;
    private JButton validateButton;
    private JLabel resultLabel;

    // Constructor para configurar el JFrame
    public ValidarDocenteUI() {
        // Configuración de la ventana principal
        setTitle("Validación de Docentes");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar el panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título estilizado
        JLabel titleLabel = new JLabel("Validación de Docente");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 204)); // Azul fuerte
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 1, 10, 10));
        formPanel.setBackground(new Color(173, 216, 230));

        // Etiqueta y campo de entrada
        JLabel usernameLabel = new JLabel("Ingrese el código:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(0, 51, 102)); // Azul oscuro

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Botón de validación
        validateButton = crearBoton("Validar Usuario", new Color(0, 153, 76)); // Verde

        // Etiqueta de resultado
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 12));
        resultLabel.setForeground(new Color(255, 0, 0)); // Rojo para mensajes de error

        // Agregar componentes al panel de formulario
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(validateButton);

        // Agregar el formulario y la etiqueta de resultado al panel principal
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(resultLabel, BorderLayout.SOUTH);

        // Añadir el panel principal al JFrame
        add(mainPanel);

        // Evento para validar el usuario
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
            resultLabel.setText("Por favor, ingrese un código válido.");
            return;
        }

        String searchDocenteQuery = "SELECT * FROM docentes WHERE codigo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(searchDocenteQuery)) {

            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                new GestionDocenteUI(codigo).setVisible(true);
                dispose(); // Cierra la ventana actual
            } else {
                resultLabel.setText("Código inválido. Intente nuevamente.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos. Verifique su conexión.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para crear botones estilizados
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }
}
