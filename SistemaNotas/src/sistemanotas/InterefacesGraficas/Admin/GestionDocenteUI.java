package sistemanotas.InterefacesGraficas.Admin;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class GestionDocenteUI extends JFrame{

    public GestionDocenteUI() {
        setTitle("Gestión de Docentes");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton editarDocenteButton = new JButton("Editar Docente");
        JButton eliminarDocenteButton = new JButton("Eliminar Docente");
        JButton asignarCursoButton = new JButton("Asignar Curso");
        JButton eliminarCursoButton = new JButton("Eliminar Curso");

        panel.add(editarDocenteButton);
        panel.add(eliminarDocenteButton);
        panel.add(asignarCursoButton);
        panel.add(eliminarCursoButton);
        add(panel);
    }
    
}
