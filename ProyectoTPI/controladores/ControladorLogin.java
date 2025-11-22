package ProyectoTPI.controladores;

import ProyectoTPI.gestores.GestorUsuario;
import ProyectoTPI.user.Usuario;
import org.jpl7.Query;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class ControladorLogin {
    private GestorUsuario gestorUsuario;

    public ControladorLogin(GestorUsuario gestorUsuario) {
        this.gestorUsuario = gestorUsuario;
        new Query("consult('ProyectoTPI/recursos/archivos/validacion.pl')").hasSolution();
    }

    public GestorUsuario getGestorUsuario() {
        return this.gestorUsuario;
    }

    public Usuario validarUsuario(String legajo, String clave) {
        String hashIngresado = generarHash(clave);
        
        for (Usuario u : gestorUsuario.getUsuarios()) {
            String consulta = String.format(
                "credenciales_validas('%s', '%s', '%s', '%s')",
                legajo, hashIngresado, u.getLegajo(), u.getHashClave()
            );

            if (new Query(consulta).hasSolution()) {
                return u;
            }
        }
        return null;
    }

    public static String generarHash(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            return null;
        }
    }
}