package ProyectoTPI.dominio;

import java.time.LocalDateTime;

public class Recarga {
    private LocalDateTime fechaYHora;
    private double monto;
    private Cuenta cuenta;
    private TipoRecarga tipoRecarga;

    public Recarga(double monto, Cuenta cuenta, TipoRecarga tipoRecarga) {
        this.fechaYHora = LocalDateTime.now();
        this.monto = monto;
        this.cuenta = cuenta;
        this.tipoRecarga = tipoRecarga;
    }

    public LocalDateTime getFechaYHora() {
        return this.fechaYHora;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getMonto() {
        return this.monto;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Cuenta getCuenta() {
        return this.cuenta;
    }

    public TipoRecarga getTipoRecarga() {
        return this.tipoRecarga;
    }
    
    public void recargarCuenta(double monto) {
        cuenta.recargarSaldo(monto);
    }
}