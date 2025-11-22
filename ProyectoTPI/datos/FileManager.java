package ProyectoTPI.datos;

import ProyectoTPI.recursos.MensajesArchivos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

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

        try (PrintWriter salida = new PrintWriter(new FileWriter(archivo, append))) {
            Stream.of(informacion).filter(s -> !leerArchivo().contains(s))
                                  .forEach(salida::println);
            //List.of(informacion).forEach(salida::println);
        } catch (IOException e) {
            MensajesArchivos.errorEscrituraArchivo();
        }
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
            List<String> lineas = Files.readAllLines(Paths.get(rutaArchivo));
            lineas.removeFirst();
            return lineas;
        } catch(IOException e) {
            MensajesArchivos.errorLecturaArchivo();
            return null;
        }
    }

    public boolean limpiarArchivo() {
        int opcion = MensajesArchivos.mensajeConfirmacion("¿Desea borrar el contenido de \"" + getNombreArchivo() + "\"?");
        if (opcion != JOptionPane.YES_OPTION) {
            return false;
        }
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

        if (opcion != JOptionPane.YES_OPTION) {
            return false;
        }
        boolean eliminado = archivo.delete();
        if (eliminado) {
            MensajesArchivos.archivoEliminado();
        } else {
            MensajesArchivos.errorEliminacionArchivo();
        }
        return eliminado;
    }
}