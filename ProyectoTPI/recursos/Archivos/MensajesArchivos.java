package ProyectoTPI.recursos.Archivos;

import javax.swing.JOptionPane;

public class MensajesArchivos {
    public static final String ERROR_CREACION = "Error al crear el archivo.";
    public static final String ERROR_ESCRITURA = "Error al escribir en el archivo.";
    public static final String ERROR_LECTURA = "Error al leer el archivo.";
    public static final String ERROR_LIMPIEZA = "Error al limpiar el archivo.";
    public static final String ERROR_ELIMINACION = "Error al eliminar el archivo.";
    public static final String NO_EXISTE = "Error. El archivo no existe.";
    public static final String ARCHIVO_ELIMINADO = "Archivo eliminado.";

    public static void errorCreacionArchivo() {
        mostrarError(ERROR_CREACION);
    }
    
    public static void errorEscrituraArchivo() {
        mostrarError(ERROR_ESCRITURA);
    }

    public static void errorLecturaArchivo() {
        mostrarError(ERROR_LECTURA);
    }

    public static void errorLimpiezaArchivo() {
        mostrarError(ERROR_LIMPIEZA);
    }

    public static void errorEliminacionArchivo() {
        mostrarError(ERROR_ELIMINACION);
    }

    public static void archivoNoExiste() {
        mostrarAdvertencia(NO_EXISTE);
    }

    public static void archivoEliminado() {
        mostrarInfo(ARCHIVO_ELIMINADO);
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
            "ALERTA",
            tipoMensaje
        );
    }

    public static int mensajeConfirmacion(String pregunta) {
        int opcion = JOptionPane.showConfirmDialog(
            null,
            pregunta,
            "Confirmar",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        return opcion;
    }
}