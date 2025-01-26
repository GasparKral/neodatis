package es.accesoadatos.vistas;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import es.accesoadatos.Aplicacion;
import es.accesoadatos.Constantes;
import es.accesoadatos.controladores.controladores_de_modelo.ControladorArticulos;
import es.accesoadatos.controladores.seguridad.ControladorExportaciones;
import es.accesoadatos.modelos.Articulo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class ControladorGestorArticulos {

    private static ControladorGestorArticulos instance;

    public static ControladorGestorArticulos getInstance() {
        if (instance == null) {
            instance = new ControladorGestorArticulos();
        }
        return instance;
    }

    public TableView<Articulo> tablaArticulos;

    @FXML
    VBox tabla;

    @FXML
    VBox filtros;

    @FXML
    Button eliminarArticuloBtn;
    @FXML
    Button modificarArticuloBtn;

    @FXML
    ComboBox<String> formatoDeExportacion;

    @FXML
    public void initialize() {

        formatoDeExportacion.getItems().addAll(
                Arrays.stream(ControladorExportaciones.FormatoDeArchivo.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()));

        ControladorArticulos intancia = ControladorArticulos.getInstance();

        tablaArticulos = new TableView<>();

        Field[] campos = Articulo.class.getDeclaredFields();

        for (Field campo : campos) {
            tablaArticulos.getColumns().add(new TableColumn<>(campo.getName()));
        }

        tablaArticulos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        VBox.setVgrow(tablaArticulos, javafx.scene.layout.Priority.ALWAYS);

        tablaArticulos.getItems().addAll(intancia.articulos);

        tabla.getChildren().add(tablaArticulos);

        FXMLLoader filtrosLoader = new FXMLLoader(Aplicacion.class.getResource("filtros.fxml"));
        try {
            filtros.getChildren().add(filtrosLoader.load());
        } catch (IOException e) {
            Logger.getLogger(Constantes.NOMBRE_LOGGER).log(Level.SEVERE,
                    "[ControladorGestoArticulos.class]: No se ha podido cargar los filtros", e);
        }

        modificarArticuloBtn.disableProperty().bind(tablaArticulos.getSelectionModel().selectedItemProperty().isNull());
        eliminarArticuloBtn.disableProperty().bind(tablaArticulos.getSelectionModel().selectedItemProperty().isNull());

    }

    @FXML
    public void agregarNuevoArticulo() {
    }

    @FXML
    public void eliminarArticulos() {
    }

    @FXML
    public void modificarArticulo() {
    }

    @FXML
    public void exportarArticulos() {
    }

    @FXML
    public void atras() {
    }

}
