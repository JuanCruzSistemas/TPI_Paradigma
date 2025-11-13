package ProyectoTPI.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario {
    private String nombre;
    private String apellido;
    private String legajo;
    private String hashClave;

    public Usuario(String legajo, String nombre, String apellido, String clave) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.hashClave = generarHash(clave);
    }

    public String getLegajo() {
        return legajo;
    }
    public String getHashClave() {
        return hashClave;
    }
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public boolean validarClave(String clave) {
        return hashClave.equals(generarHash(clave));
    }

    private String generarHash(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // @Override
    // public String toString() {
    //     return getNombreCompleto() + ";" + 
    // }
}
