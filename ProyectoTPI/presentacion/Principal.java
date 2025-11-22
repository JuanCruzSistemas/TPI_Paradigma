package ProyectoTPI.presentacion;

import ProyectoTPI.gestores.GestorCuenta;
import ProyectoTPI.gestores.GestorUsuario;
import ProyectoTPI.presentacion.panels.*;
import ProyectoTPI.presentacion.ui.BackgroundPanel;
import ProyectoTPI.presentacion.ui.ColorScheme;
import ProyectoTPI.presentacion.ui.SidebarPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación.
 * Principio: Single Responsibility - Solo coordina los componentes principales.
 * Principio: Dependency Inversion - Depende de abstracciones (interfaces/clases base).
 */
public class Principal extends JFrame {
    private GestorCuenta gestorCuenta;
    private GestorUsuario gestorUsuario;
    private Login loginFrame;
    private JPanel panelContenido;
    private CardLayout cardLayout;
    
    public Principal(Login loginFrame, GestorUsuario gestorUsuario) {
        this.gestorCuenta = new GestorCuenta();
        this.loginFrame = loginFrame;
        this.gestorUsuario = gestorUsuario;
        
        configurarVentana();
        inicializarComponentes();
        setVisible(true);
    }
    
    public Principal() {
        this(null, null);
    }
    
    private void configurarVentana() {
        setTitle("Gestor de Cuentas - UTN FRVM");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 800));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    public void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(ColorScheme.NEGRO_FONDO);
        setContentPane(panelPrincipal);
        
        // Crear sidebar
        SidebarPanel sidebar = new SidebarPanel(this, gestorUsuario);
        sidebar.setNavigationListener(this::navegarA);
        panelPrincipal.add(sidebar, BorderLayout.WEST);
        
        // Crear panel de contenido con fondo
        cardLayout = new CardLayout();
        panelContenido = new BackgroundPanel(cardLayout);
        
        agregarPaneles();
        
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
    }
    
    private void agregarPaneles() {
        // Panel de bienvenida
        panelContenido.add(new PanelBienvenidaPanel(), "inicio");
        
        // Formulario de cuenta
        FormularioCuentaPanel formCuenta = new FormularioCuentaPanel(gestorCuenta);
        formCuenta.setOnCuentaRegistrada(this::actualizarPanelConsulta);
        panelContenido.add(formCuenta, "formCuenta");
        
        // Formulario de vehículo
        panelContenido.add(new FormularioVehiculoPanel(gestorCuenta), "formVehiculo");
        
        // Panel de consulta
        panelContenido.add(new PanelConsultaCuenta(gestorCuenta), "consulta");

        panelContenido.add(new PanelConsultaVehiculos(gestorCuenta), "consultaVehiculos");
    }
    
    private void navegarA(int index, String vista) {
        // Si es el panel de consulta, recrearlo para mostrar datos actualizados
        if (index == 3) {
            actualizarPanelConsulta();
        }
        if (index == 4) {
            actualizarPanelConsultaVehiculos();
        }
        
        cardLayout.show(panelContenido, vista);
    }
    
    private void actualizarPanelConsulta() {
        // Remover el panel de consulta antiguo y crear uno nuevo
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



// package ProyectoTPI.presentacion;

// import ProyectoTPI.datos.FileManager;
// import ProyectoTPI.gestores.GestorCuenta;
// import ProyectoTPI.gestores.GestorUsuario;
// import ProyectoTPI.recursos.Mensajes;
// import ProyectoTPI.recursos.Rutas;

// import javax.swing.*;
// import javax.swing.border.*;
// import java.awt.*;
// import java.awt.event.*;

// public class Principal extends JFrame {
//     private GestorCuenta gestorCuenta;
//     private GestorUsuario gestorUsuario;
//     private Login loginFrame;
//     private JPanel panelContenido;
//     private CardLayout cardLayout;
//     private Image imagenFondo;
    
//     private final Color NEGRO_FONDO = new Color(20, 20, 20);
//     private final Color NEGRO_PANEL = new Color(30, 30, 30);
//     private final Color NEGRO_INPUT = new Color(40, 40, 40);
//     private final Color AMARILLO = new Color(255, 193, 7);
//     private final Color AMARILLO_CLARO = new Color(255, 215, 0);
//     private final Color GRIS_TEXTO = new Color(200, 200, 200);
    
//     private JPanel[] botonesMenu = new JPanel[4];
//     private int botonSeleccionado = 0;

//     public Principal(Login loginFrame, GestorUsuario gestorUsuario) {
//         this.gestorCuenta = new GestorCuenta();
//         this.loginFrame = loginFrame;
//         this.gestorUsuario = gestorUsuario;
//         cargarImagenFondo();
//         setTitle("Gestor de Cuentas - UTN FRVM");
//         setExtendedState(JFrame.MAXIMIZED_BOTH);
//         setMinimumSize(new Dimension(1200, 800));
//         setDefaultCloseOperation(EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);
//         inicializarComponentes();
//         setVisible(true);
//     }
    
//     public Principal() {
//         this(null, null);
//     }

//     private void cargarImagenFondo() {
//         try {
//             ImageIcon icon = new ImageIcon("ProyectoTPI/recursos/imagenes/fondo_utn.jpg");
//             if (icon.getIconWidth() > 0) {
//                 imagenFondo = icon.getImage();
//             }
//         } catch (Exception e) {
//             System.err.println("Error al cargar imagen de fondo: " + e.getMessage());
//         }
//     }

//     public void inicializarComponentes() {
//         JPanel panelPrincipal = new JPanel(new BorderLayout());
//         panelPrincipal.setBackground(NEGRO_FONDO);
//         setContentPane(panelPrincipal);
//         panelPrincipal.add(crearSidebar(), BorderLayout.WEST);

//         cardLayout = new CardLayout();
//         panelContenido = new JPanel(cardLayout) {
//             @Override
//             protected void paintComponent(Graphics g) {
//                 super.paintComponent(g);
//                 Graphics2D g2d = (Graphics2D) g;
//                 g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
//                                      RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                
//                 if (imagenFondo != null) {
//                     int imgW = imagenFondo.getWidth(this);
//                     int imgH = imagenFondo.getHeight(this);
//                     double scale = Math.max((double) getWidth() / imgW, (double) getHeight() / imgH);
//                     int scaledW = (int) (imgW * scale);
//                     int scaledH = (int) (imgH * scale);
//                     int x = (getWidth() - scaledW) / 2;
//                     int y = (getHeight() - scaledH) / 2;
//                     g2d.drawImage(imagenFondo, x, y, scaledW, scaledH, this);
//                     g2d.setColor(new Color(0, 0, 0, 180));
//                     g2d.fillRect(0, 0, getWidth(), getHeight());
//                 } else {
//                     g2d.setColor(NEGRO_FONDO);
//                     g2d.fillRect(0, 0, getWidth(), getHeight());
//                 }
//             }
//         };
//         panelContenido.setOpaque(false);
        
//         panelContenido.add(crearPanelBienvenida(), "inicio");
//         panelContenido.add(crearFormularioCuenta(), "formCuenta");
//         panelContenido.add(crearFormularioVehiculo(), "formVehiculo");
//         panelContenido.add(crearPanelConsulta(), "consulta");
        
//         panelPrincipal.add(panelContenido, BorderLayout.CENTER);
//     }

//     private JPanel crearSidebar() {
//         JPanel sidebar = new JPanel();
//         sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
//         sidebar.setBackground(NEGRO_PANEL);
//         sidebar.setPreferredSize(new Dimension(280, 0));
//         sidebar.setBorder(new MatteBorder(0, 0, 0, 1, new Color(50, 50, 50)));

//         JPanel logoPanel = new JPanel();
//         logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
//         logoPanel.setBackground(NEGRO_PANEL);
//         logoPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
//         logoPanel.setMaximumSize(new Dimension(280, 120));
        
//         JLabel lblEmoji = new JLabel("UTN");
//         lblEmoji.setFont(new Font("Segoe UI", Font.BOLD, 28));
//         lblEmoji.setForeground(AMARILLO);
//         lblEmoji.setAlignmentX(Component.CENTER_ALIGNMENT);
//         logoPanel.add(lblEmoji);
//         logoPanel.add(Box.createVerticalStrut(10));
        
//         JLabel lblTitulo = new JLabel("UTN FRVM");
//         lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
//         lblTitulo.setForeground(AMARILLO);
//         lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
//         logoPanel.add(lblTitulo);
        
//         JLabel lblSub = new JLabel("Gestor de Cuentas");
//         lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
//         lblSub.setForeground(GRIS_TEXTO);
//         lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
//         logoPanel.add(lblSub);
//         sidebar.add(logoPanel);
//         sidebar.add(crearSeparador());
//         sidebar.add(Box.createVerticalStrut(15));

//         String[][] opciones = {
//             {"~", "Inicio"},
//             {"+", "Registrar Cuenta"},
//             {"+", "Registrar Vehiculo"},
//             {"?", "Consultar Cuentas"}
//         };
//         String[] vistas = {"inicio", "formCuenta", "formVehiculo", "consulta"};
        

//         for (int i = 0; i < opciones.length; i++) {
//             final int index = i;
//             JPanel btn = crearBotonSidebar(opciones[i][0], opciones[i][1], i == 0);
//             botonesMenu[i] = btn;
//             btn.addMouseListener(new MouseAdapter() {
//                 public void mouseClicked(MouseEvent e) {
//                     seleccionarBoton(index);
        
//                     // Si es el panel de consulta (index 3), recrearlo
//                     if (index == 3) {
//                         panelContenido.remove(3);
//                         panelContenido.add(crearPanelConsulta(), "consulta", 3);
//                         panelContenido.revalidate();
//                         panelContenido.repaint();
//                     }
        
//                     cardLayout.show(panelContenido, vistas[index]);
//                 }
//             });
//             sidebar.add(btn);
//             sidebar.add(Box.createVerticalStrut(5));
//         }

//         sidebar.add(Box.createVerticalGlue());
//         sidebar.add(crearSeparador());
//         sidebar.add(Box.createVerticalStrut(10));

//         JPanel btnCerrar = crearBotonSidebar("X", "Cerrar Sesion", false);
//         btnCerrar.addMouseListener(new MouseAdapter() {
//             public void mouseClicked(MouseEvent e) {
//                 int confirm = JOptionPane.showConfirmDialog(
//                     Principal.this,
//                     "¿Está seguro que desea cerrar sesión?",
//                     "Cerrar Sesión",
//                     JOptionPane.YES_NO_OPTION
//                 );
//                 if (confirm == JOptionPane.YES_OPTION) {
//                     dispose();
//                     if (gestorUsuario != null) {
//                         new Login(gestorUsuario);
//                     } else {
//                         new Login(new GestorUsuario());
//                     }
//                 }
//             }
//         });
//         sidebar.add(btnCerrar);
//         sidebar.add(Box.createVerticalStrut(20));
//         return sidebar;
//     }
    
//     private void seleccionarBoton(int index) {
//         botonSeleccionado = index;
//         for (int i = 0; i < botonesMenu.length; i++) {
//             botonesMenu[i].repaint();
//         }
//     }

//     private JPanel crearBotonSidebar(String emoji, String texto, boolean seleccionado) {
//         JPanel btn = new JPanel(new BorderLayout(15, 0)) {
//             @Override
//             protected void paintComponent(Graphics g) {
//                 Graphics2D g2d = (Graphics2D) g;
//                 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
//                 boolean esSeleccionado = false;
//                 for (int i = 0; i < botonesMenu.length; i++) {
//                     if (botonesMenu[i] == this && botonSeleccionado == i) {
//                         esSeleccionado = true;
//                         break;
//                     }
//                 }
                
//                 if (esSeleccionado) {
//                     g2d.setColor(new Color(255, 193, 7, 30));
//                     g2d.fillRoundRect(5, 0, getWidth() - 10, getHeight(), 10, 10);
//                     g2d.setColor(AMARILLO);
//                     g2d.fillRoundRect(0, 5, 4, getHeight() - 10, 2, 2);
//                 } else if (getMousePosition() != null) {
//                     g2d.setColor(new Color(255, 255, 255, 15));
//                     g2d.fillRoundRect(5, 0, getWidth() - 10, getHeight(), 10, 10);
//                 }
//             }
//         };
//         btn.setOpaque(false);
//         btn.setBorder(new EmptyBorder(15, 25, 15, 20));
//         btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//         btn.setMaximumSize(new Dimension(280, 55));
//         btn.addMouseListener(new MouseAdapter() {
//             public void mouseEntered(MouseEvent e) { btn.repaint(); }
//             public void mouseExited(MouseEvent e) { btn.repaint(); }
//         });

//         JLabel lblEmoji = new JLabel(emoji);
//         lblEmoji.setFont(new Font("Segoe UI", Font.BOLD, 20));
//         lblEmoji.setForeground(AMARILLO);
//         btn.add(lblEmoji, BorderLayout.WEST);

//         JLabel lblTexto = new JLabel(texto);
//         lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
//         lblTexto.setForeground(Color.WHITE);
//         btn.add(lblTexto, BorderLayout.CENTER);
//         return btn;
//     }
    
//     private JSeparator crearSeparador() {
//         JSeparator sep = new JSeparator();
//         sep.setForeground(new Color(60, 60, 60));
//         sep.setMaximumSize(new Dimension(280, 1));
//         return sep;
//     }

//     private JPanel crearPanelBienvenida() {
//         JPanel panel = new JPanel(new GridBagLayout());
//         panel.setOpaque(false);
        
//         JPanel contenedor = new JPanel();
//         contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
//         contenedor.setOpaque(false);
        
//         JLabel lblBienvenida = new JLabel("Bienvenido al Sistema");
//         lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 42));
//         lblBienvenida.setForeground(Color.WHITE);
//         lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
//         contenedor.add(lblBienvenida);
//         contenedor.add(Box.createVerticalStrut(15));
        
//         JLabel lblDesc = new JLabel("Seleccione una opción del menú lateral para comenzar");
//         lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 18));
//         lblDesc.setForeground(GRIS_TEXTO);
//         lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
//         contenedor.add(lblDesc);
//         panel.add(contenedor);
//         return panel;
//     }

//     private JPanel crearFormularioCuenta() {
//         JPanel panel = new JPanel(new GridBagLayout());
//         panel.setOpaque(false);
        
//         JPanel form = crearPanelFormulario("+ Registrar Nueva Cuenta");
//         form.setPreferredSize(new Dimension(520, 720));
        
//         JTextField txtLegajoBuscar = crearCampoTexto();
//         JButton btnBuscar = crearBotonPrimario("Buscar");
//         btnBuscar.setPreferredSize(new Dimension(110, 38));
        
//         JTextField txtNombre = crearCampoTextoSoloLectura();
//         JTextField txtApellido = crearCampoTextoSoloLectura();
//         JTextField txtTipoDoc = crearCampoTextoSoloLectura();
//         JTextField txtNroDoc = crearCampoTextoSoloLectura();
//         JTextField txtRol = crearCampoTextoSoloLectura();
        
//         JPanel camposPanel = (JPanel) form.getComponent(1);
        
//         JPanel panelBusqueda = new JPanel(new BorderLayout(10, 0));
//         panelBusqueda.setOpaque(false);
//         panelBusqueda.setMaximumSize(new Dimension(350, 15));
//         panelBusqueda.setBorder(new EmptyBorder(0, 0, 10, 0));
        
//         JLabel lblBuscar = new JLabel("Ingrese Legajo");
//         lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
//         lblBuscar.setForeground(AMARILLO);


//         JPanel inputBusqueda = new JPanel();
//         inputBusqueda.setLayout(new BoxLayout(inputBusqueda, BoxLayout.X_AXIS));
//         inputBusqueda.setOpaque(false);

//         txtLegajoBuscar.setMaximumSize(new Dimension(300, 38));
//         txtLegajoBuscar.setPreferredSize(new Dimension(300, 38));
//         btnBuscar.setMaximumSize(new Dimension(110, 38));
//         btnBuscar.setPreferredSize(new Dimension(110, 38));

//         inputBusqueda.add(txtLegajoBuscar);
//         inputBusqueda.add(Box.createHorizontalStrut(10));
//         inputBusqueda.add(btnBuscar);
        
//         JPanel busquedaContainer = new JPanel(new BorderLayout(0, 5));
//         busquedaContainer.setOpaque(false);
//         busquedaContainer.add(lblBuscar, BorderLayout.NORTH);
//         busquedaContainer.add(inputBusqueda, BorderLayout.CENTER);
        
//         camposPanel.add(busquedaContainer);
//         camposPanel.add(Box.createVerticalStrut(8));
        
//         JSeparator sep = new JSeparator();
//         sep.setForeground(new Color(60, 60, 60));
//         sep.setMaximumSize(new Dimension(420, 1));
//         camposPanel.add(sep);
//         camposPanel.add(Box.createVerticalStrut(8));
        
//         agregarCampo(camposPanel, "Nombre", txtNombre);
//         agregarCampo(camposPanel, "Apellido", txtApellido);
//         agregarCampo(camposPanel, "Tipo Documento", txtTipoDoc);
//         agregarCampo(camposPanel, "Nro. Documento", txtNroDoc);
//         agregarCampo(camposPanel, "Rol UTN", txtRol);
        
//         JSeparator sep2 = new JSeparator();
//         sep2.setForeground(new Color(60, 60, 60));
//         sep2.setMaximumSize(new Dimension(420, 1));
//         camposPanel.add(sep2);
//         camposPanel.add(Box.createVerticalStrut(8));
        
//         btnBuscar.addActionListener(e -> {
//             String legajo = txtLegajoBuscar.getText().trim();
//             if (legajo.isEmpty()) {
//                 Mensajes.mostrarError("Ingrese un legajo para buscar.");
//                 return;
//             }
            
//             String[] datos = buscarPersonaEnCSV(legajo);
//             if (datos != null) {
//                 txtNombre.setText(datos[0]);
//                 txtApellido.setText(datos[1]);
//                 txtTipoDoc.setText(datos[3]);
//                 txtNroDoc.setText(datos[4]);
//                 txtRol.setText(datos[5]);
//             } else {
//                 Mensajes.mostrarError("No se encontró una persona con el legajo: " + legajo);
//                 txtNombre.setText("");
//                 txtApellido.setText("");
//                 txtTipoDoc.setText("");
//                 txtNroDoc.setText("");
//                 txtRol.setText("");
//             }
//         });
        
//         txtLegajoBuscar.addKeyListener(new KeyAdapter() {
//             public void keyPressed(KeyEvent e) {
//                 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                     btnBuscar.doClick();
//                 }
//             }
//         });
        
//         JPanel btnPanel = crearPanelBotones(
//             () -> {
//                 if (txtNombre.getText().trim().isEmpty()) {
//                     Mensajes.mostrarError("Primero busque un legajo válido.");
//                     return;
//                 }

//                 java.util.List<String> datosCuenta = java.util.Arrays.asList(
//                     txtNombre.getText().trim(),
//                     txtApellido.getText().trim(),
//                     txtLegajoBuscar.getText().trim(),
//                     txtTipoDoc.getText().trim(),
//                     txtNroDoc.getText().trim(),
//                     txtRol.getText().trim()
//                 );

//                 gestorCuenta.registrarCuenta(datosCuenta);

//                 Mensajes.mostrarExito("Cuenta registrada exitosamente!");

//                 Component[] components = panelContenido.getComponents();
//                 for (int i = 0; i < components.length; i++) {
//                     if (panelContenido.getLayout() instanceof CardLayout) {
//                         panelContenido.remove(3);
//                         panelContenido.add(crearPanelConsulta(), "consulta", 3);
//                         break;
//                     }
//                 }
//                 panelContenido.revalidate();
//                 panelContenido.repaint();

//                 txtLegajoBuscar.setText("");
//                 txtNombre.setText("");
//                 txtApellido.setText("");
//                 txtTipoDoc.setText("");
//                 txtNroDoc.setText("");
//                 txtRol.setText("");
//             },
//             () -> {
//                 txtLegajoBuscar.setText("");
//                 txtNombre.setText("");
//                 txtApellido.setText("");
//                 txtTipoDoc.setText("");
//                 txtNroDoc.setText("");
//                 txtRol.setText("");
//             }
//         );
//         form.add(btnPanel, BorderLayout.SOUTH);
//         panel.add(form);
//         return panel;
//     }
    
//     private String[] buscarPersonaEnCSV(String legajo) {
//         FileManager archivoPersonas = new FileManager(Rutas.RUTA_PERSONAS);
//         java.util.List<String> lineas = archivoPersonas.leerArchivo();
        
//         String[] personas = lineas.stream().map(s -> s.split(";"))
//                                            .filter(s -> s[2].equals(legajo))
//                                            .findFirst()
//                                            .orElse(null);
//         return personas;
//     }
    
//     private JTextField crearCampoTextoSoloLectura() {
//         JTextField txt = crearCampoTexto();
//         txt.setEditable(false);
//         txt.setBackground(new Color(30, 30, 30));
//         txt.setForeground(GRIS_TEXTO);
//         return txt;
//     }

//     private JPanel crearFormularioVehiculo() {
//         JPanel panel = new JPanel(new GridBagLayout());
//         panel.setOpaque(false);
        
//         JPanel form = crearPanelFormulario("+ Registrar Nuevo Vehículo");
        
//         JTextField txtModelo = crearCampoTexto();
//         JTextField txtMarca = crearCampoTexto();
//         JTextField txtPatente = crearCampoTexto();
//         JComboBox<String> cmbColor = crearComboBox(new String[] {"Blanco", "Negro", "Gris", "Rojo", "Azul", "Verde", "Otro"});
//         JTextField txtLegajoCuenta = crearCampoTexto();
        
//         JPanel camposPanel = (JPanel) form.getComponent(1);
//         agregarCampo(camposPanel, "Modelo", txtModelo);
//         agregarCampo(camposPanel, "Marca", txtMarca);
//         agregarCampo(camposPanel, "Patente", txtPatente);
//         agregarCampo(camposPanel, "Color", cmbColor);
//         agregarCampo(camposPanel, "Legajo de la Cuenta", txtLegajoCuenta);
        
//         JPanel btnPanel = crearPanelBotones(
//             () -> {
//                 if (txtModelo.getText().trim().isEmpty() || txtMarca.getText().trim().isEmpty() 
//                     || txtPatente.getText().trim().isEmpty() || txtLegajoCuenta.getText().trim().isEmpty()) {
//                     Mensajes.mostrarError("Por favor, complete todos los campos obligatorios.");
//                     return;
//                 }
//                 java.util.List<String> datosVehiculo = java.util.Arrays.asList(
//                     txtModelo.getText().trim(),
//                     txtMarca.getText().trim(),
//                     txtPatente.getText().trim(),
//                     (String) cmbColor.getSelectedItem(),
//                     txtLegajoCuenta.getText().trim()
//                 );

//                 gestorCuenta.registrarVehiculo(datosVehiculo);
//                 Mensajes.mostrarExito("Vehículo registrado exitosamente!");
//                 limpiarCampos(txtModelo, txtMarca, txtPatente, txtLegajoCuenta);
//             },
//             () -> limpiarCampos(txtModelo, txtMarca, txtPatente, txtLegajoCuenta)
//         );
//         form.add(btnPanel, BorderLayout.SOUTH);
//         panel.add(form);
//         return panel;
//     }

//     private JPanel crearPanelConsulta() {
//         JPanel panel = new JPanel(new GridBagLayout());
//         panel.setOpaque(false);
        
//         JPanel contenedor = new JPanel(new BorderLayout(0, 20));
//         contenedor.setBackground(new Color(30, 30, 30, 240));
//         contenedor.setBorder(new CompoundBorder(
//             new LineBorder(new Color(60, 60, 60), 1, true),
//             new EmptyBorder(30, 40, 30, 40)
//         ));
//         contenedor.setPreferredSize(new Dimension(900, 550));
        
//         JLabel titulo = new JLabel("? Consultar Cuentas");
//         titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
//         titulo.setForeground(AMARILLO);
//         contenedor.add(titulo, BorderLayout.NORTH);
        
//         String[] columnas = {"Nombre", "Apellido", "Legajo", "Tipo documento", "Nro documento", "Fecha y hora creación", "Estado", "Rol", "Saldo"};

//         Object[][] datos = buscarCuentaEnCSV();
        
//         JTable tabla = new JTable(datos, columnas);
//         tabla.setBackground(NEGRO_INPUT);
//         tabla.setForeground(Color.WHITE);
//         tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//         tabla.setRowHeight(40);
//         tabla.setGridColor(new Color(60, 60, 60));
//         tabla.getTableHeader().setBackground(NEGRO_PANEL);
//         tabla.getTableHeader().setForeground(AMARILLO);
//         tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
//         tabla.setSelectionBackground(new Color(255, 193, 7, 50));
//         tabla.setSelectionForeground(Color.WHITE);
        
//         JScrollPane scroll = new JScrollPane(tabla);
//         scroll.setBackground(NEGRO_INPUT);
//         scroll.getViewport().setBackground(NEGRO_INPUT);
//         scroll.setBorder(new LineBorder(new Color(60, 60, 60)));
        
//         if (!gestorCuenta.tieneCuentas()) {
//             JPanel emptyPanel = new JPanel(new GridBagLayout());
//             emptyPanel.setOpaque(false);
//             JLabel lblVacio = new JLabel("No hay cuentas registradas aún");
//             lblVacio.setFont(new Font("Segoe UI", Font.ITALIC, 18));
//             lblVacio.setForeground(GRIS_TEXTO);
//             emptyPanel.add(lblVacio);
//             contenedor.add(emptyPanel, BorderLayout.CENTER);
//         } else {
//             contenedor.add(scroll, BorderLayout.CENTER);
//         }
        
//         panel.add(contenedor);
//         return panel;
//     }

//     private Object[][] buscarCuentaEnCSV() {
//         FileManager archivoCuenta = gestorCuenta.getArchivoCuentas();
//         java.util.List<String> lineas = archivoCuenta.leerArchivo();
//         Object[][] datos = lineas.stream().map(s -> s.split(";"))
//                                           .map(campos -> new Object[]{
//                                               campos[0],
//                                               campos[1],
//                                               campos[2],
//                                               campos[3],
//                                               campos[4],
//                                               campos[5],
//                                               campos[7],
//                                               campos[8],
//                                               campos[9]
//                                           })
//                                           .toArray(Object[][]::new);
//         return datos;
//     }

//     private JPanel crearPanelFormulario(String titulo) {
//         JPanel form = new JPanel(new BorderLayout(0, 20));
//         form.setBackground(new Color(30, 30, 30, 245));
//         form.setBorder(new CompoundBorder(
//             new LineBorder(new Color(60, 60, 60), 1, true),
//             new EmptyBorder(30, 50, 25, 50)
//         ));
//         form.setPreferredSize(new Dimension(520, 580));
        
//         JLabel lblTitulo = new JLabel(titulo);
//         lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
//         lblTitulo.setForeground(AMARILLO);
//         form.add(lblTitulo, BorderLayout.NORTH);
        
//         JPanel campos = new JPanel();
//         campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
//         campos.setOpaque(false);
//         form.add(campos, BorderLayout.CENTER);
//         return form;
//     }
    
//     private void agregarCampo(JPanel panel, String label, JComponent campo) {
//         JPanel fila = new JPanel(new BorderLayout(0, 5));
//         fila.setOpaque(false);
//         fila.setMaximumSize(new Dimension(420, 65));
//         fila.setBorder(new EmptyBorder(0, 0, 6, 0));
        
//         JLabel lbl = new JLabel(label);
//         lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
//         lbl.setForeground(AMARILLO);
//         fila.add(lbl, BorderLayout.NORTH);
//         fila.add(campo, BorderLayout.CENTER);
//         panel.add(fila);
//     }

//     private JTextField crearCampoTexto() {
//         JTextField txt = new JTextField();
//         txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//         txt.setBackground(NEGRO_INPUT);
//         txt.setForeground(Color.WHITE);
//         txt.setCaretColor(AMARILLO);
//         txt.setPreferredSize(new Dimension(420, 38));
//         txt.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(new Color(60, 60, 60), 2),
//             BorderFactory.createEmptyBorder(6, 12, 6, 12)
//         ));
        
//         txt.addFocusListener(new FocusAdapter() {
//             public void focusGained(FocusEvent e) {
//                 txt.setBorder(BorderFactory.createCompoundBorder(
//                     BorderFactory.createLineBorder(AMARILLO, 2),
//                     BorderFactory.createEmptyBorder(6, 12, 6, 12)
//                 ));
//             }
//             public void focusLost(FocusEvent e) {
//                 txt.setBorder(BorderFactory.createCompoundBorder(
//                     BorderFactory.createLineBorder(new Color(60, 60, 60), 2),
//                     BorderFactory.createEmptyBorder(6, 12, 6, 12)
//                 ));
//             }
//         });
//         return txt;
//     }

//     private JComboBox<String> crearComboBox(String[] items) {
//         JComboBox<String> combo = new JComboBox<>(items);
//         combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//         combo.setBackground(NEGRO_INPUT);
//         combo.setForeground(Color.WHITE);
//         combo.setPreferredSize(new Dimension(420, 38));
//         combo.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
//         return combo;
//     }

//     private JPanel crearPanelBotones(Runnable onGuardar, Runnable onLimpiar) {
//         JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
//         panel.setOpaque(false);
        
//         JButton btnGuardar = crearBotonPrimario("Guardar");
//         btnGuardar.addActionListener(e -> onGuardar.run());
        
//         JButton btnLimpiar = crearBotonSecundario("Limpiar");
//         btnLimpiar.addActionListener(e -> onLimpiar.run());
        
//         panel.add(btnLimpiar);
//         panel.add(btnGuardar);
//         return panel;
//     }

//     private JButton crearBotonPrimario(String texto) {
//         JButton btn = new JButton(texto) {
//             @Override
//             protected void paintComponent(Graphics g) {
//                 Graphics2D g2d = (Graphics2D) g;
//                 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
//                 if (getModel().isPressed()) {
//                     g2d.setColor(new Color(200, 150, 0));
//                 } else if (getModel().isRollover()) {
//                     g2d.setColor(AMARILLO_CLARO);
//                 } else {
//                     g2d.setColor(AMARILLO);
//                 }
//                 g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
//                 g2d.setColor(NEGRO_FONDO);
//                 g2d.setFont(getFont());
//                 FontMetrics fm = g2d.getFontMetrics();
//                 int x = (getWidth() - fm.stringWidth(getText())) / 2;
//                 int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
//                 g2d.drawString(getText(), x, y);
//             }
//         };
//         btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
//         btn.setPreferredSize(new Dimension(130, 40));
//         btn.setBorder(null);
//         btn.setContentAreaFilled(false);
//         btn.setFocusPainted(false);
//         btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//         return btn;
//     }

//     private JButton crearBotonSecundario(String texto) {
//         JButton btn = new JButton(texto) {
//             @Override
//             protected void paintComponent(Graphics g) {
//                 Graphics2D g2d = (Graphics2D) g;
//                 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
//                 g2d.setColor(getModel().isRollover() ? new Color(50, 50, 50) : NEGRO_INPUT);
//                 g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
//                 g2d.setColor(AMARILLO);
//                 g2d.setStroke(new BasicStroke(2));
//                 g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 10, 10);
                
//                 g2d.setFont(getFont());
//                 FontMetrics fm = g2d.getFontMetrics();
//                 int x = (getWidth() - fm.stringWidth(getText())) / 2;
//                 int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
//                 g2d.drawString(getText(), x, y);
//             }
//         };
//         btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
//         btn.setForeground(AMARILLO);
//         btn.setPreferredSize(new Dimension(130, 40));
//         btn.setBorder(null);
//         btn.setContentAreaFilled(false);
//         btn.setFocusPainted(false);
//         btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//         return btn;
//     }

//     private void limpiarCampos(JTextField... campos) {
//         for (JTextField campo : campos) campo.setText("");
//     }
// }