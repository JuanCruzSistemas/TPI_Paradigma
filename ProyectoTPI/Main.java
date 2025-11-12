package ProyectoTPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ProyectoTPI.dominio.Usuario;
import ProyectoTPI.presentacion.Login;
import ProyectoTPI.recursos.Rutas;

public class Main {
    private List<Usuario> usuarios;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.cargarDatos();
            new Login(app.usuarios);
        });
    }

    private void cargarDatos() {
        usuarios = cargarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("⚠️ No se cargaron usuarios desde el archivo.");
        } else {
            System.out.println("✅ usuarios cargados correctamente: " + usuarios.size());
        }
    }

    private List<Usuario> cargarUsuarios() {
        usuarios = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(Rutas.RUTA_USUARIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length == 4) {
                    String legajo = datos[0].trim();
                    String nombre = datos[1].trim();
                    String apellido = datos[2].trim();
                    String clave = datos[3].trim();

                    usuarios.add(new Usuario(legajo, nombre, apellido, clave));
                } else {
                    System.out.println("Línea inválida (omitida): " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ No se pudo leer el archivo de usuarios: " + e.getMessage());
        }

        return usuarios;
    }
}
