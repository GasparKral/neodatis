package es.accesoadatos.controladores.controladores_de_modelo;

import es.accesoadatos.controladores.infraestructura.ConexionBaseDeDatosNeodatis;
import es.accesoadatos.modelos.Usuario;

/**
 * Clase que se encarga de controlar el acceso de los usuarios al sistema.
 * <p>
 * Ofrece métodos para iniciar sesión y cerrar sesión, verificando que el
 * usuario y la contraseña sean válidos.
 * </p>
 */
public class ControladorUsuario {

    /**
     * Almacena el usuario que ha iniciado sesión.
     */
    private static Usuario usuarioLoggeado = null;

    /**
     * Devuelve el usuario que ha iniciado sesión.
     * 
     * @return el usuario que ha iniciado sesión
     */
    public static Usuario getUsuarioLoggeado() {
        return usuarioLoggeado;
    }

    /**
     * Comprueba si el usuario y la contraseña son válidos y, si es así,
     * inicia la sesión.
     * <p>
     * Si el usuario no existe, lanza una excepción {@link IllegalAccessError}.
     * </p>
     * 
     * @param usuarioLoggeado el usuario que intenta acceder al sistema
     * @throws IllegalAccessError si el usuario no existe
     */
    public static void iniciarSesion(Usuario usuarioLoggeado) throws IllegalAccessError {

        if (!verificarUsuario(usuarioLoggeado.getNombre(), usuarioLoggeado.getClave())) {
            throw new IllegalAccessError("Datos Incorrectos");
        }

        ControladorUsuario.usuarioLoggeado = usuarioLoggeado;
    }

    /**
     * Modifica los datos del usuario loggeado en la base de datos y en
     * la sesión actual.
     * <p>
     * Borra el usuario actual de la base de datos y lo reemplaza por el
     * usuario nuevo (con los nuevos datos). Luego, actualiza al usuario
     * loggeado en la sesión actual.
     * </p>
     * 
     * @param nuevosDatos el usuario con los nuevos datos
     */
    public static void modificarUsuario(Usuario nuevosDatos) {
        // Borrar el usuario de la BD
        ConexionBaseDeDatosNeodatis.getIntancia().borrar(ControladorUsuario.usuarioLoggeado, Usuario.class,
                "nombre", ControladorUsuario.usuarioLoggeado.getNombre());
        // Insertar el nuevo usuario en la BD
        ConexionBaseDeDatosNeodatis.getIntancia().insertar(nuevosDatos);
        // Actualizar el usuario loggeado
        ControladorUsuario.usuarioLoggeado = nuevosDatos;
    }

    /**
     * Cierra la sesión actual.
     */
    public static void cerrarSesion() {
        ControladorUsuario.usuarioLoggeado = null;
    }

    /**
     * Comprueba si el usuario y la contraseña son válidos.
     * <p>
     * Si la id existe es porque el usuario está guardado en la BD
     * </p>
     * 
     * @param nombre el nombre del usuario
     * @param clave  la contraseña del usuario
     * @return true si el usuario y la contraseña son válidos, false en caso
     *         contrario
     */
    private static boolean verificarUsuario(String nombre, String clave) {
        return ConexionBaseDeDatosNeodatis.getIntancia().consultarUsuario(nombre, clave) != null;
    }

}
