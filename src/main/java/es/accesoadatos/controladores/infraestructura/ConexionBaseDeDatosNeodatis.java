package es.accesoadatos.controladores.infraestructura;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import es.accesoadatos.Constantes;
import es.accesoadatos.modelos.Articulo;
import es.accesoadatos.modelos.Usuario;

/**
 * Clase para interactuar con la base de datos Neodatis.
 *
 * <p>
 * La base de datos se inicializa la primera vez que se abre y se cargan los
 * datos de ejemplo.
 * </p>
 */
public class ConexionBaseDeDatosNeodatis {

    private static final Logger LOGGER = Logger.getLogger(Constantes.NOMBRE_LOGGER);

    private ConexionBaseDeDatosNeodatis() {
        inicializar();
    }

    private static ConexionBaseDeDatosNeodatis instancia = null;

    public static ConexionBaseDeDatosNeodatis getIntancia() {
        if (instancia == null) {
            instancia = new ConexionBaseDeDatosNeodatis();
        }
        return instancia;
    }

    private ODB baseDeDatos;

    /**
     * Cierra la conexión con la base de datos.
     */
    private void cerrar() {
        if (baseDeDatos != null && !baseDeDatos.isClosed())
            baseDeDatos.close();
    }

    /**
     * Abre la conexión con la base de datos.
     */
    private void abrir() {
        if (baseDeDatos == null || baseDeDatos.isClosed())
            baseDeDatos = ODBFactory.open(Constantes.BD_NEODATIS);
    }

    /**
     * Inserta un Articulo o un Usuario en la base de datos.
     *
     * <p>
     * Abre la conexión con la base de datos, inserta el objeto con el
     * método store() y cierra la conexión.
     * </p>
     * 
     * @param articulo el Articulo o Usuario a insertar
     */
    public <T> void insertar(T articulo) {
        abrir();
        baseDeDatos.store(articulo);
        cerrar();
    }

    /**
     * Borra un Articulo o Usuario de la base de datos.
     *
     * <p>
     * Abre la conexión con la base de datos, elimina el objeto con el
     * método delete() y cierra la conexión.
     * </p>
     * 
     * @param articulo el Articulo o Usuario a borrar
     */

    public <T> void borrar(T objeto, Class<T> clazz, String campoUsado, Object valorCampo) {

        abrir();
        IQuery query = new CriteriaQuery(clazz,
                Where.equal(campoUsado,
                        valorCampo));

        baseDeDatos.delete(baseDeDatos.getObjects(query).getFirst());
        cerrar();
    }

    /**
     * Consulta todos los Articulos de la base de datos.
     *
     * @return una lista con todos los Articulos de la base de datos
     */
    public List<Articulo> consultarTodos() {
        abrir();
        Objects<Articulo> articulosTemp = baseDeDatos.getObjects(Articulo.class);

        List<Articulo> articulos = new ArrayList<>();
        articulosTemp.forEach(articulo -> articulos.add(articulo));
        cerrar();
        return articulos;
    }

    public Usuario consultarUsuario(String nombre, String clave) {
        abrir();
        Objects<Usuario> usuarios = baseDeDatos.getObjects(Usuario.class);
        Usuario usuario = null;
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre) && u.getClave().equals(clave)) {
                usuario = u;
                break;
            }
        }
        cerrar();
        return usuario;
    }

    /**
     * Inicializa la base de datos.
     */
    private void inicializar() {
        abrir();

        // Comprobar si ya existen datos en la base de datos
        Objects<Usuario> administrador = baseDeDatos.getObjects(Usuario.class);

        if (administrador.size() == 0) {
            LOGGER.info("Inicializando datos de ejemplo, base de datos vacía");

            // Cargar Admistrador
            Usuario administradorTemp = new Usuario("admin", "admin");
            insertar(administradorTemp);

            // Cargar los datos de ejemplo porque la base está vacía
            Articulo articulo1 = new Articulo(1, "Portatil Acer", 500.0, "Informática", 10, 20);
            Articulo articulo2 = new Articulo(2, "Pala Pádel", 100.0, "Deportes", 5, 30);
            Articulo articulo3 = new Articulo(3, "Caja Lápices", 6.0, "Escritorio", 10, 6);
            Articulo articulo4 = new Articulo(4, "Marcadores", 10.0, "Escritorio", 20, 19);
            Articulo articulo5 = new Articulo(5, "Memoria 32GB", 120.0, "Informática", 8, 10);
            Articulo articulo6 = new Articulo(6, "Micro Intel", 150.0, "Informática", 4, 10);
            Articulo articulo7 = new Articulo(7, "Bolas Pádel", 5.0, "Deportes", 15, 30);
            Articulo articulo8 = new Articulo(8, "Falda Pádel", 15.0, "Deportes", 10, 10);

            insertar(articulo1);
            insertar(articulo2);
            insertar(articulo3);
            insertar(articulo4);
            insertar(articulo5);
            insertar(articulo6);
            insertar(articulo7);
            insertar(articulo8);

            LOGGER.info("Datos de ejemplo cargados correctamente.");
        }

        // Cerrar conexión
        cerrar();
    }

}
