package ProyectoTPI.recursos;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class FileManager {
    private String rutaArchivo;
    private File archivo;

    public FileManager(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.archivo = new File(rutaArchivo);
    }
    
    public boolean existe() {
        return this.archivo.exists();
    }

    public String getNombreArchivo() {
        return this.archivo.getName();
    }

    public String getRutaArchivo() {
        return this.rutaArchivo;
    }

    public boolean crearArchivo() {
        try {
            return !existe() && archivo.createNewFile();
        } catch (IOException e) {
            MensajesArchivos.errorCreacionArchivo();
            return false;
        }
    }

    public void agregarInformacion(boolean append, Object... informacion) {
        if (!existe()) crearArchivo();

        try (PrintWriter salida = new PrintWriter(archivo))
    }

    public void agregarInformacion(boolean append, List<?> informacion) {
        if (!existe()) crearArchivo();

        try (PrintWriter salida = new PrintWriter(new FileWriter(archivo, append))) {
            informacion.forEach(salida::println);
            
        } catch (IOException e) {
            MensajesArchivos.errorEscrituraArchivo();
        }
    }

    public List<String> leerArchivo() {
        if (!existe()) {
            MensajesArchivos.archivoNoExiste();
            return null;
        }
        try {
            return Files.readAllLines(Paths.get(rutaArchivo));
        } catch(IOException e) {
            MensajesArchivos.errorLecturaArchivo();
            return null;
        }
    }

    public boolean limpiarArchivo() {
        try (PrintWriter salida = new PrintWriter(archivo)) {
            return true;
        } catch (IOException e) {
            MensajesArchivos.errorLimpiezaArchivo();
            return false;
        }
    }

    public boolean eliminarArchivo() {
        if (!existe()) {
            MensajesArchivos.archivoNoExiste();
            return false;
        }

        int opcion = MensajesArchivos.mensajeConfirmacion("¿Desea eliminar el archivo \"" + getNombreArchivo() + "\"?");

        if (opcion == JOptionPane.YES_OPTION) {
            boolean eliminado = archivo.delete();
            if (eliminado) {
                MensajesArchivos.archivoEliminado();
            } else {
                MensajesArchivos.errorEliminacionArchivo();;
            }
            return eliminado;
        }
        return false;
    }
}