package ProyectoTPI.presentacion.panels;

import ProyectoTPI.gestores.GestorCuenta;
import ProyectoTPI.recursos.Mensajes;
import ProyectoTPI.presentacion.ui.ComponentFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario para registrar vehículos.
 * Principio: Single Responsibility - Solo maneja el registro de vehículos.
 */
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
            Mensajes.mostrarError("Por favor, complete todos los campos obligatorios.");
            return;
        }
        
        java.util.List<String> datosVehiculo = java.util.Arrays.asList(
            txtModelo.getText().trim(),
            txtMarca.getText().trim(),
            txtPatente.getText().trim(),
            (String) cmbColor.getSelectedItem(),
            txtLegajoCuenta.getText().trim()
        );
        
        gestorCuenta.registrarVehiculo(datosVehiculo);
        Mensajes.mostrarExito("Vehículo registrado exitosamente!");
        onLimpiar();
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