package ProyectoTPI.datos;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import ProyectoTPI.recursos.Rutas;
import ProyectoTPI.user.Usuario;

public class FileUsuario extends FileManager {
    public FileUsuario() {
        super(Rutas.RUTA_USUARIOS);
    }

    public List<Usuario> leerUsuarios() {
        List<String> lineas = leerArchivo();
        Function<String, Usuario> armarUsuario = s -> {
            String[] info = s.split(";");
            String legajo = info[0];
            String nombre = info[1];
            String apellido = info[2];
            String clave = info[3];
            boolean esHash = true;
            /*
             * El esHash sirve para que, como se lee del archivo, se guarde directamente la clave del archivo,
             * en vez de hacerle el hash (como sí se hace con el constructor normal de Usuario),
             * ya que sino, a la clave del archivo (ya hasheada) se le haría otra vez el hash, y se buguearía todo.
             */
        
            return new Usuario(legajo, nombre, apellido, clave, esHash);
        };
        List<Usuario> usuarios = lineas.stream()
                                       .map(armarUsuario)
                                       .collect(Collectors.toList());
        return usuarios;
    }
}