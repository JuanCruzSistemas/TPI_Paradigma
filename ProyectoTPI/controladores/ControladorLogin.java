package ProyectoTPI.controladores;

import ProyectoTPI.gestores.GestorUsuario;
import ProyectoTPI.user.Usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Predicate;

public class ControladorLogin {
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

    public static String generarHash(String entradaTexto) {
        try {
            MessageDigest calculadorHash = MessageDigest.getInstance("SHA-256");
            byte[] bytes = calculadorHash.digest(entradaTexto.getBytes());

            StringBuilder resultadoHexadecimal = new StringBuilder();
            for (byte b : bytes) {
                resultadoHexadecimal.append(String.format("%02x", b));
            }
            return resultadoHexadecimal.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}