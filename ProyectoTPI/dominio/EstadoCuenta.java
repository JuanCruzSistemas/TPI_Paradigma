package ProyectoTPI.dominio;

public class EstadoCuenta {
    private String valorEstado;
    private boolean estado;

    public EstadoCuenta(String valorEstado) {
        this.valorEstado = valorEstado;
        this.estado = true;
    }

    public void setValorEstadoCuenta(String valorEstado) {
        this.valorEstado = valorEstado;
    }

    public String getValorEstadoCuenta() {
        return this.valorEstado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean getEstado() {
        return this.estado;
    }
}