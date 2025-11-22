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

public class GestorCuenta {
    private FileManager archivoVehiculos;
    private FileManager archivoRecargas;
    private FileCuenta archivoCuentas;
    private List<Cuenta> cuentas;

    public GestorCuenta() {
        this.archivoVehiculos = new FileManager(Rutas.RUTA_VEHICULOS);
        this.archivoRecargas = new FileManager(Rutas.RUTA_RECARGAS);
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
        Vehiculo v = armarVehiculo(informacion);
        this.archivoVehiculos.agregarInformacion(true, v);
    }

    public Vehiculo armarVehiculo(List<String> inforacion) {
        Modelo modelo = new Modelo(inforacion.get(0));
        Marca maca = new Marca(inforacion.get(1));
        String patente = inforacion.get(2);
        String color = inforacion.get(3);
        Cuenta cuenta = buscarCuentaPorLegajo(inforacion.get(4));
        return new Vehiculo(modelo, maca, patente, color, cuenta);
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

    public Cuenta buscarCuentaPorLegajo(String legajo) {
        return this.cuentas.stream().filter(c -> c.getLegajo().equals(legajo))
                                    .findFirst()
                                    .orElse(null);
    }
}