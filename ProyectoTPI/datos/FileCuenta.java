package ProyectoTPI.datos;

import ProyectoTPI.dominio.Cuenta;
import ProyectoTPI.dominio.EstadoCuenta;
import ProyectoTPI.dominio.RolPersonaUTN;
import ProyectoTPI.dominio.TipoDocumento;
import ProyectoTPI.recursos.Rutas;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileCuenta extends FileManager {
    private FileManager archivoTipoDocumento;
    
    public FileCuenta() {
        super(Rutas.RUTA_CUENTAS);
        this.archivoTipoDocumento = new FileManager(Rutas.RUTA_TIPO_DOCUMENTO);
    }

    public List<Cuenta> leerCuentas() {
        List<String> lineas = leerArchivo();
        List<Cuenta> cuentas = lineas.stream()
                                     .map(this::armarCuenta)
                                     .collect(Collectors.toList());
        return cuentas;
    }

    public Cuenta armarCuenta(String linea) {
        String[] info = linea.split(";");
        String nombre = info[0];
        String apellido = info[1];
        String legajo = info[2];
        TipoDocumento tipoDocumento = buscarTipoDocumento(info[3]);
        String nroDocumento = info[4];
        LocalDateTime fechaYHoraCreacion = LocalDateTime.parse(info[5]);
        EstadoCuenta estadoCuenta = EstadoCuenta.valueOf(info[7]);
        RolPersonaUTN rolPersonaUTN = new RolPersonaUTN(info[8]);
        double saldo = Double.parseDouble(info[9]);

        return new Cuenta(nombre,apellido,legajo,tipoDocumento,nroDocumento,fechaYHoraCreacion, estadoCuenta,rolPersonaUTN,saldo);
    }

    public TipoDocumento buscarTipoDocumento(String siglas) {
        Predicate<String> compararSiglas = s -> s.split(";")[0].equals(siglas);
        Function<String, TipoDocumento> armarTipoDocumento = s -> {
            String[] info = s.split(";");
            String sigla = info[0];
            String descripcion = info[1];
            return new TipoDocumento(sigla, descripcion);
        };
        TipoDocumento tipoDocumento = archivoTipoDocumento.leerArchivo().stream()
                                                             .filter(compararSiglas)
                                                             .map(armarTipoDocumento)
                                                             .findFirst()
                                                             .orElse(null);
        return tipoDocumento;
    }
}