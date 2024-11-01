package sistemanotas.InterefacesGraficas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GestionEstudianteUI extends JFrame {

    public GestionEstudianteUI() {
        setTitle("Gestión de Estudiantes");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton buscarEstudianteButton = new JButton("Buscar Estudiante");
        JButton editarEstudianteButton = new JButton("Editar Estudiante");
        JButton eliminarEstudianteButton = new JButton("Eliminar Estudiante");
        JButton asignarCursoButton = new JButton("Asignar Curso");
        JButton eliminarCursoButton = new JButton("Eliminar Curso");

        panel.add(buscarEstudianteButton);
        panel.add(editarEstudianteButton);
        panel.add(eliminarEstudianteButton);
        panel.add(asignarCursoButton);
        panel.add(eliminarCursoButton);
        add(panel);
    }
}

