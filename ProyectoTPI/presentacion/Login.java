package ProyectoTPI.presentacion;

import ProyectoTPI.controladores.ControladorLogin;
import ProyectoTPI.gestores.GestorUsuario;
import ProyectoTPI.recursos.Mensajes;
import ProyectoTPI.user.Usuario;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

// import java.util.function.Predicate;

public class Login extends JFrame {
    private JTextField txtLegajo;
    private JPasswordField txtClave;
    private JButton btnIngresar;
    private ControladorLogin controladorLogin;

    public Login(GestorUsuario gestorUsuario) {
        this.controladorLogin = new ControladorLogin(gestorUsuario);

        setTitle("Login Usuario");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        inicializarComponentes();
        setVisible(true);
    }

    public void inicializarComponentes() {
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
    }

    private void validarLogin() {
        String legajo = txtLegajo.getText().trim();
        String clave = new String(txtClave.getPassword());

        if (legajo.isEmpty() || clave.isEmpty()) {
            Mensajes.mensajeCompletarCampos();
            return;
        }

        Usuario usuario = this.controladorLogin.validarUsuario(legajo, clave);

        if (usuario == null) {
            Mensajes.mensajeDatosIncorrectos();
            return;
        }

        Mensajes.mostrarInfo("Bienvenido, " + usuario.getNombreCompleto());
        dispose();

        if (this.controladorLogin.esAdmin(usuario)) {
            new VentanaAdmin(this.controladorLogin.getGestorUsuario());
            return;
        } else {
            new Principal();
        }
    }
}

// public class Login extends JFrame {
//     private JTextField txtLegajo;
//     private JPasswordField txtClave;
//     private JButton btnIngresar;
//     private GestorUsuario gestorUsuario;

//     public Login(GestorUsuario gestorUsuario) {
//         this.gestorUsuario = gestorUsuario;

//         setTitle("Login Usuario");
//         setSize(400, 250);
//         setDefaultCloseOperation(EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setResizable(false);
//         setLayout(null);

//         inicializarComponentes();
//         setVisible(true);
//     }

//     public void inicializarComponentes() {
//         JLabel lblUsuario = new JLabel("Legajo:");
//         lblUsuario.setBounds(40, 40, 80, 25);
//         add(lblUsuario);

//         txtLegajo = new JTextField();
//         txtLegajo.setBounds(120, 40, 200, 25);
//         add(txtLegajo);

//         JLabel lblClave = new JLabel("Contraseña:");
//         lblClave.setBounds(40, 80, 80, 25);
//         add(lblClave);

//         txtClave = new JPasswordField();
//         txtClave.setBounds(120, 80, 200, 25);
//         add(txtClave);

//         btnIngresar = new JButton("Ingresar");
//         btnIngresar.setBounds(120, 130, 100, 30);
//         add(btnIngresar);

//         btnIngresar.addActionListener(e -> validarLogin());
//     }

//     private void validarLogin() {
//         String legajo = txtLegajo.getText().trim();
//         String clave = new String(txtClave.getPassword());

//         if (legajo.isEmpty() || clave.isEmpty()) {
//             Mensajes.mensajeCompletarCampos();
//             return;
//         }
        
//         Predicate<Usuario> compararLegajo = u -> u.getLegajo().equalsIgnoreCase(legajo);
//         Predicate<Usuario> compararClave = u -> u.validarClave(clave);
//         Usuario usuario = this.gestorUsuario.getUsuarios().stream()
//                                                           .filter(compararLegajo.and(compararClave))
//                                                           .findFirst()
//                                                           .orElse(null);
//         if (usuario == null) {
//             Mensajes.mensajeDatosIncorrectos();
//             return;
//         }
//         Mensajes.mostrarInfo("Bienvenido, " + usuario.getNombreCompleto());

//         dispose();
//         if (legajo.charAt(0) == 'A') { // Veamos con qué condición se define a un admin, esta es de ejemplo.
//             new VentanaAdmin(this.gestorUsuario);
//             return;
//         } else {
//             new Principal();
//         }
//     }
// }