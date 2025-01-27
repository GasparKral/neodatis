package es.accesoadatos.controladores.controladores_de_modelo;

import java.util.HashSet;
import java.util.List;

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
        agregarInicio(ConexionBaseDeDatosNeodatis.getIntancia().consultarTodos());
    }

    // Lista observable de artículos
    public ObservableList<Articulo> articulos = javafx.collections.FXCollections.observableArrayList();
    // Conjunto de categorías únicas de los artículos
    private HashSet<String> categorias = new HashSet<>();

    // Método para obtener las categorías únicas
    public HashSet<String> getCategorias() {
        return categorias;
    }

    private void agregarInicio(List<Articulo> articulos) {
        for (Articulo articulo : articulos) {
            this.articulos.add(articulo);
            categorias.add(articulo.getCategoria());
        }
    }

    // Método para agregar un artículo a la lista y a la base de datos
    public void agregarArticulo(Articulo articulo) {
        articulos.add(articulo);
        categorias.add(articulo.getCategoria());
        ConexionBaseDeDatosNeodatis.getIntancia().insertar(articulo);
    }

    // Método para borrar un artículo de la lista y de la base de datos
    public void borrarArticulo(Articulo articulo) {
        articulos.remove(articulo);
        if (esLaUltimaCategoria(articulo)) {
            categorias.remove(articulo.getCategoria());
        }
        ConexionBaseDeDatosNeodatis.getIntancia().borrar(articulo, Articulo.class, "codigo", articulo.getCodigo());
    }

    // Método auxiliar para verificar si es la última instancia de una categoría
    private boolean esLaUltimaCategoria(Articulo articulo) {
        return articulos.filtered(art -> art.getCategoria().equals(articulo.getCategoria())).size() == 1;
    }

    public boolean codigoUsado(Integer codigo) {
        return this.articulos.stream().anyMatch(
                articulo -> articulo.getCodigo() == codigo);
    }

    public Articulo siguienteArticulo(Articulo actual) {
        return this.articulos.get((this.articulos.indexOf(actual) + 1) % this.articulos.size());
    }

    public Articulo anteriorArticulo(Articulo actual) {
        int indexActual = this.articulos.indexOf(actual);
        return this.articulos.get(((indexActual == 0) ? 7 : (indexActual - 1)) % this.articulos.size());
    }

}
