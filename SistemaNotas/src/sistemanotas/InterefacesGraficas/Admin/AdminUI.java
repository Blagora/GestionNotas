package sistemanotas.InterefacesGraficas.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sistemanotas.Estructura.AdminService;
import sistemanotas.InterefacesGraficas.Login;

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
        JButton gestionarDocentesButton = new JButton("Gestionar Docentes");
        JButton agregarDocenteButton = new JButton("Agregar Nuevo Docente");
        JButton salirButton = new JButton("Salir");

        gestionarEstudiantesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirValidarEstudiantes();
            }
        });

        agregarEstudianteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirAgregarEstudiante();
            }
        });
        gestionarDocentesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirValidarDocentes();
            }
        });
        agregarDocenteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirAgregarDocente();
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
        panel.add(gestionarDocentesButton);
        panel.add(agregarDocenteButton);
        panel.add(salirButton);
        add(panel);
    }

    private void abrirValidarEstudiantes() {
        new ValidarEstudianteUI().setVisible(true);
    }

    private void abrirAgregarEstudiante() {
        new AgregarEstudianteUI().setVisible(true);
    }
    private void abrirValidarDocentes() {
        new ValidarDocenteUI().setVisible(true);
    }

    private void abrirAgregarDocente() {
        new AgregarDocenteUI().setVisible(true);
    }
    
}


