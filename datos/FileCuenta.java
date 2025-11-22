package ProyectoTPI.datos;

import java.util.List;

import ProyectoTPI.dominio.Cuenta;
import ProyectoTPI.recursos.Rutas;

public class FileCuenta extends FileManager {
    /**
     * aca agregaría el archivo de vehiculos, no estaría mal una clase FileVehiculo que así como las otras tenga su metodo leerVehiculos(),
     * y otros métodos. Uno podría ser justamente buscarVehiculosDeCuenta(String patente), como el que menciono abajo.
     */
    // private FileVehiculo archivoVehiculos;
    public FileCuenta() {
        super(Rutas.RUTA_CUENTAS);
        // this.archivoVehiculos = new FileVehiculo();
    }

    public List<Cuenta> leerCuentas() {
        /*
         * AGREGAR IMPLEMENTACION (similar a la de leerUsuarios() de FileUsuario)
         * usaría mas metodos en vez de hacer solo 'atributo = info[indice]', porque no son solo string.
         * Ejemplo: al leer la cuenta, denería saber el rol de la persona utn. Pero en el archivo cuenta, solo está el nombre del rol, y el rol
         * no solo tiene nombre, tiene fechas y el descuento. Entonces, en vez de hacer
         * RolPersonaUTN rol = info[indice] (que estaría mal porque me daria solo el nombre),
         * hago RolPersonaUTN rol = buscarRolPor(info[indice]); y ese buscarRolPor(String nombreRol) busca en el archivo de roles registrados.
         * Encuentra el que tenga el nombre que figura en info[indice] y crea el objeto Rol con los datos encontrados, y lo asigna.
         * 
         * Algo similar sucede con los vehículos, solo que podría ser List<Vehiculo> vehiculos = buscarVehiculosDeCuenta(legajo); Eso buscaría en
         * el archivo de vehiculos registrados. Encuentra todos los que figuren con legajo igual al pasado, y los trae como List<Vehiculo>.
         * Otra forma (más facil pero cambia la forma de registrar una cuenta) es que al momento de registrar la cuenta, se registre además una
         * lista de las patentes de sus vehiculos. Entonces es más fácil buscar en el archivo de vehículos, y no se le pasa el legajo sino que se
         * le pasa la patente al metodo buscarVehiculosDeCuenta(String patente). De esta forma nos evitamos la relacion bidireccional entre Vehiculo
         * y Cuenta (si es que quieren, me da igual).
         * 
         * Lo otro hay métodos que lo hacen, como la fecha que creo que era algo como LocalDateTime.parse(y aca le paso la string de fecha) algo asi.
         * 
         * 
         */
        return List.of();
    }
}