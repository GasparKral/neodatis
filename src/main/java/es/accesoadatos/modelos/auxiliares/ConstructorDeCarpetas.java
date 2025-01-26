package es.accesoadatos.modelos.auxiliares;

import es.accesoadatos.Constantes;

/**
 * Clase que se encarga de crear las carpetas necesarias para que el sistema
 * funcione correctamente.
 */
public class ConstructorDeCarpetas {

    /**
     * Rutas de las carpetas que se crean.
     */
    public static final String[] RUTAS_CARPETAS = { Constantes.DIRECCION_BASE,
            Constantes.DIRECCION_COPIAS_DE_SEGURIDAD, Constantes.DIRECCION_CACHE };

    /**
     * Inicializa las carpetas necesarias para que el sistema funcione
     * correctamente.
     */
    public static void inicializar() {
        for (String ruta : RUTAS_CARPETAS) {
            java.io.File carpeta = new java.io.File(ruta);
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }
        }
    }

}
