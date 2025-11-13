package ProyectoTPI;

import ProyectoTPI.presentacion.Login;
import ProyectoTPI.gestores.GestorCargarDatos;
public class Main {
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GestorCargarDatos gestor = new GestorCargarDatos();
            gestor.cargarDatos();
            new Login(gestor.getUsuarios());
        });
    }
}
