package ProyectoTPI.dominio;

public class TipoRecarga {
    private String nombre;

    public TipoRecarga(String nombre) {
        this.nombre = nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
}