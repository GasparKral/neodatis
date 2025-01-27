package es.accesoadatos.controladores;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import es.accesoadatos.modelos.Articulo;
import es.accesoadatos.modelos.auxiliares.FilterBuilder;

public class FiltersTest {

    public List<Articulo> articulosTest;

    @Test
    public void testFiltroVacio() {
        // Filtros vacios
        FilterBuilder filtros = new FilterBuilder();
        List<Articulo> articulos = List.of(
                new Articulo(1, "Portatil Acer", 500.0, "Informática", 10, 20),
                new Articulo(2, "Pala Pádel", 100.0, "Deportes", 5, 30));
        List<Articulo> articulosFiltrados = filtros.filtrar(articulos);
        assertEquals(articulos, articulosFiltrados);
    }

    @Test
    public void testFiltroConUnElemento() {
        // Aquellos con stock menor a 10
        FilterBuilder filtros = new FilterBuilder(articulo -> articulo.getStock() > 10);
        List<Articulo> articulos = List.of(
                new Articulo(1, "Portatil Acer", 500.0, "Informática", 10, 20),
                new Articulo(2, "Pala Pádel", 100.0, "Deportes", 5, 30));
        List<Articulo> articulosFiltrados = filtros.filtrar(articulos);
        assertEquals(2, articulosFiltrados.size());
        assertEquals(articulos.get(0), articulosFiltrados.get(0));
    }

    @Test
    public void testFiltroConVariosElementos() {
        // Aquellos que tengan stock superior a 10 y que sean de la categoria
        // Informática
        FilterBuilder filtros = new FilterBuilder(
                articulo -> articulo.getStock() > 10,
                articulo -> articulo.getCategoria().equals("Informática"));
        List<Articulo> articulos = List.of(
                new Articulo(1, "Portatil Acer", 500.0, "Informática", 10, 20),
                new Articulo(2, "Pala Pádel", 100.0, "Deportes", 5, 30),
                new Articulo(3, "Caja Lápices", 6.0, "Escritorio", 10, 6),
                new Articulo(4, "Marcadores", 10.0, "Escritorio", 20, 19));
        List<Articulo> articulosFiltrados = filtros.filtrar(articulos);
        assertEquals(1, articulosFiltrados.size());
        assertEquals(articulos.get(0), articulosFiltrados.get(0));
    }

    @Test
    public void testFiltroConNingunElemento() {
        // Aquellos con stock menor a 100
        FilterBuilder filtros = new FilterBuilder(articulo -> articulo.getStock() > 100);
        List<Articulo> articulos = List.of(
                new Articulo(1, "Portatil Acer", 500.0, "Informática", 10, 20),
                new Articulo(2, "Pala Pádel", 100.0, "Deportes", 5, 30));
        List<Articulo> articulosFiltrados = filtros.filtrar(articulos);
        assertTrue(articulosFiltrados.isEmpty());
    }

}
