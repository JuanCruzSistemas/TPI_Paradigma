package ProyectoTPI.presentacion.panels;

import ProyectoTPI.datos.FileManager;
import ProyectoTPI.gestores.GestorCuenta;
import ProyectoTPI.presentacion.ui.ColorScheme;
import ProyectoTPI.presentacion.ui.ComponentFactory;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import java.util.List;

public class PanelConsultaCuenta extends JPanel {
    private GestorCuenta gestorCuenta;
    
    public PanelConsultaCuenta(GestorCuenta gestorCuenta) {
        this.gestorCuenta = gestorCuenta;
        setLayout(new GridBagLayout());
        setOpaque(false);
        inicializar();
    }
    
    private void inicializar() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 20));
        contenedor.setBackground(ColorScheme.NEGRO_PANEL_TRANSPARENTE);
        contenedor.setBorder(new CompoundBorder(
            new LineBorder(ColorScheme.BORDE_OSCURO, 1, true),
            new EmptyBorder(30, 40, 30, 40)
        ));
        contenedor.setPreferredSize(new Dimension(900, 550));
        
        JLabel titulo = ComponentFactory.crearEtiqueta("? Consultar Cuentas", 28, true, ColorScheme.AMARILLO);
        contenedor.add(titulo, BorderLayout.NORTH);
        
        if (!gestorCuenta.tieneCuentas()) {
            contenedor.add(crearPanelVacio(), BorderLayout.CENTER);
        } else {
            contenedor.add(crearTabla(), BorderLayout.CENTER);
        }
        
        add(contenedor);
    }
    
    private JPanel crearPanelVacio() {
        JPanel emptyPanel = new JPanel(new GridBagLayout());
        emptyPanel.setOpaque(false);
        JLabel lblVacio = ComponentFactory.crearEtiqueta(
            "No hay cuentas registradas aún", 
            18, 
            false, 
            ColorScheme.GRIS_TEXTO
        );
        lblVacio.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        emptyPanel.add(lblVacio);
        return emptyPanel;
    }
    
    private JScrollPane crearTabla() {
        String[] columnas = {
            "Nombre", "Apellido", "Legajo", "Tipo documento", 
            "Nro documento", "Fecha y hora creación", "Estado", "Rol", "Saldo"
        };
        
        Object[][] datos = obtenerDatosCuentas();
        
        JTable tabla = ComponentFactory.crearTablaEstilizada(datos, columnas);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBackground(ColorScheme.NEGRO_INPUT);
        scroll.getViewport().setBackground(ColorScheme.NEGRO_INPUT);
        scroll.setBorder(new LineBorder(ColorScheme.BORDE_OSCURO));
        
        return scroll;
    }
    
    private Object[][] obtenerDatosCuentas() {
        FileManager archivoCuenta = gestorCuenta.getArchivoCuentas();
        List<String> lineas = archivoCuenta.leerArchivo();
        
        return lineas.stream()
                     .map(s -> s.split(";"))
                     .map(campos -> new Object[]{
                         campos[0],  // Nombre
                         campos[1],  // Apellido
                         campos[2],  // Legajo
                         campos[3],  // Tipo documento
                         campos[4],  // Nro documento
                         campos[5],  // Fecha y hora creación
                         campos[7],  // Estado
                         campos[8],  // Rol
                         campos[9]   // Saldo
                     })
                     .toArray(Object[][]::new);
    }
}