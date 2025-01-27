package es.accesoadatos.controladores.seguridad;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.accesoadatos.Constantes;
import es.accesoadatos.controladores.controladores_de_modelo.ControladorArticulos;
import es.accesoadatos.modelos.Articulo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Clase que se encarga de hacer copias de seguridad de los Articulos
 * presentes en la base de datos.
 */
public class ControladorCopiasDeSeguridad {

    private static ControladorArticulos instancia = ControladorArticulos.getInstance();

    /**
     * Hace una copia de seguridad de los Articulos presentes en la base de datos.
     * La copia de seguridad se almacena en un archivo binario que se encuentra
     * en la carpeta especificada en Constantes.DIRECCION_COPIAS_DE_SEGURIDAD.
     */
    public static void hacerCopiaDeSeguridad() {

        String fileName = "Articulos_" + LocalDate.now().toString() + System.currentTimeMillis() + ".bin";

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new java.io.FileOutputStream(Constantes.DIRECCION_COPIAS_DE_SEGURIDAD + fileName))) {
            oos.writeObject(instancia.articulos.toArray());
        } catch (IOException io) {
            Logger.getLogger(Constantes.NOMBRE_LOGGER).log(Level.SEVERE,
                    "[ControladorCopiasDeSeguridad.class]: No se ha podido hacer la copia de seguridad",
                    io);
        }
    }

    /**
     * Restaura la copia de seguridad del archivo especificado y la
     * guarda en la base de datos.
     * 
     * @param archivo el archivo que contiene la copia de seguridad
     */
    public static void restaurarCopiaDeSeguridad(File archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new java.io.FileInputStream(archivo))) {
            Object[] array = (Object[]) ois.readObject();
            ObservableList<Articulo> newList = FXCollections.observableArrayList();
            for (Object obj : array) {
                newList.add((Articulo) obj);
            }
            instancia.articulos = newList;
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(Constantes.NOMBRE_LOGGER).log(Level.SEVERE,
                    "[ControladorCopiasDeSeguridad.class]: No se ha podido restaurar la copia de seguridad",
                    e);
        }
    }

}
