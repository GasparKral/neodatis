package es.accesoadatos.controladores.controladores_de_modelo;

import java.util.HashSet;

import es.accesoadatos.controladores.infraestructura.ConexionBaseDeDatosNeodatis;
import es.accesoadatos.modelos.Articulo;
import javafx.collections.ObservableList;

public class ControladorArticulos {

    private static ControladorArticulos instance;

    // Método para obtener la instancia única de ControladorArticulos
    public static ControladorArticulos getInstance() {
        if (instance == null) {
            instance = new ControladorArticulos();
        }
        return instance;
    }

    private ControladorArticulos() {
        articulos.addAll(new ConexionBaseDeDatosNeodatis().consultarTodos());
    }

    // Lista observable de artículos
    public ObservableList<Articulo> articulos = javafx.collections.FXCollections.observableArrayList();
    // Conjunto de categorías únicas de los artículos
    private HashSet<String> categorias = new HashSet<>();

    // Método para obtener las categorías únicas
    public HashSet<String> getCategorias() {
        return categorias;
    }

    // Método para agregar un artículo a la lista y a la base de datos
    public void agregarArticulo(Articulo articulo) {
        articulos.add(articulo);
        categorias.add(articulo.getCategoria());
        new ConexionBaseDeDatosNeodatis().insertar(articulo);
    }

    // Método para borrar un artículo de la lista y de la base de datos
    public void borrarArticulo(Articulo articulo) {
        articulos.remove(articulo);
        if (esLaUltimaCategoria(articulo)) {
            categorias.remove(articulo.getCategoria());
        }
        new ConexionBaseDeDatosNeodatis().borrar(articulo);
    }

    // Método auxiliar para verificar si es la última instancia de una categoría
    private boolean esLaUltimaCategoria(Articulo articulo) {
        return articulos.filtered(art -> art.getCategoria().equals(articulo.getCategoria())).size() == 1;
    }

}
