package ProyectoTPI.dominio;

import ProyectoTPI.recursos.MensajesDominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Cuenta {
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
    private List<Vehiculo> vehiculos;

    public Cuenta(String nombre, String apellido, String legajo, TipoDocumento tipoDocumento, String nroDocumento, RolPersonaUTN rolPersonaUtn, EstadoCuenta estadoCuenta, double saldo) {
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

    // Constructor para la lectura desde cuentas.csv
    public Cuenta(String nombre, String apellido, String legajo, TipoDocumento tipoDocumento, String nroDocumento, LocalDateTime fechaYHoraCreacion, LocalDateTime fechaYHoraBaja, EstadoCuenta estadoCuenta, RolPersonaUTN rolPersonaUtn, double saldo, List<Vehiculo> vehiculos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.legajo = legajo;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.fechaYHoraCreacion = fechaYHoraCreacion;
        this.fechaYHoraBaja = fechaYHoraBaja;
        this.estadoCuenta = estadoCuenta;
        this.rolPersonaUtn = rolPersonaUtn;
        this.saldo = saldo;
        this.vehiculos = vehiculos;
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

    public List<Vehiculo> getVehiculos() {
        return this.vehiculos;
    }
    
    public Vehiculo getVehiculo(String patente) {
        if (!tieneVehiculos()) {
            return null;
        }
        return buscarVehiculo(patente);
    }

    public boolean tieneVehiculos() {
        if (this.vehiculos.isEmpty()) {
            MensajesDominio.advertenciaNoPoseeVehiculos();
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
    
    public List<String> getPatentesVehiculos() {
        return this.vehiculos.stream()
                             .map(v -> v.getPatente())
                             .collect(Collectors.toList());
    }
    
    public void agregarVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
    }
    
    public void eliminarVehiculo(Vehiculo vehiculo) {
        this.vehiculos.remove(vehiculo);
        MensajesDominio.infoVehiculoEliminado();
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
            "%s;%s;%s;%s;%s;%s;%s;%s;%s;%.2f;%s",
            this.nombre,
            this.apellido,
            this.legajo,
            this.tipoDocumento.getSiglas(),
            this.nroDocumento,
            this.fechaYHoraCreacion,
            this.fechaYHoraBaja,
            this.estadoCuenta,
            this.rolPersonaUtn.getNombreRol(),
            this.saldo,
            getPatentesVehiculos()
        );
        return representacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cuenta)) {
            return false;
        }
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(this.legajo, cuenta.legajo) && Objects.equals(this.nroDocumento, cuenta.nroDocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.legajo, this.nroDocumento);
    }
}