package ProyectoTPI.gestores;

import ProyectoTPI.datos.FileCuenta;
import ProyectoTPI.datos.FileManager;
import ProyectoTPI.dominio.Cuenta;
import ProyectoTPI.dominio.Marca;
import ProyectoTPI.dominio.Modelo;
import ProyectoTPI.dominio.Recarga;
import ProyectoTPI.dominio.RolPersonaUTN;
import ProyectoTPI.dominio.TipoDocumento;
import ProyectoTPI.dominio.TipoRecarga;
import ProyectoTPI.dominio.Vehiculo;
import ProyectoTPI.recursos.Rutas;

import java.util.List;
import java.util.function.Predicate;

public class GestorCuenta {
    private FileManager archivoVehiculos;
    private FileManager archivoRecargas;
    private FileManager archivoPersonas;
    private FileCuenta archivoCuentas;
    private List<Cuenta> cuentas;

    public GestorCuenta() {
        this.archivoVehiculos = new FileManager(Rutas.RUTA_VEHICULOS);
        this.archivoRecargas = new FileManager(Rutas.RUTA_RECARGAS);
        this.archivoPersonas = new FileManager(Rutas.RUTA_PERSONAS);
        this.archivoCuentas = new FileCuenta();
        this.cuentas = cargarCuentasGestor();
    }

    public List<Cuenta> getCuentas() {
        return this.cuentas;
    }

    public FileCuenta getArchivoCuentas() {
        return this.archivoCuentas;
    }

    public FileManager getArchivoVehiculos() {
        return this.archivoVehiculos;
    }

    public FileManager getArchivoPersonas() {
        return this.archivoPersonas;
    }

    public void registrarCuenta(List<String> informacion) {
        Cuenta c = armarCuenta(informacion);
        this.cuentas.add(c);
        this.archivoCuentas.agregarInformacion(true, c);
    }

    public Cuenta armarCuenta(List<String> informacion) {
        String nombre = informacion.get(0);
        String apellido = informacion.get(1);
        String legajo = informacion.get(2);
        TipoDocumento tipoDocumento = archivoCuentas.buscarTipoDocumento(informacion.get(3));
        String nroDocumento = informacion.get(4);
        RolPersonaUTN rolPersonaUTN = new RolPersonaUTN(informacion.get(5));
        return new Cuenta(nombre, apellido, legajo, tipoDocumento, nroDocumento, rolPersonaUTN);
    }

    public void registrarVehiculo(List<String> informacion) {
        Vehiculo vehiculo = armarVehiculo(informacion);
        this.archivoVehiculos.agregarInformacion(true, vehiculo);
    }

    public Vehiculo armarVehiculo(List<String> informacion) {
        Modelo modelo = new Modelo(informacion.get(0));
        Marca maca = new Marca(informacion.get(1));
        String patente = informacion.get(2);
        String color = informacion.get(3);
        Cuenta cuenta = buscarCuentaPorLegajo(informacion.get(4));
        return new Vehiculo(modelo, maca, patente, color, cuenta);
    }
    
    public Cuenta buscarCuentaPorLegajo(String legajo) {
        Predicate<Cuenta> compararLegajo = c -> c.getLegajo().equals(legajo);
        return this.cuentas.stream().filter(compararLegajo)
                                    .findFirst()
                                    .orElse(null);
    }

    public boolean tieneCuentas() {
        return !cuentas.isEmpty();
    }

    public List<Cuenta> cargarCuentasGestor() {
        List<Cuenta> cuentas = archivoCuentas.leerCuentas();
        return cuentas;
    }

    public void recargarCuenta(String legajo, double monto, TipoRecarga tipoRecarga) {
        Cuenta cuenta = buscarCuentaPorLegajo(legajo);
        Recarga recarga = new Recarga(monto, cuenta, tipoRecarga);
        recarga.recargarCuenta(monto);
        this.archivoRecargas.agregarInformacion(true, recarga);
    }
}