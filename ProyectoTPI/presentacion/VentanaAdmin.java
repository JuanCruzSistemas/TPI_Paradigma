package ProyectoTPI.presentacion;

import ProyectoTPI.dominio.Usuario;
import ProyectoTPI.recursos.Rutas;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class VentanaAdmin extends JFrame {
    private List<Usuario> usuarios;

    public VentanaAdmin(List<Usuario> usuarios) {
        this.usuarios = usuarios;

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

    private void agregarUsuario() {
        String legajo = JOptionPane.showInputDialog("Legajo:");
        String nombre = JOptionPane.showInputDialog("Nombre:");
        String apellido = JOptionPane.showInputDialog("Apellido:");
        String contrasena = JOptionPane.showInputDialog("Contraseña:");

        if (legajo == null || nombre == null || apellido == null || contrasena == null ||
            legajo.isBlank() || nombre.isBlank() || apellido.isBlank() || contrasena.isBlank()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        Usuario nuevo = new Usuario(legajo.trim(), nombre.trim(), apellido.trim(), contrasena.trim());
        usuarios.add(nuevo);

        guardarUsuarioArchivo(nuevo);
        JOptionPane.showMessageDialog(this, "Usuario añadido y guardado correctamente.");
    }

    private void guardarUsuarioArchivo(Usuario usuario) {
        try (FileWriter fw = new FileWriter(Rutas.RUTA_USUARIOS, true)) {
            fw.write(
                usuario.getLegajo() + " | " +
                usuario.getNombreCompleto().split(" ")[0] + " | " +
                usuario.getNombreCompleto().split(" ")[1] + " | " +
                usuario.getHashClave() + "\n"
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar en el archivo: " + e.getMessage());
        }
    }
}
