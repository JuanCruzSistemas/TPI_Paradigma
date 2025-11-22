package ProyectoTPI.presentacion.ui;

import ProyectoTPI.gestores.GestorUsuario;
import ProyectoTPI.presentacion.Login;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class SidebarPanel extends JPanel {
    private JPanel[] botonesMenu = new JPanel[5];
    private int botonSeleccionado = 0;
    private NavigationListener navigationListener;
    private JFrame parentFrame;
    private GestorUsuario gestorUsuario;
    
    public interface NavigationListener {
        void onNavigate(int index, String vista);
    }
    
    public SidebarPanel(JFrame parentFrame, GestorUsuario gestorUsuario) {
        this.parentFrame = parentFrame;
        this.gestorUsuario = gestorUsuario;
        inicializar();
    }
    
    public void setNavigationListener(NavigationListener listener) {
        this.navigationListener = listener;
    }
    
    private void inicializar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ColorScheme.NEGRO_PANEL);
        setPreferredSize(new Dimension(280, 0));
        setBorder(new MatteBorder(0, 0, 0, 1, ColorScheme.GRIS_OSCURO));
        
        add(crearLogoPanel());
        add(ComponentFactory.crearSeparador());
        add(Box.createVerticalStrut(15));
        
        agregarBotonesMenu();
        
        add(Box.createVerticalGlue());
        add(ComponentFactory.crearSeparador());
        add(Box.createVerticalStrut(10));
        add(crearBotonCerrarSesion());
        add(Box.createVerticalStrut(20));
    }
    
    private JPanel crearLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBackground(ColorScheme.NEGRO_PANEL);
        logoPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
        logoPanel.setMaximumSize(new Dimension(280, 120));
        
        JLabel lblEmoji = ComponentFactory.crearEtiqueta("UTN", 28, true, ColorScheme.AMARILLO);
        lblEmoji.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoPanel.add(lblEmoji);
        logoPanel.add(Box.createVerticalStrut(10));
        
        JLabel lblTitulo = ComponentFactory.crearEtiqueta("UTN FRVM", 22, true, ColorScheme.AMARILLO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoPanel.add(lblTitulo);
        
        JLabel lblSub = ComponentFactory.crearEtiqueta("Gestor de Cuentas", 13, false, ColorScheme.GRIS_TEXTO);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoPanel.add(lblSub);
        
        return logoPanel;
    }
    
    private void agregarBotonesMenu() {
        String[][] opciones = {
            {"~", "Inicio"},
            {"+", "Registrar Cuenta"},
            {"+", "Registrar Vehiculo"},
            {"?", "Consultar Cuentas"},
            {"?", "Consultar Vehiculos"}  // ← AGREGAR ESTA LÍNEA
        };
        String[] vistas = {"inicio", "formCuenta", "formVehiculo", "consulta", "consultaVehiculos"};
        
        for (int i = 0; i < opciones.length; i++) {
            final int index = i;
            JPanel btn = crearBotonSidebar(opciones[i][0], opciones[i][1]);
            botonesMenu[i] = btn;
            btn.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    seleccionarBoton(index);
                    if (navigationListener != null) {
                        navigationListener.onNavigate(index, vistas[index]);
                    }
                }
            });
            add(btn);
            add(Box.createVerticalStrut(5));
        }
    }
    
    private void seleccionarBoton(int index) {
        botonSeleccionado = index;
        for (int i = 0; i < botonesMenu.length; i++) {
            botonesMenu[i].repaint();
        }
    }
    
    private JPanel crearBotonSidebar(String emoji, String texto) {
        JPanel btn = new JPanel(new BorderLayout(15, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                boolean esSeleccionado = false;
                for (int i = 0; i < botonesMenu.length; i++) {
                    if (botonesMenu[i] == this && botonSeleccionado == i) {
                        esSeleccionado = true;
                        break;
                    }
                }
                
                if (esSeleccionado) {
                    g2d.setColor(ColorScheme.AMARILLO_TRANSPARENTE);
                    g2d.fillRoundRect(5, 0, getWidth() - 10, getHeight(), 10, 10);
                    g2d.setColor(ColorScheme.AMARILLO);
                    g2d.fillRoundRect(0, 5, 4, getHeight() - 10, 2, 2);
                } else if (getMousePosition() != null) {
                    g2d.setColor(ColorScheme.BLANCO_TRANSPARENTE);
                    g2d.fillRoundRect(5, 0, getWidth() - 10, getHeight(), 10, 10);
                }
            }
        };
        btn.setOpaque(false);
        btn.setBorder(new EmptyBorder(15, 25, 15, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(280, 55));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.repaint(); }
            public void mouseExited(MouseEvent e) { btn.repaint(); }
        });
        
        JLabel lblEmoji = ComponentFactory.crearEtiqueta(emoji, 20, true, ColorScheme.AMARILLO);
        btn.add(lblEmoji, BorderLayout.WEST);
        
        JLabel lblTexto = ComponentFactory.crearEtiqueta(texto, 15, false, Color.WHITE);
        btn.add(lblTexto, BorderLayout.CENTER);
        
        return btn;
    }
    
    private JPanel crearBotonCerrarSesion() {
        JPanel btnCerrar = crearBotonSidebar("X", "Cerrar Sesion");
        btnCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "¿Está seguro que desea cerrar sesión?",
                    "Cerrar Sesión",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    parentFrame.dispose();
                    if (gestorUsuario != null) {
                        new Login(gestorUsuario);
                    } else {
                        new Login(new GestorUsuario());
                    }
                }
            }
        });
        return btnCerrar;
    }
}