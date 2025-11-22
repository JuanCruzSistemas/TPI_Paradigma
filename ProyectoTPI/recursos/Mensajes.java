package ProyectoTPI.recursos;

import javax.swing.JOptionPane;

public class Mensajes {
    public static final String CAMPOS_VACIOS = "Por favor, complete todos los campos.";
    public static final String DATOS_INCORRECTOS = "Legajo o contraseña incorrectos.";

    public static void mensajeCompletarCampos() {
        mostrarAdvertencia(CAMPOS_VACIOS);
    }

    public static void mensajeDatosIncorrectos() {
        mostrarError(DATOS_INCORRECTOS);
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