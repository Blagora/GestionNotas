package sistemanotas.InterefacesGraficas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sistemanotas.Estructura.AdminService;
public class AdminUI extends JFrame {

    private AdminService adminService;

    public AdminUI() {
        adminService = new AdminService();
        
        setTitle("Panel de Administración - Admin");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton gestionarEstudiantesButton = new JButton("Gestionar Estudiantes");
        JButton agregarEstudianteButton = new JButton("Agregar Nuevo Estudiante");
        JButton salirButton = new JButton("Salir");

        gestionarEstudiantesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirGestionEstudiantes();
            }
        });

        agregarEstudianteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirAgregarEstudiante();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login().setVisible(true);
            }
        });

        panel.add(gestionarEstudiantesButton);
        panel.add(agregarEstudianteButton);
        panel.add(salirButton);
        add(panel);
    }

    private void abrirGestionEstudiantes() {
        new GestionEstudianteUI().setVisible(true);
    }

    private void abrirAgregarEstudiante() {
        new AgregarEstudianteUI().setVisible(true);
    }
    
    
}


