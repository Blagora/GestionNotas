package sistemanotas.InterefacesGraficas.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import sistemanotas.Estructura.AdminService;

public class AgregarDocenteUI extends JFrame {
    
    private JTextField codigoField;
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField correoField;
    private JTextField areaField;
    private JButton agregarButton;
    private JButton cancelarButton;
    private AdminService adminService;

    public AgregarDocenteUI() {
        adminService = new AdminService();

        // Configurar ventana principal
        setTitle("Agregar Docente");
        setSize(450, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 248, 255)); // Fondo azul claro
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de entrada
        JLabel titleLabel = new JLabel("Agregar Nuevo Docente");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(new Color(0, 51, 102)); // Azul oscuro
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; // Reiniciar anchura

        // Código
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Código:"), gbc);
        codigoField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(codigoField, gbc);

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Nombre:"), gbc);
        nombreField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(nombreField, gbc);

        // Apellido
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Apellido:"), gbc);
        apellidoField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(apellidoField, gbc);

        // Correo
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Correo:"), gbc);
        correoField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(correoField, gbc);

        // Área
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Área:"), gbc);
        areaField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(areaField, gbc);

        // Botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        agregarButton = new JButton("Agregar");
        agregarButton.setBackground(new Color(0, 153, 76)); // Verde
        agregarButton.setForeground(Color.WHITE);
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBackground(new Color(204, 0, 0)); // Rojo
        cancelarButton.setForeground(Color.WHITE);
        buttonPanel.add(agregarButton);
        buttonPanel.add(cancelarButton);

        // Agregar acciones a los botones
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDocente();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Añadir paneles a la ventana
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void agregarDocente() {
        String codigo = codigoField.getText().trim();
        String nombre = nombreField.getText().trim();
        String apellido = apellidoField.getText().trim();
        String correo = correoField.getText().trim();
        String area = areaField.getText().trim();

        // Validar campos
        if (codigo.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || area.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Llamar al método de agregar estudiante
        boolean agregado = adminService.agregarDocente(codigo, nombre, apellido, correo, area);

        if (agregado) {
            JOptionPane.showMessageDialog(this, "Docente agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar docente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
