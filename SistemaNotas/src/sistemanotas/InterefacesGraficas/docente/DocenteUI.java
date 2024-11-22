package sistemanotas.InterefacesGraficas.docente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sistemanotas.InterefacesGraficas.Login;

public class DocenteUI extends JFrame {
    private String docenteId;

    public DocenteUI(String docenteId) {
        this.docenteId = docenteId;
        setTitle("Panel de Docente");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel con fondo personalizado
        JPanel panel = new FondoPanel();
        panel.setLayout(new BorderLayout(10, 10)); // Mejor control sobre el layout
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado alrededor del panel

        // Crear botones con un método de configuración
        JButton gestionarCortesButton = crearBoton("Gestionar Cortes", e -> gestionarCortes());
        JButton gestionarGruposButton = crearBoton("Gestionar Grupos de Notas", e -> gestionarGrupos());
        JButton asignarNotasButton = crearBoton("Asignar Notas", e -> asignarNotas());
        JButton salirButton = crearBoton("Salir", e -> salir());

        // Panel de botones
        JPanel botonesPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        botonesPanel.add(gestionarCortesButton);
        botonesPanel.add(gestionarGruposButton);
        botonesPanel.add(asignarNotasButton);
        botonesPanel.add(salirButton);

        panel.add(botonesPanel, BorderLayout.CENTER); // Añadir panel de botones en el centro

        add(panel); // Añadir el panel principal al JFrame

        // Establecer el diseño visual del JFrame
        setIconImage(new ImageIcon(getClass().getResource("/proyecto_images/uan_login.png")).getImage()); // Icono de la ventana
    }

    // Método para crear los botones con estilo común
    private JButton crearBoton(String texto, ActionListener listener) {
        JButton button = new JButton(texto);
        button.setBackground(Color.decode("#007BFF"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(listener);
        return button;
    }

    private void gestionarCortes() {
        // Mostrar ventana para gestionar cortes del curso
        new GestionCortesUI(docenteId).setVisible(true);
    }

    private void gestionarGrupos() {
        // Mostrar ventana para gestionar grupos dentro de cortes
        new GestionGruposUI(docenteId).setVisible(true);
    }

    private void asignarNotas() {
        // Mostrar ventana para asignar notas
        new AsignarNotasUI(docenteId).setVisible(true);
    }

    private void salir() {
        // Cerrar la sesión y mostrar la ventana de login
        dispose();
        new Login().setVisible(true);
    }

    // Clase interna para el panel con fondo personalizado
    class FondoPanel extends JPanel {
        private Image imagen;

        public FondoPanel() {
            // Cargar la imagen de fondo desde la carpeta src/proyecto_images
            imagen = new ImageIcon(getClass().getResource("/proyecto_images/fondo_docente.jpg")).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                // Dibuja la imagen en todo el panel
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}

