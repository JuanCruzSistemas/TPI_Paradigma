package ProyectoTPI.user;

import ProyectoTPI.controladores.ControladorLogin;

public class Usuario {
    private String nombre;
    private String apellido;
    private String legajo;
    private String hashClave;

    public Usuario(String legajo, String nombre, String apellido, String clave) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.hashClave = clave;
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
        return hashClave.equals(ControladorLogin.generarHash(clave));
    }

    @Override
    public String toString() {
        return this.legajo + ";" + this.nombre + ";" + this.apellido + ";" + this.hashClave;
    }
}