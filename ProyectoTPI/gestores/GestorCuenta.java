package ProyectoTPI.gestores;

import ProyectoTPI.datos.FileCuenta;
import ProyectoTPI.dominio.Cuenta;
import ProyectoTPI.recursos.MensajesArchivos;

import java.util.List;

public class GestorCuenta {
    private FileCuenta archivoCuentas;
    private List<Cuenta> cuentas;

    public GestorCuenta() {
        this.archivoCuentas = new FileCuenta();
        // la inicialización del archivo de vehiculos
        this.cuentas = cargarCuentas();
    }

    public List<Cuenta> getCuentas() {
        return this.cuentas;
    }

    public void registrarCuenta(Cuenta c) {
        this.cuentas.add(c);
        /*
         * Agregar lógica para, además, agregar la cuenta 'c' al this.archivoCuentas (que trabaja con cuentas.csv).
         * lo mismo que en registrarUsuario de GestorUsuario.
         */
    }

    public boolean tieneCuentas() {
        return !cuentas.isEmpty();
    }

    public List<Cuenta> cargarCuentas() {
        /*
         * NO FUNCIONA porque aún no se implementó leerCuentas() de la clase FileCuenta.
         */
        List<Cuenta> cuentas = archivoCuentas.leerCuentas();
        if (cuentas.isEmpty()) {
            MensajesArchivos.archivoVacio();
        }
        return cuentas;
    }    
}