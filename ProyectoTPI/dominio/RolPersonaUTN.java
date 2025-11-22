package ProyectoTPI.dominio;

import java.time.LocalDateTime;

public class RolPersonaUTN {
    private String nombreRol;
    private LocalDateTime fechaYHoraInicioDescuento;
    private LocalDateTime fechaYHoraFinDescuento;
    private double valorDescuento;
    
    public RolPersonaUTN(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getNombreRol() {
        return this.nombreRol;
    }

    public void darDeAltaDescuento(double valorDescuento) {
        this.valorDescuento = valorDescuento;
        this.fechaYHoraInicioDescuento = LocalDateTime.now();
    }

    public void darDeBajaDescuento() {
        this.valorDescuento = 0;
        this.fechaYHoraFinDescuento = LocalDateTime.now();
    }
    
    public LocalDateTime getFechaYHoraInicioDescuento() {
        return fechaYHoraInicioDescuento;
    }

    public LocalDateTime getFechaYHoraFinDescuento() {
        return this.fechaYHoraFinDescuento;
    }

    public void setValorDescuento(double valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    @Override
    public String toString() {
        return this.nombreRol + ";" + this.fechaYHoraInicioDescuento + ";" + this.fechaYHoraFinDescuento + ";" + this.valorDescuento;
    }
}