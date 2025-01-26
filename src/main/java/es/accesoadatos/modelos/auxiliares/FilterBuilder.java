package es.accesoadatos.modelos.auxiliares;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import es.accesoadatos.modelos.Articulo;

/**
 * Clase que permite construir un conjunto de filtros para una lista de
 * Articulo. Los filtros se pueden concatenar utilizando el operador
 * lógico AND.
 */
public class FilterBuilder {

    /**
     * Funciones que se encargan de filtrar los artículos. Los resultados se
     * concatenan utilizando el operador l gico AND.
     */
    private List<Function<Articulo, Boolean>> filters = new ArrayList<>();

    /**
     * Crea un constructor de filtros a partir de una lista de funciones.
     * 
     * @param filters lista de funciones que se encargan de filtrar los artículos.
     */
    public FilterBuilder(List<Function<Articulo, Boolean>> filters) {
        this.filters = filters;
    }

    /**
     * Crea un constructor de filtros a partir de un n mero variable de
     * funciones.
     * 
     * @param filters funciones que se encargan de filtrar los artículos.
     */
    public FilterBuilder(@SuppressWarnings("unchecked") Function<Articulo, Boolean>... filters) {
        for (Function<Articulo, Boolean> function : filters) {
            this.filters.add(function);
        }
    }

    /**
     * A ade una nueva funci n de filtrado al conjunto de filtros.
     * 
     * @param filter funci n de filtrado.
     * @return el mismo constructor de filtros.
     */
    public FilterBuilder addFilter(Function<Articulo, Boolean> filter) {
        filters.add(filter);
        return this;
    }

    /**
     * Filtra una lista de artículos seg n los filtros definidos.
     * 
     * @param articulos lista de artículos a filtrar.
     * @return la lista de artículos filtrados.
     */
    public List<Articulo> filter(List<Articulo> articulos) {

        List<Articulo> articulosFiltrados = articulos;

        // Se iteran los filtros y se van aplicando sobre la lista de artículos
        for (Function<Articulo, Boolean> filter : filters) {
            articulosFiltrados = articulosFiltrados.stream().filter(
                    articulo -> filter.apply(articulo)).toList();
        }

        return articulosFiltrados;
    }

}
