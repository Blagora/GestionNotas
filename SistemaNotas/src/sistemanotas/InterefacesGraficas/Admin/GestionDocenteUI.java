package sistemanotas.InterefacesGraficas.Admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sistemanotas.Estructura.AdminService;


public class GestionDocenteUI extends JFrame{

    private String codigoDocente;
    private AdminService adminService;
    
    public GestionDocenteUI(String codigoDocente) {
        
        adminService = new AdminService();
        this.codigoDocente = codigoDocente;
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
        
        editarDocenteButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirEditarDocente();
            }
            
        });
        
        eliminarDocenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, 
                    "¿Está seguro de que desea eliminar este docente?", 
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean eliminado = adminService.eliminarDocente(codigoDocente);

                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Docente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar docente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    private void abrirEditarDocente() {
        new EditarDocenteUI(codigoDocente).setVisible(true);
    }
}
