package ProyectoTPI;

import ProyectoTPI.gestores.GestorUsuario;
import ProyectoTPI.presentacion.Login;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestorUsuario gestor = new GestorUsuario();
            new Login(gestor); // le puse que le pase el gestor directamente
        });
    }
}