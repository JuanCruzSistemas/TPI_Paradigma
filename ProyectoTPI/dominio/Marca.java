package ProyectoTPI.dominio;

import java.util.List;

public class Marca {
    private String nombre;
    private List<Modelo> modelos;
    
    public Marca(String nombre) {
        this.nombre = nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }

    public List<Modelo> getModelos() {
        return this.modelos;
    }

    @Override
    public String toString() {
        StringBuilder representacion = new StringBuilder();
        this.modelos.forEach(m -> representacion.append(this.nombre).append(";").append(m).append("\n"));
        return representacion.toString();
    }
}