package ProyectoTPI.dominio;

public class TipoDocumento {
    private String siglas;
    private String descripcion;

    public TipoDocumento(String siglas, String descripcion) {
        this.siglas = siglas;
        this.descripcion = descripcion;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public String getSiglas() {
        return this.siglas;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    @Override
    public String toString() {
        return this.siglas + ";" + this.descripcion;
    }
}