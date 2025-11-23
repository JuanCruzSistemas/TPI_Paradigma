package ProyectoTPI.presentacion;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ProyectoTPI.gestores.GestorCuenta;
import ProyectoTPI.gestores.GestorUsuario;
import ProyectoTPI.presentacion.panels.*;
import ProyectoTPI.presentacion.ui.BackgroundPanel;
import ProyectoTPI.presentacion.ui.ColorScheme;
import ProyectoTPI.presentacion.ui.SidebarPanel;




public class Principal extends JFrame {
    private GestorCuenta gestorCuenta;
    private GestorUsuario gestorUsuario;
    private JPanel panelContenido;
    private CardLayout cardLayout;
    
    public Principal(GestorUsuario gestorUsuario) {
        this.gestorCuenta = new GestorCuenta();
        this.gestorUsuario = gestorUsuario;
        
        configurarVentana();
        inicializarComponentes();
        setVisible(true);
    }
    
    public Principal() {
        this(null);
    }
    
    public void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(ColorScheme.NEGRO_FONDO);
        setContentPane(panelPrincipal);
        
        SidebarPanel sidebar = new SidebarPanel(this, gestorUsuario);
        sidebar.setNavigationListener(this::navegarA);
        panelPrincipal.add(sidebar, BorderLayout.WEST);
        
        cardLayout = new CardLayout();
        panelContenido = new BackgroundPanel(cardLayout);
        
        agregarPaneles();
        
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
    }
    
    private void configurarVentana() {
        setTitle("Gestor de Cuentas - UTN FRVM");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 800));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void agregarPaneles() {
        panelContenido.add(new PanelBienvenidaPanel(), "inicio");
        
        FormularioCuentaPanel formCuenta = new FormularioCuentaPanel(gestorCuenta);
        formCuenta.setOnCuentaRegistrada(this::actualizarPanelConsultaCuenta);
        panelContenido.add(formCuenta, "formCuenta");
        
        panelContenido.add(new FormularioVehiculoPanel(gestorCuenta), "formVehiculo");
        
        panelContenido.add(new PanelConsultaCuenta(gestorCuenta), "consulta");

        panelContenido.add(new PanelConsultaVehiculos(gestorCuenta), "consultaVehiculos");
    }
    
    private void navegarA(int index, String vista) {
        if (index == 3) {
            actualizarPanelConsultaCuenta();
        }
        if (index == 4) {
            actualizarPanelConsultaVehiculos();
        }
        
        cardLayout.show(panelContenido, vista);
    }
    
    private void actualizarPanelConsultaCuenta() {
        Component[] components = panelContenido.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof PanelConsultaCuenta) {
                panelContenido.remove(i);
                panelContenido.add(new PanelConsultaCuenta(gestorCuenta), "consulta", i);
                break;
            }
        }
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private void actualizarPanelConsultaVehiculos() {
        Component[] components = panelContenido.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof PanelConsultaVehiculos) {
                panelContenido.remove(i);
                panelContenido.add(new PanelConsultaVehiculos(gestorCuenta), "consultaVehiculos", i);
                break;
            }
        }
        panelContenido.revalidate();
        panelContenido.repaint();
    }
}