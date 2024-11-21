package sistemanotas.InterefacesGraficas.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import sistemanotas.Estructura.AdminService;

public class AgregarEstudianteUI extends JFrame {

    private JTextField codigoField;
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField correoField;
    private JButton agregarButton;
    private JButton cancelarButton;
    private AdminService adminService;
    private BufferedImage backgroundImage;

    public AgregarEstudianteUI() {
        adminService = new AdminService();
        setTitle("Agregar Estudiante");
        setSize(600, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cargar la imagen de fondo
        try {
            backgroundImage = loadBackgroundImage("C:\\Users\\Admin\\Pictures\\proyecto_images\\estudiantes_fondo.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configurar el panel principal
        JPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Títulos y campos
        JLabel titleLabel = new JLabel("Agregar Estudiante");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 30));
        titleLabel.setForeground(new Color(15, 42, 236 )); // Ajusta el color para mayor contraste
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        codigoField = createStyledTextField();
        nombreField = createStyledTextField();
        apellidoField = createStyledTextField();
        correoField = createStyledTextField();

        agregarButton = createStyledButton("Agregar Estudiante", new Color(34, 139, 34)); // Verde
        cancelarButton = createStyledButton("Cancelar", new Color(220, 20, 60)); // Rojo

        // Crear contenedores para campos y etiquetas
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createLabeledField("Código:", codigoField));
        mainPanel.add(createLabeledField("Nombre:", nombreField));
        mainPanel.add(createLabeledField("Apellido:", apellidoField));
        mainPanel.add(createLabeledField("Correo:", correoField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.add(agregarButton);
        buttonPanel.add(cancelarButton);
        mainPanel.add(buttonPanel);

        // Agregar acción a los botones
        agregarButton.addActionListener(e -> agregarEstudiante());
        cancelarButton.addActionListener(e -> dispose());

        add(mainPanel);
    }

    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(15, 42, 236)); // Color ajustado
        label.setFont(new Font("Arial Black", Font.PLAIN, 30));
        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

  private JTextField createStyledTextField() {
    JTextField textField = new JTextField(15); // Ajustamos las columnas a 15
    textField.setFont(new Font("Arial ", Font.PLAIN, 30));
    textField.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
    textField.setPreferredSize(new Dimension(150, 25)); // Tamaño personalizado
    return textField;
}

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return button;
    }

    private BufferedImage loadBackgroundImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private void agregarEstudiante() {
        String codigo = codigoField.getText().trim();
        String nombre = nombreField.getText().trim();
        String apellido = apellidoField.getText().trim();
        String correo = correoField.getText().trim();

        // Validar campos
        if (codigo.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Llamar al método de agregar estudiante
        boolean agregado = adminService.agregarEstudiante(codigo, nombre, apellido, correo);

        if (agregado) {
            JOptionPane.showMessageDialog(this, "Estudiante agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cerrar la ventana después de agregar
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
