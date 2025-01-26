package es.accesoadatos.controladores.seguridad;

import java.util.List;
import java.util.logging.Logger;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import es.accesoadatos.Constantes;
import es.accesoadatos.modelos.interfaces.Imprimible;

public class ControladorExportaciones {

    /**
     * Enumeración que define los formatos de archivo disponibles
     * 
     * <pre>
     * JSON
     * CSV
     * </pre>
     */
    public static enum FormatoDeArchivo {
        JSON, CSV
    }

    /**
     * Método para realizar una copia de seguridad de las entidades en el formato
     * especificado.
     *
     * @param formato   el formato del archivo (JSON o CSV)
     * @param entidades la lista de entidades a exportar
     * @param clase     la clase de las entidades
     */
    public static <T extends Imprimible> void copiaDeSeguridad(FormatoDeArchivo formato, List<T> entidades,
            Class<T> clase) {
        switch (formato) {
            case JSON:
                copiaAJson(entidades, clase);
                break;
            case CSV:
                copiaACSV(entidades, clase);
                break;
        }
    }

    /**
     * Método privado para realizar una copia de seguridad en formato CSV.
     *
     * @param entidades la lista de entidades a exportar
     * @param clase     la clase de las entidades
     */
    private static <T extends Imprimible> void copiaACSV(List<T> entidades, Class<T> clase) {
        try (FileWriter escritor = new FileWriter(generadorDeNombre(clase) + ".csv")) {
            for (T entidad : entidades) {
                escritor.write(entidad.aCSV());
            }
        } catch (IOException e) {
            Logger.getLogger(Constantes.NOMBRE_LOGGER).warning(
                    "[ControladorExportaciones.class]: No se ha podido crear el archivo " + generadorDeNombre(clase)
                            + ".csv " + e.getMessage());
        }
    }

    /**
     * Método privado para realizar una copia de seguridad en formato JSON.
     *
     * @param entidades la lista de entidades a exportar
     * @param clase     la clase de las entidades
     */
    private static <T extends Imprimible> void copiaAJson(List<T> entidades, Class<T> clase) {
        try (FileWriter escritor = new FileWriter(generadorDeNombre(clase) + ".json")) {
            escritor.write("{" + "\"copia de seguridad de " + clase.getName() + "\"");
            for (T entidad : entidades) {
                escritor.write(entidad.aJSON());
            }
            escritor.write("}");
        } catch (IOException e) {
            Logger.getLogger(Constantes.NOMBRE_LOGGER).warning(
                    "[ControladorExportaciones.class]: No se ha podido crear el archivo " + generadorDeNombre(clase)
                            + ".json " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para generar el nombre del archivo de copia de seguridad.
     *
     * @param clase la clase de las entidades
     * @return el nombre generado para el archivo
     */
    private static <T extends Imprimible> String generadorDeNombre(Class<T> clase) {
        return clase.getName() + "_" + LocalDate.now().toString();
    }
}
