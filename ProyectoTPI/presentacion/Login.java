package ProyectoTPI.presentacion;

import ProyectoTPI.controladores.ControladorLogin;
import ProyectoTPI.gestores.GestorUsuario;
import ProyectoTPI.recursos.Mensajes;
import ProyectoTPI.user.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Login extends JFrame {
    private JTextField txtLegajo;
    private JPasswordField txtClave;
    private JButton btnIngresar;
    private ControladorLogin controladorLogin;
    private JPanel panelFondo;
    private Image imagenFondo;
    private Image logoUTN;
    private Point puntoInicial;

    public Login(GestorUsuario gestorUsuario) {
        this.controladorLogin = new ControladorLogin(gestorUsuario);
        cargarImagenes();

        setTitle("Login Usuario - UTN FRVM");
        setSize(650, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 650, 800, 30, 30));

        inicializarComponentes();
        agregarMovimientoVentana();
        setVisible(true);
    }
    
    public void inicializarComponentes() {
        // Panel principal con imagen de fondo escalada
        panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                                     RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                                     RenderingHints.VALUE_RENDER_QUALITY);
                
                if (imagenFondo != null) {
                    // Escalar imagen manteniendo proporción y cubriendo todo el panel
                    int imgW = imagenFondo.getWidth(this);
                    int imgH = imagenFondo.getHeight(this);
                    int panelW = getWidth();
                    int panelH = getHeight();
                    
                    double scale = Math.max((double) panelW / imgW, (double) panelH / imgH);
                    int scaledW = (int) (imgW * scale);
                    int scaledH = (int) (imgH * scale);
                    int x = (panelW - scaledW) / 2;
                    int y = (panelH - scaledH) / 2;
                    
                    g2d.drawImage(imagenFondo, x, y, scaledW, scaledH, this);
                    
                    // Overlay oscuro para legibilidad
                    g2d.setColor(new Color(0, 0, 0, 150));
                    g2d.fillRect(0, 0, panelW, panelH);
                } else {
                    // Gradiente fallback
                    GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(15, 15, 15),
                        0, getHeight(), new Color(35, 35, 35)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelFondo.setLayout(null);
        setContentPane(panelFondo);
        
        // Panel contenedor principal (tarjeta)
        JPanel panelContenedor = crearPanelContenedor();
        panelContenedor.setBounds(75, 100, 500, 600);
        panelFondo.add(panelContenedor);
        
        // Botón cerrar
        JButton btnCerrar = crearBotonCerrar();
        btnCerrar.setBounds(595, 15, 40, 40);
        panelFondo.add(btnCerrar);
        
        // Componentes del formulario
        agregarComponentesFormulario(panelContenedor);
    }
    
    private void cargarImagenes() {
        try {
            // Cargar imagen de fondo (1800x958 o cualquier tamaño)
            ImageIcon iconFondo = new ImageIcon("ProyectoTPI/recursos/imagenes/fondo_utn.jpg");
            if (iconFondo.getIconWidth() > 0) {
                imagenFondo = iconFondo.getImage();
            }
            
            // Cargar logo
            ImageIcon iconLogo = new ImageIcon("ProyectoTPI/recursos/imagenes/logo_utn.png");
            if (iconLogo.getIconWidth() > 0) {
                logoUTN = iconLogo.getImage();
            }
        } catch (Exception e) {
            System.err.println("Error al cargar imágenes: " + e.getMessage());
        }
    }

    private void agregarMovimientoVentana() {
        // Permite arrastrar la ventana sin bordes
        panelFondo.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                puntoInicial = e.getPoint();
            }
        });
        panelFondo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = getLocation().x + e.getX() - puntoInicial.x;
                int y = getLocation().y + e.getY() - puntoInicial.y;
                setLocation(x, y);
            }
        });
    }
    
    private JPanel crearPanelContenedor() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                     RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Sombra amarilla difusa
                for (int i = 0; i < 15; i++) {
                    g2d.setColor(new Color(255, 193, 7, 10 - i / 2));
                    g2d.fillRoundRect(i, i, getWidth() - i * 2, getHeight() - i * 2, 30, 30);
                }
                
                // Fondo negro de la tarjeta
                g2d.setColor(new Color(20, 20, 20, 240));
                g2d.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 25, 25);
                
                // Borde sutil amarillo
                g2d.setColor(new Color(255, 193, 7, 80));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(0, 0, getWidth() - 11, getHeight() - 11, 25, 25);
            }
        };
    }

    private JButton crearBotonCerrar() {
        JButton btn = new JButton("×") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                     RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isRollover()) {
                    g2d.setColor(new Color(220, 53, 69));
                } else {
                    g2d.setColor(new Color(40, 40, 40, 200));
                }
                g2d.fillOval(0, 0, getWidth(), getHeight());
                
                g2d.setColor(getModel().isRollover() ? Color.WHITE : new Color(255, 215, 0));
                g2d.setFont(new Font("Arial", Font.BOLD, 22));
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth("×")) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString("×", x, y);
            }
        };
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> System.exit(0));
        return btn;
    }

    private void agregarComponentesFormulario(JPanel panel) {
        panel.setLayout(null);
        panel.setOpaque(false);

        // Título
        JLabel lblTitulo = new JLabel("INICIAR SESIÓN");
        lblTitulo.setBounds(0, 40, 490, 45);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("UTN - Facultad Regional Villa María");
        lblSubtitulo.setBounds(0, 85, 490, 25);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(180, 180, 180));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblSubtitulo);

        // Logo o ícono
        JLabel lblLogo = crearLabelLogo();
        panel.add(lblLogo);

        // Campo Legajo
        JLabel lblLegajo = crearLabel("Legajo", 320);
        panel.add(lblLegajo);
        
        txtLegajo = crearTextField();
        txtLegajo.setBounds(70, 350, 360, 50);
        panel.add(txtLegajo);

        // Campo Contraseña
        JLabel lblClave = crearLabel("Contraseña", 415);
        panel.add(lblClave);
        
        txtClave = crearPasswordField();
        txtClave.setBounds(70, 445, 360, 50);
        panel.add(txtClave);

        // Botón Ingresar
        btnIngresar = crearBotonIngresar();
        panel.add(btnIngresar);

        // Enter para iniciar sesión
        txtClave.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) validarLogin();
            }
        });
    }

    private JLabel crearLabelLogo() {
        JLabel lbl = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                                     RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                     RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (logoUTN != null) {
                    int imgW = logoUTN.getWidth(this);
                    int imgH = logoUTN.getHeight(this);
                    
                    if (imgW > 0 && imgH > 0) {
                        // Área máxima disponible para el logo
                        int maxW = 200;
                        int maxH = 150;
                        
                        // Calcular escala manteniendo proporción
                        double scale = Math.min((double) maxW / imgW, (double) maxH / imgH);
                        int scaledW = (int) (imgW * scale);
                        int scaledH = (int) (imgH * scale);
                        
                        int x = (getWidth() - scaledW) / 2;
                        int y = (getHeight() - scaledH) / 2;
                        
                        // Círculo de fondo (opcional, ajustado al tamaño)
                        int circleSize = Math.max(scaledW, scaledH) + 30;
                        int cx = (getWidth() - circleSize) / 2;
                        int cy = (getHeight() - circleSize) / 2;
                        g2d.setColor(new Color(255, 215, 0, 25));
                        g2d.fillOval(cx, cy, circleSize, circleSize);
                        
                        g2d.drawImage(logoUTN, x, y, scaledW, scaledH, this);
                    }
                } else {
                    // Fallback emoji
                    int size = 110;
                    int x = (getWidth() - size) / 2;
                    int y = (getHeight() - size) / 2;
                    
                    g2d.setColor(new Color(255, 215, 0, 40));
                    g2d.fillOval(x - 12, y - 12, size + 24, size + 24);
                    g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 55));
                    FontMetrics fm = g2d.getFontMetrics();
                    String emoji = "🎓";
                    int ex = (getWidth() - fm.stringWidth(emoji)) / 2;
                    int ey = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                    g2d.drawString(emoji, ex, ey);
                }
            }
        };
        lbl.setBounds(0, 120, 490, 180);
        return lbl;
    }

    private JLabel crearLabel(String texto, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(70, y, 360, 25);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(new Color(255, 215, 0));
        return lbl;
    }

    private JTextField crearTextField() {
        JTextField txt = new JTextField();
        estilizarCampo(txt);
        return txt;
    }

    private JPasswordField crearPasswordField() {
        JPasswordField txt = new JPasswordField();
        estilizarCampo(txt);
        return txt;
    }

    private void estilizarCampo(JTextField txt) {
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txt.setBackground(new Color(35, 35, 35));
        txt.setForeground(Color.WHITE);
        txt.setCaretColor(new Color(255, 215, 0));
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60), 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        // Efecto focus
        txt.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 193, 7), 2),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
            public void focusLost(FocusEvent e) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(60, 60, 60), 2),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
        });
    }

    private JButton crearBotonIngresar() {
        JButton btn = new JButton("INGRESAR") {
            private boolean hover = false;
            private boolean pressed = false;
            
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                    public void mouseExited(MouseEvent e) { hover = false; repaint(); }
                    public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
                    public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
                });
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                     RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color colorBase = new Color(255, 193, 7);
                Color colorHover = new Color(255, 215, 0);
                Color colorPressed = new Color(230, 170, 0);
                
                // Sombra
                if (!pressed) {
                    g2d.setColor(new Color(255, 193, 7, 60));
                    g2d.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, 15, 15);
                }
                
                // Fondo del botón
                if (pressed) {
                    g2d.setColor(colorPressed);
                } else if (hover) {
                    g2d.setColor(colorHover);
                } else {
                    g2d.setColor(colorBase);
                }
                g2d.fillRoundRect(0, pressed ? 2 : 0, getWidth(), getHeight() - 4, 15, 15);
                
                // Texto
                g2d.setColor(new Color(20, 20, 20));
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - 4 - fm.getHeight()) / 2) + fm.getAscent() + (pressed ? 2 : 0);
                g2d.drawString(getText(), x, y);
            }
        };
        btn.setBounds(115, 520, 270, 55);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> validarLogin());
        return btn;
    }

    private void validarLogin() {
        String legajo = txtLegajo.getText().trim();
        String clave = new String(txtClave.getPassword());

        if (legajo.isEmpty() || clave.isEmpty()) {
            Mensajes.mensajeCompletarCampos();
            return;
        }

        Usuario usuario = this.controladorLogin.validarUsuario(legajo, clave);

        if (usuario == null) {
            Mensajes.mensajeDatosIncorrectos();
            return;
        }

        Mensajes.mostrarInfo("Bienvenido, " + usuario.getNombreCompleto());
        dispose();
        new Principal(this.controladorLogin.getGestorUsuario());
    }
}