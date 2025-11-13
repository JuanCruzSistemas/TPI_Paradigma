package ProyectoTPI.presentacion;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ProyectoTPI.user.Usuario;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.util.List;

public class Login extends JFrame {
    private JTextField txtLegajo;
    private JPasswordField txtClave;
    private JButton btnIngresar;
    private List<Usuario> usuarios;

    public Login(List<Usuario> usuarios) {
        this.usuarios = usuarios;

        setTitle("Login Usuario");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblUsuario = new JLabel("Legajo:");
        lblUsuario.setBounds(40, 40, 80, 25);
        add(lblUsuario);

        txtLegajo = new JTextField();
        txtLegajo.setBounds(120, 40, 200, 25);
        add(txtLegajo);

        JLabel lblClave = new JLabel("Contraseña:");
        lblClave.setBounds(40, 80, 80, 25);
        add(lblClave);

        txtClave = new JPasswordField();
        txtClave.setBounds(120, 80, 200, 25);
        add(txtClave);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(120, 130, 100, 30);
        add(btnIngresar);

        btnIngresar.addActionListener(e -> validarLogin());

        setVisible(true);
    }

    private void validarLogin() {
        String legajo = txtLegajo.getText().trim();
        String clave = new String(txtClave.getPassword());
        if (legajo.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }
        if (legajo.equalsIgnoreCase("admin")) {
            new VentanaAdmin(this.usuarios);
            return;
        }
        for (Usuario e : usuarios) {
            if (e.getLegajo().equalsIgnoreCase(legajo) && e.validarClave(clave)) {
                JOptionPane.showMessageDialog(this, "Bienvenido, " + e.getNombreCompleto());
                dispose();
                new Principal();
                return;
            } else if (e.getLegajo().equalsIgnoreCase(legajo)) {
                JOptionPane.showMessageDialog(this, "Legajo o contraseña incorrectos.");
                return;
            } else {
                JOptionPane.showMessageDialog(this, "No existe un usuario con ese legajo.");
                return;
            }
        }

       
    }
}
