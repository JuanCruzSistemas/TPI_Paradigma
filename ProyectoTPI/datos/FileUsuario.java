package ProyectoTPI.datos;

import java.util.List;
import java.util.stream.Collectors;

import ProyectoTPI.recursos.Rutas;
import ProyectoTPI.user.Usuario;

public class FileUsuario extends FileManager {
    public FileUsuario() {
        super(Rutas.RUTA_USUARIOS);
    }

    public List<Usuario> leerUsuarios() {
        List<String> lineas = leerArchivo();
        List<Usuario> usuarios = lineas.stream()
                                       .map(this::armarUsuario)
                                       .collect(Collectors.toList());
        return usuarios;
    }

    public Usuario armarUsuario(String linea) {
        String[] info = linea.split(";");
        String legajo = info[0];
        String nombre = info[1];
        String apellido = info[2];
        String clave = info[3];
        return new Usuario(legajo, nombre, apellido, clave);
    }
}