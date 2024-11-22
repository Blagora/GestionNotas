package sistemanotas.InterefacesGraficas.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sistemanotas.Estructura.AdminService;

public class AgregarEstudianteUI extends JFrame {

    private JTextField codigoField;
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField correoField;
    private JButton agregarButton;
    private JButton cancelarButton;
    private AdminService adminService;

    public AgregarEstudianteUI() {
        adminService = new AdminService();

        // Configuración básica de la ventana
        setTitle("Agregar Estudiante");
        setSize(400, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        codigoField = new JTextField(20);
        nombreField = new JTextField(20);
        apellidoField = new JTextField(20);
        correoField = new JTextField(20);
        agregarButton = new JButton("Agregar Estudiante");
        cancelarButton = new JButton("Cancelar");

        // Crear panel principal con fondo azul
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(173, 216, 230)); // Azul claro
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta y campo para "Código"
        JLabel codigoLabel = new JLabel("Código:");
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(codigoLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(codigoField, gbc);

        // Etiqueta y campo para "Nombre"
        JLabel nombreLabel = new JLabel("Nombre:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(nombreLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(nombreField, gbc);

        // Etiqueta y campo para "Apellido"
        JLabel apellidoLabel = new JLabel("Apellido:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(apellidoLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(apellidoField, gbc);

        // Etiqueta y campo para "Correo"
        JLabel correoLabel = new JLabel("Correo:");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(correoLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(correoField, gbc);

        // Botón "Agregar Estudiante"
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa dos columnas
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(agregarButton, gbc);

        // Botón "Cancelar"
        gbc.gridy = 5;
        panel.add(cancelarButton, gbc);

        // Agregar acción al botón "Agregar Estudiante"
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEstudiante();
            }
        });

        // Agregar acción al botón "Cancelar"
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana
            }
        });

        // Agregar el panel al frame
        add(panel);
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
