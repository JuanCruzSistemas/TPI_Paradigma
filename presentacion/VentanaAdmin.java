package ProyectoTPI.presentacion;

import ProyectoTPI.gestores.GestorUsuario;
import ProyectoTPI.recursos.Mensajes;
import ProyectoTPI.user.Usuario;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class VentanaAdmin extends JFrame {
    private GestorUsuario gestorUsuario;

    public VentanaAdmin(GestorUsuario gestorUsuario) {
        this.gestorUsuario = gestorUsuario;

        setTitle("Panel del Administrador");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Panel del Administrador");
        lblTitulo.setBounds(110, 20, 200, 25);
        add(lblTitulo);

        JButton btnAgregar = new JButton("Añadir Usuario");
        btnAgregar.setBounds(120, 80, 150, 40);
        add(btnAgregar);

        btnAgregar.addActionListener(e -> agregarUsuario());

        setVisible(true);
    }

    public void agregarUsuario() {
        String legajo = JOptionPane.showInputDialog("Legajo:");
        String nombre = JOptionPane.showInputDialog("Nombre:");
        String apellido = JOptionPane.showInputDialog("Apellido:");
        String contrasena = JOptionPane.showInputDialog("Contraseña:");

        if (legajo == null || nombre == null || apellido == null || contrasena == null ||
            legajo.isBlank() || nombre.isBlank() || apellido.isBlank() || contrasena.isBlank()) {
            Mensajes.mensajeCompletarCampos();
            return;
        }

        Usuario nuevo = new Usuario(legajo.trim(), nombre.trim(), apellido.trim(), contrasena.trim());
        guardarUsuarioArchivo(nuevo);
        Mensajes.mostrarInfo("Usuario añadido y guardado correctamente.");
    }

    public void guardarUsuarioArchivo(Usuario usuario) {
        this.gestorUsuario.registrarUsuario(usuario);
    }
}