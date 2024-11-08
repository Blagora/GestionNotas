package sistemanotas.InterefacesGraficas.Estudiante;

import javax.swing.*;
import sistemanotas.Estructura.Estudiante;

public class EstudianteUI extends JFrame {

    public EstudianteUI(Estudiante estudiante) {
        setTitle("Panel de Estudiante - " + estudiante.getNombre());
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton verCursosButton = new JButton("Ver Cursos");
        JButton verNotasButton = new JButton("Ver Notas");

        verCursosButton.addActionListener(e -> verCursos(estudiante));
        verNotasButton.addActionListener(e -> verNotas(estudiante));

        panel.add(verCursosButton);
        panel.add(verNotasButton);
        add(panel);
    }

    private void verCursos(Estudiante estudiante) {
        JOptionPane.showMessageDialog(this, "Cursos del Estudiante: " + estudiante.getNombre());
    }

    private void verNotas(Estudiante estudiante) {
        JOptionPane.showMessageDialog(this, "Notas del Estudiante: " + estudiante.getNombre());
    }
}

