package sistemanotas.InterefacesGraficas.Admin;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import sistemanotas.Estructura.AdminService;
import sistemanotas.Estructura.Docente;

public class EditarDocenteUI extends JFrame {

    private JTextField codigoField;
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField correoField;
    private JTextField areaField;
    private JButton guardarButton;
    private JButton cancelarButton;
    private AdminService adminService;

    public EditarDocenteUI(String codigoDocente) {
        adminService = new AdminService();

        setTitle("Editar Docente");
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Establecer fondo azul
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(0, 102, 204)); // Fondo azul
        add(mainPanel);

        // Estilo común para los campos de texto
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        Border fieldBorder = BorderFactory.createLineBorder(new Color(0, 51, 153), 2);

        // Crear componentes estilizados
        codigoField = crearCampoTexto(fieldFont, fieldBorder, false);
        nombreField = crearCampoTexto(fieldFont, fieldBorder, true);
        apellidoField = crearCampoTexto(fieldFont, fieldBorder, true);
        correoField = crearCampoTexto(fieldFont, fieldBorder, true);
        areaField = crearCampoTexto(fieldFont, fieldBorder, true);

        guardarButton = crearBoton("Guardar Cambios", new Color(0, 153, 51), Color.WHITE);
        cancelarButton = crearBoton("Cancelar", new Color(204, 0, 0), Color.WHITE);

        // Crear etiquetas estilizadas
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        JLabel codigoLabel = crearEtiqueta("Código:", labelFont, Color.WHITE);
        JLabel nombreLabel = crearEtiqueta("Nombre:", labelFont, Color.WHITE);
        JLabel apellidoLabel = crearEtiqueta("Apellido:", labelFont, Color.WHITE);
        JLabel correoLabel = crearEtiqueta("Correo:", labelFont, Color.WHITE);
        JLabel areaLabel = crearEtiqueta("Área:", labelFont, Color.WHITE);

        // Posicionar componentes
        int labelX = 50, labelWidth = 100, labelHeight = 30;
        int fieldX = 160, fieldWidth = 220, fieldHeight = 30;
        int yOffset = 40;

        codigoLabel.setBounds(labelX, 30, labelWidth, labelHeight);
        codigoField.setBounds(fieldX, 30, fieldWidth, fieldHeight);

        nombreLabel.setBounds(labelX, 30 + yOffset * 2, labelWidth, labelHeight);
        nombreField.setBounds(fieldX, 30 + yOffset * 2, fieldWidth, fieldHeight);

        apellidoLabel.setBounds(labelX, 30 + yOffset * 4, labelWidth, labelHeight);
        apellidoField.setBounds(fieldX, 30 + yOffset * 4, fieldWidth, fieldHeight);

        correoLabel.setBounds(labelX, 30 + yOffset * 6, labelWidth, labelHeight);
        correoField.setBounds(fieldX, 30 + yOffset * 6, fieldWidth, fieldHeight);

        areaLabel.setBounds(labelX, 30 + yOffset * 8, labelWidth, labelHeight);
        areaField.setBounds(fieldX, 30 + yOffset * 8, fieldWidth, fieldHeight);

        guardarButton.setBounds(50, 30 + yOffset * 10, 150, 40);
        cancelarButton.setBounds(230, 30 + yOffset * 10, 150, 40);

        // Agregar componentes al panel
        mainPanel.add(codigoLabel);
        mainPanel.add(codigoField);
        mainPanel.add(nombreLabel);
        mainPanel.add(nombreField);
        mainPanel.add(apellidoLabel);
        mainPanel.add(apellidoField);
        mainPanel.add(correoLabel);
        mainPanel.add(correoField);
        mainPanel.add(areaLabel);
        mainPanel.add(areaField);
        mainPanel.add(guardarButton);
        mainPanel.add(cancelarButton);

        // Cargar datos del docente automáticamente al inicio
        cargarDocente(codigoDocente);

        // Agregar acción al botón guardar
        guardarButton.addActionListener(e -> guardarCambios());

        // Agregar acción al botón cancelar
        cancelarButton.addActionListener(e -> dispose()); // Cierra la ventana
    }

    private JTextField crearCampoTexto(Font font, Border border, boolean editable) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setBorder(border);
        textField.setEditable(editable);
        return textField;
    }

    private JButton crearBoton(String texto, Color background, Color foreground) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(background);
        boton.setForeground(foreground);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JLabel crearEtiqueta(String texto, Font font, Color color) {
        JLabel label = new JLabel(texto);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private void cargarDocente(String codigoDocente) {
        Docente docente = adminService.obtenerDocente(codigoDocente);

        if (docente != null) {
            codigoField.setText(docente.getCodigo());
            nombreField.setText(docente.getNombre());
            apellidoField.setText(docente.getApellido());
            correoField.setText(docente.getCorreo());
            areaField.setText(docente.getArea());
        } else {
            JOptionPane.showMessageDialog(this, "Docente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose(); // Cierra la ventana si no se encuentra el docente
        }
    }

    private void guardarCambios() {
        String codigo = codigoField.getText().trim();
        String nombre = nombreField.getText().trim();
        String apellido = apellidoField.getText().trim();
        String correo = correoField.getText().trim();
        String area = areaField.getText().trim();

        // Validar campos
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || area.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Llamar al método para actualizar el docente
        boolean actualizado = adminService.actualizarDocente(codigo, nombre, apellido, correo, area);

        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Docente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cerrar la ventana después de actualizar
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar docente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
