package ProyectoTPI.interfaz;

import ProyectoTPI.dominio.Empleado;
import ProyectoTPI.recursos.Rutas;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class VentanaAdmin extends JFrame {
    private ArrayList<Empleado> empleados;

    public VentanaAdmin(ArrayList<Empleado> empleados) {
        this.empleados = empleados;

        setTitle("Panel del Administrador");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Panel del Administrador");
        lblTitulo.setBounds(110, 20, 200, 25);
        add(lblTitulo);

        JButton btnAgregar = new JButton("Añadir Empleado");
        btnAgregar.setBounds(120, 80, 150, 40);
        add(btnAgregar);

        btnAgregar.addActionListener(e -> agregarEmpleado());

        setVisible(true);
    }

    private void agregarEmpleado() {
        String legajo = JOptionPane.showInputDialog("Legajo:");
        String nombre = JOptionPane.showInputDialog("Nombre:");
        String apellido = JOptionPane.showInputDialog("Apellido:");
        String contrasena = JOptionPane.showInputDialog("Contraseña:");

        if (legajo == null || nombre == null || apellido == null || contrasena == null ||
            legajo.isBlank() || nombre.isBlank() || apellido.isBlank() || contrasena.isBlank()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        Empleado nuevo = new Empleado(legajo.trim(), nombre.trim(), apellido.trim(), contrasena.trim());
        empleados.add(nuevo);

        guardarEmpleadoArchivo(nuevo);
        JOptionPane.showMessageDialog(this, "Empleado añadido y guardado correctamente.");
    }

    private void guardarEmpleadoArchivo(Empleado empleado) {
        try (FileWriter fw = new FileWriter(Rutas.RUTA_EMPLEADOS, true)) {
            fw.write(
                empleado.getLegajo() + " | " +
                empleado.getNombreCompleto().split(" ")[0] + " | " +
                empleado.getNombreCompleto().split(" ")[1] + " | " +
                empleado.getHashClave() + "\n"
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar en el archivo: " + e.getMessage());
        }
    }
}
