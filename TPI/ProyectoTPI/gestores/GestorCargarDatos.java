package ProyectoTPI.gestores;

import ProyectoTPI.user.Usuario;
import java.util.List;
public class GestorCargarDatos {
    private List<Usuario> usuarios;

    public void cargarDatos() {
        GestorUsuario gestor = new GestorUsuario();
        usuarios = gestor.cargarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("⚠️ No se cargaron usuarios desde el archivo.");
        } else {
            System.out.println("✅ usuarios cargados correctamente: " + usuarios.size());
        }
    }
    public List<Usuario> getUsuarios(){
        return this.usuarios;
    }
}
