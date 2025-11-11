package ProyectoTPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ProyectoTPI.interfaz.Login;
import ProyectoTPI.dominio.Empleado;
import ProyectoTPI.recursos.Rutas;

public class Main {
    private ArrayList<Empleado> empleados;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.cargarDatos();
            new Login(app.empleados);
        });
    }

    private void cargarDatos() {
        empleados = cargarEmpleados();
        if (empleados.isEmpty()) {
            System.out.println("⚠️ No se cargaron empleados desde el archivo.");
        } else {
            System.out.println("✅ Empleados cargados correctamente: " + empleados.size());
        }
    }

    private ArrayList<Empleado> cargarEmpleados() {
        empleados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(Rutas.RUTA_EMPLEADOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length == 4) {
                    String legajo = datos[0].trim();
                    String nombre = datos[1].trim();
                    String apellido = datos[2].trim();
                    String clave = datos[3].trim();

                    empleados.add(new Empleado(legajo, nombre, apellido, clave));
                } else {
                    System.out.println("Línea inválida (omitida): " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ No se pudo leer el archivo de empleados: " + e.getMessage());
        }

        return empleados;
    }
}
