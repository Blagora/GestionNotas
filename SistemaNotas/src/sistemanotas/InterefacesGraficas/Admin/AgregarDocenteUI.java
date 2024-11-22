package sistemanotas.InterefacesGraficas.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
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

        // Configuración básica de la ventana
        setTitle("Agregar Docente");
        setSize(400, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        codigoField = new JTextField(20);
        nombreField = new JTextField(20);
        apellidoField = new JTextField(20);
        correoField = new JTextField(20);
        areaField = new JTextField(20);
        agregarButton = new JButton("Agregar Docente");
        cancelarButton = new JButton("Cancelar");

        // Crear panel principal con fondo azul
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(173, 216, 230)); // Color azul claro
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Agregar componentes al panel con posiciones específicas
        JLabel codigoLabel = new JLabel("Código:");
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(codigoLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(codigoField, gbc);

        JLabel nombreLabel = new JLabel("Nombre:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(nombreLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(nombreField, gbc);

        JLabel apellidoLabel = new JLabel("Apellido:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(apellidoLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(apellidoField, gbc);

        JLabel correoLabel = new JLabel("Correo:");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(correoLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(correoField, gbc);

        JLabel areaLabel = new JLabel("Área:");
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(areaLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(areaField, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        panel.add(agregarButton, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        panel.add(cancelarButton, gbc);

        // Acciones de los botones
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDocente();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana
            }
        });

        // Añadir el panel a la ventana
        add(panel);
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

        // Llamar al método de agregar docente
        boolean agregado = adminService.agregarDocente(codigo, nombre, apellido, correo, area);

        if (agregado) {
            JOptionPane.showMessageDialog(this, "Docente agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cerrar la ventana después de agregar
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar docente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
