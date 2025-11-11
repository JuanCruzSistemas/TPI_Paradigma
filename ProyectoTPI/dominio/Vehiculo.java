package ProyectoTPI.dominio;

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

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Modelo getModelo() {
        return this.modelo;
    }
    
    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Marca getMarca() {
        return this.marca;
    }

    public void setPatente(String patente) {
        this.patente = patente;
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
}