package sistemanotas.InterefacesGraficas.Admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sistemanotas.Estructura.AdminService;

public class GestionEstudianteUI extends JFrame {
    
     private String codigoEstudiante;

    public GestionEstudianteUI(String codigoEstudiante) {
        
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
        eliminarEstudianteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, 
                    "¿Está seguro de que desea eliminar este estudiante?", 
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    AdminService adminService = new AdminService();
                    boolean eliminado = adminService.eliminarEstudiante(codigoEstudiante);

                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Estudiante eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

    }
    private void abrirEditarEstudiante() {
        new EditarEstudianteUI(codigoEstudiante).setVisible(true);
    }
}

