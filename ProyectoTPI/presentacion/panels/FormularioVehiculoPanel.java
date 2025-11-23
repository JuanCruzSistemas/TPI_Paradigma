package ProyectoTPI.presentacion.panels;

import ProyectoTPI.gestores.GestorCuenta;
import ProyectoTPI.recursos.MensajesDominio;
import ProyectoTPI.presentacion.ui.ComponentFactory;
import ProyectoTPI.datos.FileManager;
import ProyectoTPI.recursos.Rutas;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Dimension;

import java.util.List;
import java.util.function.Function;

public class FormularioVehiculoPanel extends FormularioBase {
    private GestorCuenta gestorCuenta;
    private JTextField txtModelo;
    private JTextField txtMarca;
    private JTextField txtPatente;
    private JComboBox<String> cmbColor;
    private JTextField txtLegajoCuenta;
    
    public FormularioVehiculoPanel(GestorCuenta gestorCuenta) {
        super("+ Registrar Nuevo Vehículo");
        this.gestorCuenta = gestorCuenta;
    }
    
    @Override
    protected void construirFormulario() {
        txtModelo = ComponentFactory.crearCampoTexto();
        txtMarca = ComponentFactory.crearCampoTexto();
        txtPatente = ComponentFactory.crearCampoTexto();
        cmbColor = ComponentFactory.crearComboBox(new String[] {
            "Blanco", "Negro", "Gris", "Rojo", "Azul", "Verde", "Otro"
        });
        txtLegajoCuenta = ComponentFactory.crearCampoTexto();
        
        agregarCampo("Modelo", txtModelo);
        agregarCampo("Marca", txtMarca);
        agregarCampo("Patente", txtPatente);
        agregarCampo("Color", cmbColor);
        agregarCampo("Legajo de la Cuenta", txtLegajoCuenta);
    }
    
    @Override
    protected void onGuardar() {
        if (txtModelo.getText().trim().isEmpty() ||
            txtMarca.getText().trim().isEmpty() ||
            txtPatente.getText().trim().isEmpty() ||
            txtLegajoCuenta.getText().trim().isEmpty()) {
            MensajesDominio.mensajeCompletarCampos();
            return;
        }

        String patente = txtPatente.getText().trim();
        
        // Validar que la patente no exista
        if (existePatente(patente)) {
            MensajesDominio.yaExisteVehiculoConPatente(patente);
            return;
        }

        List<String> datosVehiculo = java.util.Arrays.asList(
            txtModelo.getText().trim(),
            txtMarca.getText().trim(),
            patente,
            (String) cmbColor.getSelectedItem(),
            txtLegajoCuenta.getText().trim()
        );

        gestorCuenta.registrarVehiculo(datosVehiculo);
        MensajesDominio.vehiculoRegistradoConExito();
        onLimpiar();
    }

    private boolean existePatente(String patente) {
        FileManager archivoVehiculos = new FileManager(Rutas.RUTA_VEHICULOS);
        List<String> lineas = archivoVehiculos.leerArchivo();
        Function<String, String[]> separarDatos = s -> s.split(";");
        
        return lineas.stream()
                     .map(separarDatos)
                     .anyMatch(campos -> campos[2].equalsIgnoreCase(patente));
    }
    
    @Override
    protected void onLimpiar() {
        limpiarCampos(txtModelo, txtMarca, txtPatente, txtLegajoCuenta);
        cmbColor.setSelectedIndex(0);
    }
    
    @Override
    protected Dimension getDimensionesFormulario() {
        return new Dimension(520, 580);
    }
}