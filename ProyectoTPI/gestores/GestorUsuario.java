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
    
    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = archivoUsuarios.leerUsuarios();
        if (usuarios.isEmpty()) {
            MensajesArchivos.archivoVacio();
        }
        return usuarios;
    }

    public void registrarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        this.archivoUsuarios.agregarInformacion(true, usuario);
    }
}