package ProyectoTPI.recursos;

public class MensajesDominio extends Mensajes {
    public static final String CUENTA_YA_DADA_BAJA = "La cuenta ya esta dada de baja.";
    public static final String CUENTA_NUNCA_DADA_BAJA = "La cuenta nunca fue dada de baja.";
    public static final String NO_POSEE_VEHICULOS = "La cuenta no posee vehículos registrados.";
    public static final String VEHICULO_ELIMINADO = "Vehículo eliminado.";
    public static final String VEHICULO_REGISTRADO_CON_EXITO = "Vehículo registrado exitosamente!";

    public static void advertenciaNoPoseeVehiculos() {
        mostrarAdvertencia(NO_POSEE_VEHICULOS);
    }

    public static void infoVehiculoEliminado() {
        mostrarInfo(VEHICULO_ELIMINADO);
    }

    public static void vehiculoRegistradoConExito() {
        mostrarInfo(VEHICULO_REGISTRADO_CON_EXITO);
    }
}