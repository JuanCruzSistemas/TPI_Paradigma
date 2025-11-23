package ProyectoTPI.presentacion.panels;

import ProyectoTPI.datos.FileManager;
import ProyectoTPI.gestores.GestorCuenta;
import ProyectoTPI.recursos.Mensajes;
import ProyectoTPI.recursos.Rutas;
import ProyectoTPI.presentacion.ui.ButtonFactory;
import ProyectoTPI.presentacion.ui.ColorScheme;
import ProyectoTPI.presentacion.ui.ComponentFactory;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FormularioCuentaPanel extends FormularioBase {
    private GestorCuenta gestorCuenta;
    private JTextField txtLegajoBuscar;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTipoDoc;
    private JTextField txtNroDoc;
    private JTextField txtRol;
    private JButton btnBuscar;
    private Runnable onCuentaRegistrada;
    
    public FormularioCuentaPanel(GestorCuenta gestorCuenta) {
        super("+ Registrar Nueva Cuenta");
        this.gestorCuenta = gestorCuenta;
    }
    
    public void setOnCuentaRegistrada(Runnable callback) {
        this.onCuentaRegistrada = callback;
    }
    
    @Override
    protected void construirFormulario() {
        txtLegajoBuscar = ComponentFactory.crearCampoTexto();
        btnBuscar = ButtonFactory.crearBotonConTamano("Buscar", 110, 38);
        
        txtNombre = ComponentFactory.crearCampoTextoSoloLectura();
        txtApellido = ComponentFactory.crearCampoTextoSoloLectura();
        txtTipoDoc = ComponentFactory.crearCampoTextoSoloLectura();
        txtNroDoc = ComponentFactory.crearCampoTextoSoloLectura();
        txtRol = ComponentFactory.crearCampoTextoSoloLectura();
        
        JPanel panelBusqueda = crearPanelBusqueda();
        camposPanel.add(panelBusqueda);
        agregarEspacio(8);
        agregarSeparador();
        
        agregarCampo("Nombre", txtNombre);
        agregarCampo("Apellido", txtApellido);
        agregarCampo("Tipo Documento", txtTipoDoc);
        agregarCampo("Nro. Documento", txtNroDoc);
        agregarCampo("Rol UTN", txtRol);
        
        agregarSeparador();
        configurarEventos();
    }
    
    private JPanel crearPanelBusqueda() {
        JLabel lblBuscar = ComponentFactory.crearEtiqueta("Ingrese Legajo", 13, true, ColorScheme.AMARILLO);
        
        JPanel inputBusqueda = new JPanel();
        inputBusqueda.setLayout(new BoxLayout(inputBusqueda, BoxLayout.X_AXIS));
        inputBusqueda.setOpaque(false);
        
        txtLegajoBuscar.setMaximumSize(new Dimension(300, 38));
        txtLegajoBuscar.setPreferredSize(new Dimension(300, 38));
        btnBuscar.setMaximumSize(new Dimension(110, 38));
        btnBuscar.setPreferredSize(new Dimension(110, 38));
        
        inputBusqueda.add(txtLegajoBuscar);
        inputBusqueda.add(Box.createHorizontalStrut(10));
        inputBusqueda.add(btnBuscar);
        
        JPanel busquedaContainer = new JPanel(new BorderLayout(0, 5));
        busquedaContainer.setOpaque(false);
        busquedaContainer.add(lblBuscar, BorderLayout.NORTH);
        busquedaContainer.add(inputBusqueda, BorderLayout.CENTER);
        
        return busquedaContainer;
    }
    
    private void configurarEventos() {
        btnBuscar.addActionListener(e -> buscarPersona());
        
        txtLegajoBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnBuscar.doClick();
                }
            }
        });
    }
    
    private void buscarPersona() {
        String legajo = txtLegajoBuscar.getText().trim();
        if (legajo.isEmpty()) {
            Mensajes.mostrarError("Ingrese un legajo para buscar.");
            return;
        }
        
        String[] datos = buscarPersonaEnCSV(legajo);
        if (datos != null) {
            txtNombre.setText(datos[0]);
            txtApellido.setText(datos[1]);
            txtTipoDoc.setText(datos[3]);
            txtNroDoc.setText(datos[4]);
            txtRol.setText(datos[5]);
        } else {
            Mensajes.noExistePersonaConLegajo(legajo);
            limpiarCamposDatos();
        }
    }
    
    private String[] buscarPersonaEnCSV(String legajo) {
        List<String> lineas = this.gestorCuenta.getArchivoPersonas().leerArchivo();
        return lineas.stream()
                     .map(s -> s.split(";"))
                     .filter(s -> s[2].equals(legajo))
                     .findFirst()
                     .orElse(null);
    }
    
    @Override
    protected void onGuardar() {
        if (txtNombre.getText().trim().isEmpty()) {
            Mensajes.buscarLegajoValido();
            return;
        }

        String legajo = txtLegajoBuscar.getText().trim();
        
        if (existeLegajoCuenta(legajo)) {
            Mensajes.yaExisteCuentaConLegajo(legajo);
            return;
        }

        List<String> datosCuenta = Arrays.asList(
            txtNombre.getText().trim(),
            txtApellido.getText().trim(),
            legajo,
            txtTipoDoc.getText().trim(),
            txtNroDoc.getText().trim(),
            txtRol.getText().trim()
        );

        gestorCuenta.registrarCuenta(datosCuenta);
        Mensajes.cuentaRegistradaConExito();

        if (onCuentaRegistrada != null) {
            onCuentaRegistrada.run();
        }

        onLimpiar();
    }

    private boolean existeLegajoCuenta(String legajo) {
        FileManager archivoCuentas = new FileManager(Rutas.RUTA_CUENTAS);
        List<String> lineas = archivoCuentas.leerArchivo();
        Function<String, String[]> separarDatos = s -> s.split(";");
        
        return lineas.stream()
                     .map(separarDatos)
                     .anyMatch(campos -> campos[2].equalsIgnoreCase(legajo));
    }
    
    @Override
    protected void onLimpiar() {
        limpiarCampos(txtLegajoBuscar, txtNombre, txtApellido, txtTipoDoc, txtNroDoc, txtRol);
    }
    
    private void limpiarCamposDatos() {
        limpiarCampos(txtNombre, txtApellido, txtTipoDoc, txtNroDoc, txtRol);
    }
    
    @Override
    protected Dimension getDimensionesFormulario() {
        return new Dimension(520, 720);
    }
}