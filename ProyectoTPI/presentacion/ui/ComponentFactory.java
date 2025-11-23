package ProyectoTPI.presentacion.ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ComponentFactory {
    private ComponentFactory() {}
    
    public static JTextField crearCampoTexto() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBackground(ColorScheme.NEGRO_INPUT);
        txt.setForeground(Color.WHITE);
        txt.setCaretColor(ColorScheme.AMARILLO);
        txt.setPreferredSize(new Dimension(420, 38));
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorScheme.BORDE_OSCURO, 2),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        
        txt.addFocusListener(new FocusAdapter() {
            
            public void focusGained(FocusEvent e) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ColorScheme.AMARILLO, 2),
                    BorderFactory.createEmptyBorder(6, 12, 6, 12)
                ));
            }
            public void focusLost(FocusEvent e) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ColorScheme.BORDE_OSCURO, 2),
                    BorderFactory.createEmptyBorder(6, 12, 6, 12)
                ));
            }
        });
        return txt;
    }
    
    public static JTextField crearCampoTextoSoloLectura() {
        JTextField txt = crearCampoTexto();
        txt.setEditable(false);
        txt.setBackground(new Color(30, 30, 30));
        txt.setForeground(ColorScheme.GRIS_TEXTO);
        return txt;
    }
    
    public static JComboBox<String> crearComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(ColorScheme.NEGRO_INPUT);
        combo.setForeground(Color.WHITE);
        combo.setPreferredSize(new Dimension(420, 38));
        combo.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDE_OSCURO, 2));
        return combo;
    }
    
    public static JLabel crearEtiqueta(String texto, int tamano, boolean negrita, Color color) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", negrita ? Font.BOLD : Font.PLAIN, tamano));
        label.setForeground(color);
        return label;
    }
    
    public static JSeparator crearSeparador() {
        JSeparator sep = new JSeparator();
        sep.setForeground(ColorScheme.BORDE_OSCURO);
        sep.setMaximumSize(new Dimension(280, 1));
        return sep;
    }
    
    public static JSeparator crearSeparadorAncho(int ancho) {
        JSeparator sep = new JSeparator();
        sep.setForeground(ColorScheme.BORDE_OSCURO);
        sep.setMaximumSize(new Dimension(ancho, 1));
        return sep;
    }
    
    public static JTable crearTablaEstilizada(Object[][] datos, String[] columnas) {
        JTable tabla = new JTable(datos, columnas);
        tabla.setBackground(ColorScheme.NEGRO_INPUT);
        tabla.setForeground(Color.WHITE);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(40);
        tabla.setGridColor(ColorScheme.BORDE_OSCURO);
        tabla.getTableHeader().setBackground(ColorScheme.NEGRO_PANEL);
        tabla.getTableHeader().setForeground(ColorScheme.AMARILLO);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.setSelectionBackground(ColorScheme.SELECCION_TABLA);
        tabla.setSelectionForeground(Color.WHITE);
        return tabla;
    }
}