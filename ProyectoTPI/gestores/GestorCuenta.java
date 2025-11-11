package ProyectoTPI.gestores;

import ProyectoTPI.dominio.Cuenta;

import java.util.ArrayList;
import java.util.List;

public class GestorCuenta {
    private List<Cuenta> cuentas = new ArrayList<>();

    public void agregarCuenta(Cuenta c) {
        cuentas.add(c);
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public boolean tieneCuentas() {
        return !cuentas.isEmpty();
    }
}
