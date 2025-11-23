package ProyectoTPI.presentacion.panels;

import ProyectoTPI.presentacion.ui.ColorScheme;
import ProyectoTPI.presentacion.ui.ComponentFactory;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComponent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

public abstract class FormularioBase extends JPanel {
    protected JPanel camposPanel;
    private String titulo;
    
    public FormularioBase(String titulo) {
        this.titulo = titulo;
        setLayout(new GridBagLayout());
        setOpaque(false);
        inicializar();
    }
    
    private void inicializar() {
        JPanel form = crearPanelFormulario();
        add(form);
    }
    
    private JPanel crearPanelFormulario() {
        JPanel form = new JPanel(new BorderLayout(0, 20));
        form.setBackground(ColorScheme.NEGRO_PANEL_OPACO);
        form.setBorder(new CompoundBorder(
            new LineBorder(ColorScheme.BORDE_OSCURO, 1, true),
            new EmptyBorder(30, 50, 25, 50)
        ));
        form.setPreferredSize(getDimensionesFormulario());
        
        JLabel lblTitulo = ComponentFactory.crearEtiqueta(titulo, 26, true, ColorScheme.AMARILLO);
        form.add(lblTitulo, BorderLayout.NORTH);
        
        camposPanel = new JPanel();
        camposPanel.setLayout(new BoxLayout(camposPanel, BoxLayout.Y_AXIS));
        camposPanel.setOpaque(false);
        form.add(camposPanel, BorderLayout.CENTER);
        
        construirFormulario();
        
        JPanel btnPanel = crearPanelBotones();
        form.add(btnPanel, BorderLayout.SOUTH);
        
        return form;
    }
    
    protected void agregarCampo(String label, JComponent campo) {
        JPanel fila = new JPanel(new BorderLayout(0, 5));
        fila.setOpaque(false);
        fila.setMaximumSize(new Dimension(420, 65));
        fila.setBorder(new EmptyBorder(0, 0, 6, 0));
        
        JLabel lbl = ComponentFactory.crearEtiqueta(label, 13, true, ColorScheme.AMARILLO);
        fila.add(lbl, BorderLayout.NORTH);
        fila.add(campo, BorderLayout.CENTER);
        camposPanel.add(fila);
    }
    
    protected void agregarSeparador() {
        camposPanel.add(ComponentFactory.crearSeparadorAncho(420));
        camposPanel.add(Box.createVerticalStrut(8));
    }
    
    protected void agregarEspacio(int altura) {
        camposPanel.add(Box.createVerticalStrut(altura));
    }
    
    protected JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setOpaque(false);
        
        JButton btnGuardar = ProyectoTPI.presentacion.ui.ButtonFactory.crearBotonPrimario("Guardar");
        btnGuardar.addActionListener(e -> onGuardar());
        
        JButton btnLimpiar = ProyectoTPI.presentacion.ui.ButtonFactory.crearBotonSecundario("Limpiar");
        btnLimpiar.addActionListener(e -> onLimpiar());
        
        panel.add(btnLimpiar);
        panel.add(btnGuardar);
        return panel;
    }
    
    protected void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }
    
    protected abstract void construirFormulario();
    protected abstract void onGuardar();
    protected abstract void onLimpiar();
    protected abstract Dimension getDimensionesFormulario();
}