package sistemanotas.InterefacesGraficas.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import sistemanotas.Estructura.AdminService;

public class CrearCursoUI extends JFrame {

    private JTextField codigoField;
    private JTextField nombreField;
    private JButton crearButton;
    private AdminService adminService;

    public CrearCursoUI(AdminService adminService) {
        this.adminService = adminService;

        // Configuración básica de la ventana
        setTitle("Crear Curso");
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal con fondo azul claro
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Configurar etiquetas
        JLabel codigoLabel = new JLabel("Código:");
        codigoLabel.setForeground(new Color(0, 102, 204)); // Azul fuerte
        codigoLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setForeground(new Color(0, 102, 204)); // Azul fuerte
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Crear campos de texto
        codigoField = new JTextField(20);
        nombreField = new JTextField(20);

        // Crear botón
        crearButton = new JButton("Crear Curso");
        crearButton.setBackground(new Color(0, 153, 76)); // Verde
        crearButton.setForeground(Color.WHITE);
        crearButton.setFont(new Font("Arial", Font.BOLD, 14));
        crearButton.setFocusPainted(false);

        // Posicionar componentes en el panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(codigoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(codigoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nombreLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Botón ocupa dos columnas
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(crearButton, gbc);

        // Agregar acción al botón
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCurso();
            }
        });

        // Agregar panel al frame
        add(panel);
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
