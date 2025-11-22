package ProyectoTPI.gestores;

import ProyectoTPI.recursos.MensajesArchivos;
import ProyectoTPI.user.Usuario;
import ProyectoTPI.datos.FileUsuario;

import java.util.List;

public class GestorUsuario {
    private List<Usuario> usuarios;
    private FileUsuario archivoUsuarios;

    public GestorUsuario() {
        this.archivoUsuarios = new FileUsuario();
        this.usuarios = cargarUsuarios();
    }
    
    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void registrarUsuario(Usuario u) {
        this.usuarios.add(u);
        /*
         * Agregar lógica para, además, agregar el usuario 'u' al this.archivoUsuarios (que trabaja con usuarios.csv).
         * Creo que FileManager tiene para agregar info al archivo pero no se estoy gaga
         */
    }

    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = archivoUsuarios.leerUsuarios();
        if (usuarios.isEmpty()) {
            MensajesArchivos.archivoVacio();
        }
        return usuarios;
    }
}