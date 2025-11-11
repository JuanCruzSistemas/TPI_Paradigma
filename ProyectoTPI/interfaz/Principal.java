package ProyectoTPI.interfaz;

import ProyectoTPI.gestores.GestorCuenta;
import ProyectoTPI.dominio.Cuenta;
import ProyectoTPI.dominio.RolPersonaUTN;

import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {
    private GestorCuenta gestor;

    public Principal() {
        gestor = new GestorCuenta();

        setTitle("Gestor de Cuentas - ProyectoTPI");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnRegistrarCuenta = new JButton("Registrar Cuenta");
        JButton btnRegistrarVehiculo = new JButton("Registrar Vehículo");
        JButton btnConsultar = new JButton("Consultar");

        btnRegistrarCuenta.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnRegistrarVehiculo.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnConsultar.setFont(new Font("SansSerif", Font.BOLD, 18));

        btnRegistrarCuenta.addActionListener(e -> registrarCuentaDemo());
        btnRegistrarVehiculo.addActionListener(e -> JOptionPane.showMessageDialog(this, "Formulario de registro de vehículo."));
        btnConsultar.addActionListener(e -> consultarCuentas());

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 30, 30));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 400, 100, 400));
        panelBotones.add(btnRegistrarCuenta);
        panelBotones.add(btnRegistrarVehiculo);
        panelBotones.add(btnConsultar);

        add(panelBotones, BorderLayout.CENTER);
        setVisible(true);
    }

    private void registrarCuentaDemo() {
        gestor.agregarCuenta(new CuentaDemo(new RolPersonaUTN("Empleado UTN", 0.1)));
        JOptionPane.showMessageDialog(this, "Cuenta registrada exitosamente.");
    }

    private void consultarCuentas() {
        if (!gestor.tieneCuentas()) {
            JOptionPane.showMessageDialog(this, "No hay cuentas registradas.");
        } else {
            StringBuilder sb = new StringBuilder("Cuentas registradas:\n");
            for (Cuenta c : gestor.getCuentas()) {
                sb.append("- ").append(c.getRolPersonaUtn().getNombreRol())
                  .append(" | Estado: ").append(c.getEstadoCuenta().getValorEstadoCuenta())
                  .append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }

    private static class CuentaDemo extends Cuenta {
        public CuentaDemo(RolPersonaUTN rol) {
            super(rol);
        }
    }
}
