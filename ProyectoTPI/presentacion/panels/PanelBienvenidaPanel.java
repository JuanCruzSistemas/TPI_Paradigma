package ProyectoTPI.presentacion.panels;

import ProyectoTPI.presentacion.ui.ColorScheme;
import ProyectoTPI.presentacion.ui.ComponentFactory;

import javax.swing.*;
import java.awt.*;

/**
 
Panel de bienvenida inicial.
Principio: Single Responsibility - Solo muestra el mensaje de bienvenida.*/
public class PanelBienvenidaPanel extends JPanel {

    public PanelBienvenidaPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        inicializar();
    }

    private void inicializar() {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);

        JLabel lblBienvenida = ComponentFactory.crearEtiqueta(
            "Bienvenido al Sistema", 
            42, 
            true, 
            Color.WHITE
        );
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblBienvenida);
        contenedor.add(Box.createVerticalStrut(15));

        JLabel lblDesc = ComponentFactory.crearEtiqueta(
            "Seleccione una opción del menú lateral para comenzar", 
            18, 
            false, 
            ColorScheme.GRIS_TEXTO
        );
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblDesc);

        add(contenedor);
    }
}