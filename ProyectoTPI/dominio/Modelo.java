package ProyectoTPI.dominio;

public class Modelo {
    private String nombre;
    private int año;
    private String descripcion;
    
    public Modelo(String nombre, int año, String descripcion) {
        this.nombre = nombre;
        this.año = año;
        this.descripcion = descripcion;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getAño() {
        return this.año;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    @Override
    public String toString() {
        return this.nombre + ";" + this.año + ";" + this.descripcion;
    }
}