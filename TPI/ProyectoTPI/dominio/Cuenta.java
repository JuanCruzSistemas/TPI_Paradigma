package ProyectoTPI.dominio;

import ProyectoTPI.recursos.MensajesDominio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.time.LocalDateTime;

public class Cuenta {
    private String nombre;
    private String apellido;
    private int legajo;
    private TipoDocumento tipoDocumento;
    private String nroDocumento;
    private LocalDateTime fechaYHoraCreacion;
    private LocalDateTime fechaYHoraBaja;
    private EstadoCuenta estadoCuenta;
    private RolPersonaUTN rolPersonaUtn;
    private ArrayList<Vehiculo> vehiculos;
    private double saldo;

    public Cuenta(String nombre, String apellido, int legajo, RolPersonaUTN rolPersonaUtn, TipoDocumento tipoDocumento, String nroDocumento, EstadoCuenta estadoCuenta, double saldo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.legajo = legajo;
        this.fechaYHoraCreacion = LocalDateTime.now();
        this.estadoCuenta = EstadoCuenta.ACTIVA;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.vehiculos = new ArrayList<>();
        this.rolPersonaUtn = rolPersonaUtn;
        this.saldo = saldo;
    }
    
    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public int getLegajo() {
        return this.legajo;
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
    
    public List<Vehiculo> getVehiculos() {
        if (!tieneVehiculos()) {
            return null;
        }
        return this.vehiculos;
    }

    public TipoDocumento getTipoDocumento() {
        return this.tipoDocumento;
    }

    public String getNroDocumento() {
        return this.nroDocumento;
    }
    
    public RolPersonaUTN getRolPersonaUTN() {
        return this.rolPersonaUtn;
    }

    public Vehiculo getVehiculo(String patente) {
        if (!tieneVehiculos()) {
            return null;
        }
        return buscarVehiculo(patente);
    }
    
    public double getSaldo() {
        return this.saldo;
    }

    public boolean tieneVehiculos() {
        if (this.vehiculos.isEmpty()) {
            System.out.println(MensajesDominio.NO_POSEE_VEHICULOS);
            return false;
        }
        return true;
    }

    public Vehiculo buscarVehiculo(String patente) {
        Predicate<Vehiculo> verificarPatente = v -> v.getPatente().equals(patente);
        Vehiculo vehiculo = vehiculos.stream()
                              .filter(verificarPatente)
                              .findFirst()
                              .orElse(null);
        return vehiculo;
    }

    public boolean poseeFechaDeBaja() {
        return this.fechaYHoraBaja != null;
    }

    public void agregarVehiculo(Vehiculo Automotor) {
        this.vehiculos.add(Automotor);
    }

    public void eliminarVehiculo(Vehiculo Automotor) {
        this.vehiculos.remove(Automotor);
        System.out.println(MensajesDominio.VEHICULO_ELIMINADO);
    }

    public void darDeBajaCuenta() {
        if (estaInactiva()) {
            System.out.println(MensajesDominio.CUENTA_YA_DADA_BAJA + " Fecha de baja: " + this.fechaYHoraBaja);
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
}
