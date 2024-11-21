package sistemanotas.InterefacesGraficas.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sistemanotas.Estructura.AdminService;

public class CrearCursoUI extends JFrame {
    
    private JTextField codigoField;
    private JTextField nombreField;
    private JButton crearButton;
    private JButton cancelarButton;
    private AdminService adminService;
    
    public CrearCursoUI(AdminService adminService) {
        this.adminService = adminService;

        setTitle("Crear Curso");
        setSize(450, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con fondo personalizado
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        
        // Título estilizado
        JLabel titleLabel = new JLabel("Crear Nuevo Curso");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(34, 45, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre elementos

        // Campos de texto con etiquetas
        mainPanel.add(createLabeledField("Código del Curso:", codigoField = new JTextField(15)));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre elementos
        mainPanel.add(createLabeledField("Nombre del Curso:", nombreField = new JTextField(15)));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio antes de los botones

        // Panel para botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));

        // Botón "Crear Curso"
        crearButton = createStyledButton("Crear Curso", new Color(34, 139, 34)); // Verde
        buttonPanel.add(crearButton);

        // Botón "Cancelar"
        cancelarButton = createStyledButton("Cancelar", new Color(220, 20, 60)); // Rojo
        buttonPanel.add(cancelarButton);

        mainPanel.add(buttonPanel);

        // Acciones de los botones
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCurso();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana
            }
        });

        add(mainPanel);
    }

    // Método para crear campos con etiquetas
    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(0, 51, 102));
        panel.add(label, BorderLayout.WEST);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        textField.setPreferredSize(new Dimension(250, 25));
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    // Método para estilizar botones
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return button;
    }

    private void crearCurso() {
        String codigo = codigoField.getText().trim();
        String nombre = nombreField.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean cursoCreado = adminService.crearCurso(codigo, nombre);
        if (cursoCreado) {
            JOptionPane.showMessageDialog(this, "Curso creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cierra la ventana tras crear el curso
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear el curso. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
