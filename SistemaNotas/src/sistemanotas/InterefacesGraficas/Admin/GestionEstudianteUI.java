package sistemanotas.InterefacesGraficas.Admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GestionEstudianteUI extends JFrame {
    
     private String codigoEstudiante;

    public GestionEstudianteUI(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
        
        setTitle("Gestión de Estudiantes");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton editarEstudianteButton = new JButton("Editar Estudiante");
        JButton eliminarEstudianteButton = new JButton("Eliminar Estudiante");
        JButton asignarCursoButton = new JButton("Asignar Curso");
        JButton eliminarCursoButton = new JButton("Eliminar Curso");

        panel.add(editarEstudianteButton);
        panel.add(eliminarEstudianteButton);
        panel.add(asignarCursoButton);
        panel.add(eliminarCursoButton);
        add(panel);
        
        editarEstudianteButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirEditarEstudiante();
            }
            
        });
        
    }
    private void abrirEditarEstudiante() {
        new EditarEstudianteUI(codigoEstudiante).setVisible(true);
    }
}

