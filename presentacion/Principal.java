package ProyectoTPI.presentacion;

import ProyectoTPI.gestores.GestorCuenta;
import ProyectoTPI.dominio.Cuenta;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.util.function.Consumer;

public class Principal extends JFrame {
    private GestorCuenta gestorCuenta;

    public Principal() {
        this.gestorCuenta = new GestorCuenta();

        setTitle("Gestor de Cuentas - ProyectoTPI");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        setVisible(true);
    }

    public void inicializarComponentes() {
        JButton btnRegistrarCuenta = new JButton("Registrar Cuenta");
        JButton btnRegistrarVehiculo = new JButton("Registrar Vehículo");
        JButton btnConsultar = new JButton("Consultar");

        btnRegistrarCuenta.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnRegistrarVehiculo.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnConsultar.setFont(new Font("SansSerif", Font.BOLD, 18));

        btnRegistrarCuenta.addActionListener(e -> registrarCuenta());
        btnRegistrarVehiculo.addActionListener(e -> registrarVehiculo());
        btnConsultar.addActionListener(e -> consultarCuentas());

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 30, 30));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 400, 100, 400));
        panelBotones.add(btnRegistrarCuenta);
        panelBotones.add(btnRegistrarVehiculo);
        panelBotones.add(btnConsultar);

        add(panelBotones, BorderLayout.CENTER);
    }

    private void registrarCuenta() {
        /**
         * broooooooooooooooooooooooooooooooooooo
         * oooooooooooooooooooooooooooooooooooooooo
         * oooooooooooooooooooooooooooooooooooooooooo
         * 
         * acá todo chiche de GUI, capaz otra pantalla para esto, estilo la del prototipo de ASI.
         * calculo yo que con el gestorCuentas basta, o sea gestorCuentas.registrarCuenta(cuenta).
         * 
         */
    }

    private void consultarCuentas() {
        /**
         * mejorar GUI porque no se si se buguee al mostrar tanta info solo en un mensaje en ventana, mejor hagamos un jframe aparte no? qsy
         */
        if (!gestorCuenta.tieneCuentas()) {
            JOptionPane.showMessageDialog(this, "No hay cuentas registradas.");
        } else {
            StringBuilder sb = new StringBuilder("Cuentas registradas:\n");
            Consumer<Cuenta> armarMensajeCuenta = c -> sb.append("- ")
                                                    .append(c.getRolPersonaUTN().getNombreRol())
                                                    .append(" | Estado: ")
                                                    .append(c.getEstadoCuenta())
                                                    .append("\n");
            
            gestorCuenta.getCuentas().forEach(armarMensajeCuenta);
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }

    private void registrarVehiculo() {
        JOptionPane.showMessageDialog(this, "Formulario de registro de vehículo.");
        /**
         * broooooooooooooooooooooooooooooooooooo
         * oooooooooooooooooooooooooooooooooooooooo
         * oooooooooooooooooooooooooooooooooooooooooo
         * 
         * acá todo chiche de GUI, capaz otra pantalla para esto, estilo la del prototipo de ASI.
         * 
         * Va podría ser directo con el GestorCuenta, porque el gestionar la cuenta abarca registrar y obtener sus vehiculos,
         * así que god. No borro lo de arriba porque qsy porque.
         * 
         */
    }
}