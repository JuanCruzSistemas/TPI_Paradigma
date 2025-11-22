package ProyectoTPI.controladores;


import org.jpl7.Query;   
import org.jpl7.Term;
import org.jpl7.Atom;
import ProyectoTPI.user.*;
import java.util.Map;

public class ControladorLoginProlog {
    // Ruta ajustada a TU estructura: recursos/archivos/usuarios.pl
    private static final String PROLOG_FILE = "recursos/archivos/usuarios.pl";
    
    public ControladorLoginProlog() {
        cargarBaseConocimiento();
    }
    
    private void cargarBaseConocimiento() {
        try {
            Query q = new Query("consult", new Term[] {
                new Atom(PROLOG_FILE)
            });
            
            if (q.hasSolution()) {
                System.out.println("✅ Base de conocimiento Prolog cargada desde: " + PROLOG_FILE);
            } else {
                System.err.println("❌ Error al cargar: " + PROLOG_FILE);
                System.err.println("   Verifica que el archivo existe en recursos/archivos/");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al cargar Prolog: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Valida las credenciales de un usuario usando Prolog
     */
    public Usuario validarUsuario(String legajo, String password) {
        try {
            // Crear consulta Prolog
            String consulta = String.format(
                "validar_usuario('%s', '%s', Nombre, Rol)",
                legajo, password
            );
            
            System.out.println("🔍 Consultando Prolog: " + consulta);
            
            Query query = new Query(consulta);
            
            if (query.hasSolution()) {
                Map<String, Term> sol = query.oneSolution();
                
                String nombre = sol.get("Nombre").toString().replace("'", "");
                String rol = sol.get("Rol").toString();
                
                System.out.println("✅ Usuario encontrado: " + nombre + " (" + rol + ")");
                
                // Crear objeto Usuario
                Usuario usuario = new Usuario();
                usuario.setLegajo(legajo);
                usuario.setNombreCompleto(nombre);
                usuario.setRol(rol);
                
                return usuario;
            } else {
                System.out.println("❌ Usuario no encontrado o contraseña incorrecta");
                return null;
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error en validación: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Verifica si un usuario tiene un permiso específico
     */
    public boolean tienePermiso(String legajo, String accion) {
        try {
            String consulta = String.format(
                "tiene_permiso('%s', %s)",
                legajo, accion
            );
            
            Query query = new Query(consulta);
            return query.hasSolution();
            
        } catch (Exception e) {
            System.err.println("❌ Error al verificar permiso: " + e.getMessage());
            return false;
        }
    }
}
