package ProyectoTPI.dominio;

import ProyectoTPI.recursos.MensajesDominio;

import java.time.LocalDateTime;
import java.util.Objects;

public class Cuenta implements Recargable {
    private String nombre;
    private String apellido;
    private String legajo;
    private TipoDocumento tipoDocumento;
    private String nroDocumento;
    private LocalDateTime fechaYHoraCreacion;
    private LocalDateTime fechaYHoraBaja;
    private EstadoCuenta estadoCuenta;
    private RolPersonaUTN rolPersonaUtn;
    private double saldo;

    public Cuenta(String nombre, String apellido, String legajo, TipoDocumento tipoDocumento, String nroDocumento, RolPersonaUTN rolPersonaUtn) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.legajo = legajo;
        this.fechaYHoraCreacion = LocalDateTime.now();
        this.estadoCuenta = EstadoCuenta.ACTIVA;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.rolPersonaUtn = rolPersonaUtn;
        this.saldo = 0;
    }

    // Constructor para la lectura desde cuentas.csv
    public Cuenta(String nombre, String apellido, String legajo, TipoDocumento tipoDocumento, String nroDocumento, LocalDateTime fechaYHoraCreacion, EstadoCuenta estadoCuenta, RolPersonaUTN rolPersonaUtn, double saldo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.legajo = legajo;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.fechaYHoraCreacion = fechaYHoraCreacion;
        this.estadoCuenta = estadoCuenta;
        this.rolPersonaUtn = rolPersonaUtn;
        this.saldo = saldo;
    }
    
    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public String getLegajo() {
        return this.legajo;
    }

    public TipoDocumento getTipoDocumento() {
        return this.tipoDocumento;
    }

    public String getNroDocumento() {
        return this.nroDocumento;
    }
    
    public LocalDateTime getFechaYHoraCreacion() {
        return this.fechaYHoraCreacion;
    }

    public LocalDateTime getFechaYHoraBaja() {
        if (!poseeFechaDeBaja()) {
            System.out.println(MensajesDominio.CUENTA_NUNCA_DADA_BAJA);
            return null;
        } else {
            return this.fechaYHoraBaja;
        }
    }
    
    public EstadoCuenta getEstadoCuenta() {
        return this.estadoCuenta;
    }
    
    public RolPersonaUTN getRolPersonaUTN() {
        return this.rolPersonaUtn;
    }
    
    public double getSaldo() {
        return this.saldo;
    }

    public boolean poseeFechaDeBaja() {
        return this.fechaYHoraBaja != null;
    }

    public void darDeBajaCuenta() {
        if (estaInactiva()) {
            MensajesDominio.mostrarInfo(MensajesDominio.CUENTA_YA_DADA_BAJA + "\nFecha de baja: " + this.fechaYHoraBaja);
        } else {
            this.estadoCuenta = EstadoCuenta.INACTIVA;
            this.fechaYHoraBaja = LocalDateTime.now();
        }
    }
    
    public boolean estaInactiva() {
        return this.estadoCuenta == EstadoCuenta.INACTIVA;
    }

    public void reactivarCuenta() {
        this.estadoCuenta = EstadoCuenta.ACTIVA;
    }

    public void recargarSaldo(double monto) {
        this.saldo += monto;
    }

    public void disminuirSaldo(double monto) {
        this.saldo -= monto;
    }

    @Override
    public String toString() {
        String representacion = String.format(
            "%s;%s;%s;%s;%s;%s;%s;%s;%s;%.2f",
            this.nombre,
            this.apellido,
            this.legajo,
            this.tipoDocumento.getSiglas(),
            this.nroDocumento,
            this.fechaYHoraCreacion,
            this.fechaYHoraBaja,
            this.estadoCuenta,
            this.rolPersonaUtn.getNombreRol(),
            this.saldo
        ).replace(",", "."); // Para replazar la coma decimal del saldo por punto: "0,00" -> "0.00"
        return representacion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cuenta)) {
            return false;
        }
        Cuenta cuenta = (Cuenta) obj;
        return Objects.equals(this.legajo, cuenta.legajo) && Objects.equals(this.nroDocumento, cuenta.nroDocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.legajo, this.nroDocumento);
    }
}