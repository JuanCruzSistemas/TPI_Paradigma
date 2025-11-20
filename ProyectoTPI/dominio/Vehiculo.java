package ProyectoTPI.dominio;

import java.util.Objects;

public class Vehiculo {
    private Modelo modelo;
    private Marca marca;
    private String patente;
    private String color;
    private Cuenta cuenta;

    public Vehiculo(Modelo modelo, Marca marca, String patente, String color, Cuenta cuenta) {
        this.modelo = modelo;
        this.marca = marca;
        this.patente = patente;
        this.color = color;
        this.cuenta = cuenta;
    }

    public Modelo getModelo() {
        return this.modelo;
    }

    public Marca getMarca() {
        return this.marca;
    }

    public String getPatente() {
        return this.patente;
    }
    
    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Cuenta getCuenta() {
        return this.cuenta;
    }

    @Override
    public String toString() {
        return this.modelo.getNombre() + ";" + this.marca.getNombre() + ";" + this.patente + ";" + this.color + ";" + this.cuenta.getLegajo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehiculo)) {
            return false;
        }
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(this.patente, vehiculo.patente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patente);
    }
}