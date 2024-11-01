package sistemanotas.InterefacesGraficas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.*;

public class AgregarEstudianteUI extends JFrame {

    public AgregarEstudianteUI() {
        setTitle("Agregar Nuevo Estudiante");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField codigoField = new JTextField(20);
        JTextField nombreField = new JTextField(20);
        JTextField apellidoField = new JTextField(20);
        JTextField correoField = new JTextField(20);
        JTextField fechaNacimientoField = new JTextField(20);

        JButton agregarButton = new JButton("Agregar");

        agregarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Código para agregar al estudiante en la base de datos
            }
        });

        panel.add(new JLabel("Código:"));
        panel.add(codigoField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Apellido:"));
        panel.add(apellidoField);
        panel.add(new JLabel("Correo:"));
        panel.add(correoField);
        panel.add(new JLabel("Fecha de Nacimiento:"));
        panel.add(fechaNacimientoField);
        panel.add(agregarButton);

        add(panel);
    }
}

