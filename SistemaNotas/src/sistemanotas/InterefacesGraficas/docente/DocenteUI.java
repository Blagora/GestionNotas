package sistemanotas.InterefacesGraficas.docente;

import javax.swing.*;
import sistemanotas.Estructura.Docente;

public class DocenteUI extends JFrame {

    public DocenteUI(Docente docente) {
        setTitle("Panel de Docente - " + docente.getNombre());
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton verCursosButton = new JButton("Ver Cursos Asignados");
        JButton gestionarNotasButton = new JButton("Gestionar Notas");

        verCursosButton.addActionListener(e -> verCursos(docente));
        gestionarNotasButton.addActionListener(e -> gestionarNotas(docente));

        panel.add(verCursosButton);
        panel.add(gestionarNotasButton);
        add(panel);
    }

    private void verCursos(Docente docente) {
        JOptionPane.showMessageDialog(this, "Cursos Asignados para " + docente.getNombre());
    }

    private void gestionarNotas(Docente docente) {
        JOptionPane.showMessageDialog(this, "Gestionar Notas para " + docente.getNombre());
    }
}

