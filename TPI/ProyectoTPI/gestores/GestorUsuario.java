package ProyectoTPI.gestores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ProyectoTPI.recursos.Rutas;
import ProyectoTPI.user.Usuario;

public class GestorUsuario {
    public GestorUsuario() {
        cargarUsuarios();
    }

    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Rutas.RUTA_USUARIOS))) {
            String linea;
            if (br.ready()) { //emitir primer linea
                br.readLine(); 
            }
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
