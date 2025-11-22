package ProyectoTPI.dominio;

import java.time.LocalDateTime;

public class RolPersonaUTN {
    private String nombreRol;
    private LocalDateTime fechaYHoraInicioDescuento;
    private LocalDateTime fechaYHoraFinDescuento;
    private double valorDescuento;
    
    public RolPersonaUTN(String nombreRol, double valorDescuento) {
        this.nombreRol = nombreRol;
        this.fechaYHoraInicioDescuento = LocalDateTime.now();
        this.valorDescuento = valorDescuento;
    }

    // Constructor para la lectura desde roles_utn.csv
    public RolPersonaUTN(String nombreRol, LocalDateTime fechaYHoraInicio, LocalDateTime fechaYHoraFin, double valorDescuento) {
        this.nombreRol = nombreRol;
        this.fechaYHoraInicioDescuento = fechaYHoraInicio;
        this.fechaYHoraFinDescuento = fechaYHoraFin;
        this.valorDescuento = valorDescuento;
    }
    
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getNombreRol() {
        return this.nombreRol;
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

    public double getDescuento() {
        return this.valorDescuento;
    }

    @Override
    public String toString() {
        return this.nombreRol + ";" + this.fechaYHoraInicioDescuento + ";" + this.fechaYHoraFinDescuento + ";" + this.valorDescuento;
    }
}