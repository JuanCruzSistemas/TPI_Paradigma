package ProyectoTPI.interfaz;

import ProyectoTPI.dominio.Empleado;
import javax.swing.*;
import java.util.ArrayList;

public class Login extends JFrame {
    private JTextField txtLegajo;
    private JPasswordField txtClave;
    private JButton btnIngresar;
    private ArrayList<Empleado> empleados;

    public Login(ArrayList<Empleado> empleados) {
        this.empleados = empleados;

        setTitle("Login Empleado");
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

        for (Empleado e : empleados) {
            if (e.getLegajo().equalsIgnoreCase(legajo) && e.validarClave(clave)) {
                JOptionPane.showMessageDialog(this, "Bienvenido, " + e.getNombreCompleto());
                dispose();
                if (legajo.equalsIgnoreCase("admin")) {
                    new VentanaAdmin(this.empleados);
                } else {
                    new Principal();
                }
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Legajo o contraseña incorrectos.");
    }
}
