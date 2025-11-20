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

    /**
     * 
     * Constructor para la lectura desde usuarios.csv
     * La variable esHash sirve para saber si se está leyendo desde el archivo usuarios.csv (true) o si se está
     * creando el objeto, para lo que sea, desde cero, sin registro previo (false)
     * Ahora que lo pienso el constructor de arriba podría no estar y crear objetos con el de abajo, poniendo esHash = false,
     * pero bueno, me parece que el de arriba queda más clean para cuando no se lee en el arhcivo, pero bueno, como quieran.
     * 
     */
    public Usuario(String legajo, String nombre, String apellido, String clave, boolean esHash) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.hashClave = esHash ? clave : generarHash(clave);
    }

    public String getLegajo() {
        return this.legajo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public String getHashClave() {
        return this.hashClave;
    }

    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    public boolean validarClave(String clave) {
        return hashClave.equals(generarHash(clave));
    }

    private String generarHash(String entradaTexto) {
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

    @Override
    public String toString() {
        return this.legajo + ";" + this.nombre + ";" + this.apellido + ";" + this.hashClave;
    }
}