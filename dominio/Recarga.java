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

    // Constructor para la lectura desde recargas.csv
    public Recarga(LocalDateTime fechaYHora, double monto, Cuenta cuenta, TipoRecarga tipoRecarga) {
        this.fechaYHora = fechaYHora;
        this.monto = monto;
        this.cuenta = cuenta;
        this.tipoRecarga = tipoRecarga;
    }

    public LocalDateTime getFechaYHora() {
        return this.fechaYHora;
    }

    public double getMonto() {
        return this.monto;
    }

    public Cuenta getCuenta() {
        return this.cuenta;
    }

    public TipoRecarga getTipoRecarga() {
        return this.tipoRecarga;
    }
    
    public void recargarCuenta(double monto) {
        this.cuenta.recargarSaldo(monto);
    }

    @Override
    public String toString() {
        return this.fechaYHora + ";" + this.monto + ";" + this.cuenta.getLegajo() + ";" + this.tipoRecarga;
    }
}