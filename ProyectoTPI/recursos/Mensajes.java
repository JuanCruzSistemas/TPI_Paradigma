package ProyectoTPI.recursos;

import javax.swing.JOptionPane;

public class Mensajes {
    public static final String CAMPOS_VACIOS = "Por favor, complete todos los campos.";
    public static final String DATOS_INCORRECTOS = "Legajo o contraseña incorrectos.";
    public static final String NO_EXISTE_PERSONA_CON_LEGAJO = "No se encontró una persona con el legajo: ";
    public static final String YA_EXISTE_CUENTA_CON_LEGAJO = "Ya existe una cuenta con el legajo: ";
    public static final String BUSCAR_LEGAJO_VALIDO = "Primero busque un legajo válido.";
    public static final String CUENTA_REGISTRADA_CON_EXITO = "Cuenta registrada exitosamente!";
    public static final String YA_EXISTE_VEHICULO_CON_PATENTE = "Ya existe un vehículo con la patente: ";

    public static void yaExisteVehiculoConPatente(String patente) {
        mostrarError(YA_EXISTE_VEHICULO_CON_PATENTE + patente);
    }

    public static void cuentaRegistradaConExito() {
        mostrarExito(CUENTA_REGISTRADA_CON_EXITO);
    }

    public static void yaExisteCuentaConLegajo(String legajo) {
        mostrarError(YA_EXISTE_CUENTA_CON_LEGAJO + legajo);
    }

    public static void buscarLegajoValido() {
        mostrarError(BUSCAR_LEGAJO_VALIDO);
    }

    public static void noExistePersonaConLegajo(String legajo) {
        mostrarError(NO_EXISTE_PERSONA_CON_LEGAJO + legajo);
    }

    public static void mensajeCompletarCampos() {
        mostrarAdvertencia(CAMPOS_VACIOS);
    }

    public static void mensajeDatosIncorrectos() {
        mostrarError(DATOS_INCORRECTOS);
    }
    
    public static void mostrarExito(String mensaje) {
        mostrarInfo(mensaje);
    }

    public static void mostrarError(String mensaje) {
        mostrarMensaje(mensaje, JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarInfo(String mensaje) {
        mostrarMensaje(mensaje, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void mostrarAdvertencia(String mensaje) {
        mostrarMensaje(mensaje, JOptionPane.WARNING_MESSAGE);
    }

    public static void mostrarMensaje(String mensaje, int tipoMensaje) {
        JOptionPane.showMessageDialog(
            null,
            mensaje,
            "",
            tipoMensaje
        );
    }

    public static int mensajeConfirmacion(String pregunta, String titulo) {
        int opcion = JOptionPane.showConfirmDialog(
            null,
            pregunta,
            titulo,
            JOptionPane.YES_NO_OPTION
        );
        return opcion;
    }
}