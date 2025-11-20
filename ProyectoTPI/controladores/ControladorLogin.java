package ProyectoTPI.controladores;

import ProyectoTPI.gestores.GestorUsuario;
import ProyectoTPI.user.Usuario;

import java.util.function.Predicate;

public class ControladorLogin {
    // pongo un poco de código a ver si les parece, sino lo dejamos con los calculos directamente en Login.java. Onda esto es
    // para seguir con la lógica Modelo-Vista-Controlador
    private GestorUsuario gestorUsuario;

    public ControladorLogin(GestorUsuario gestorUsuario) {
        this.gestorUsuario = gestorUsuario;
    }
    
    public GestorUsuario getGestorUsuario() {
        return this.gestorUsuario;
    }

    public Usuario validarUsuario(String legajo, String clave) {
        Predicate<Usuario> compararLegajo = u -> u.getLegajo().equalsIgnoreCase(legajo);
        Predicate<Usuario> compararClave = u -> u.validarClave(clave);
        Usuario usuario = this.gestorUsuario.getUsuarios().stream()
                                                          .filter(compararLegajo.and(compararClave))
                                                          .findFirst()
                                                          .orElse(null);
        return usuario;
    }

    public boolean esAdmin(Usuario usuario) {
        return usuario.getLegajo().startsWith("A"); // Veamos con qué condición se define a un admin, esta es de ejemplo.
    }
}