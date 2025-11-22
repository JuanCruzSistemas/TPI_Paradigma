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

/**
 * Panel para consultar y visualizar vehículos registrados.
 * Principio: Single Responsibility - Solo maneja la visualización de vehículos.
 */
public class PanelConsultaVehiculos extends JPanel {
    private GestorCuenta gestorCuenta;
    
    public PanelConsultaVehiculos(GestorCuenta gestorCuenta) {
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
        
        JLabel titulo = ComponentFactory.crearEtiqueta("? Consultar Vehículos", 28, true, ColorScheme.AMARILLO);
        contenedor.add(titulo, BorderLayout.NORTH);
        
        if (!tieneVehiculos()) {
            contenedor.add(crearPanelVacio(), BorderLayout.CENTER);
        } else {
            contenedor.add(crearTabla(), BorderLayout.CENTER);
        }
        
        add(contenedor);
    }
    
    private boolean tieneVehiculos() {
        FileManager archivoVehiculos = gestorCuenta.getArchivoVehiculos();
        java.util.List<String> lineas = archivoVehiculos.leerArchivo();
        return !lineas.isEmpty();
    }
    
    private JPanel crearPanelVacio() {
        JPanel emptyPanel = new JPanel(new GridBagLayout());
        emptyPanel.setOpaque(false);
        JLabel lblVacio = ComponentFactory.crearEtiqueta(
            "No hay vehículos registrados aún",
            18,
            false,
            ColorScheme.GRIS_TEXTO
        );
        lblVacio.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        emptyPanel.add(lblVacio);
        return emptyPanel;
    }
    
    private JScrollPane crearTabla() {
        // CAMBIA ESTAS COLUMNAS SEGÚN TUS NECESIDADES
        String[] columnas = {
            "Modelo", "Marca", "Patente", "Color", "Legajo cuenta"
        };
        
        Object[][] datos = obtenerDatosVehiculos();
        
        JTable tabla = ComponentFactory.crearTablaEstilizada(datos, columnas);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBackground(ColorScheme.NEGRO_INPUT);
        scroll.getViewport().setBackground(ColorScheme.NEGRO_INPUT);
        scroll.setBorder(new LineBorder(ColorScheme.BORDE_OSCURO));
        
        return scroll;
    }
    
    private Object[][] obtenerDatosVehiculos() {
        FileManager archivoVehiculos = gestorCuenta.getArchivoVehiculos();
        java.util.List<String> lineas = archivoVehiculos.leerArchivo();
        
        // AJUSTA ESTOS ÍNDICES SEGÚN LA ESTRUCTURA DE TU CSV
        // Ejemplo: si tu CSV es: modelo;marca;patente;color;legajo
        return lineas.stream()
                     .map(s -> s.split(";"))
                     .map(campos -> new Object[]{
                         campos[0],  // Modelo
                         campos[1],  // Marca
                         campos[2],  // Patente
                         campos[3],  // Color
                         campos[4]   // Legajo Propietario
                     })
                     .toArray(Object[][]::new);
    }
}