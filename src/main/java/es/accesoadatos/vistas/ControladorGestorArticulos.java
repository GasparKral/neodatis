package es.accesoadatos.vistas;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class ControladorGestorArticulos {

    public static TableView<Articulo> tablaArticulos;

    @FXML
    VBox tabla;

    @FXML
    VBox filtros;

    @FXML
    Button eliminarArticuloBtn;
    @FXML
    Button modificarArticuloBtn;

    @FXML
    ComboBox<ControladorExportaciones.FormatoDeArchivo> formatoDeExportacion;

    @FXML
    public void initialize() {

        formatoDeExportacion.getItems().addAll(ControladorExportaciones.FormatoDeArchivo.values());

        ControladorArticulos intancia = ControladorArticulos.getInstance();

        tablaArticulos = new TableView<>();

        Field[] campos = Articulo.class.getDeclaredFields();

        for (Field campo : campos) {
            tablaArticulos.getColumns().add(new TableColumn<>(campo.getName()));
        }

        for (TableColumn<Articulo, ?> columna : tablaArticulos.getColumns()) {
            columna.setCellValueFactory(new PropertyValueFactory<>(columna.getText()));
        }

        tablaArticulos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        VBox.setVgrow(tablaArticulos, javafx.scene.layout.Priority.ALWAYS);

        tablaArticulos.setItems(intancia.articulos);
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
        Aplicacion.abrirNuevaVentana("campos", new ControladorCampos(), "Crear un nuevo Articulo", 700, 500);
    }

    @FXML
    public void eliminarArticulos() {
        Articulo articulo = tablaArticulos.getSelectionModel().getSelectedItem();
        ControladorArticulos.getInstance().borrarArticulo(articulo);
    }

    @FXML
    public void modificarArticulo() {
        Articulo articuloSelecionado = tablaArticulos.getSelectionModel().getSelectedItem();
        Aplicacion.abrirNuevaVentana("campos", new ControladorCampos(
                articuloSelecionado), "Crear un nuevo Articulo", 700, 500);
    }

    @FXML
    public void exportarArticulos() {
        DirectoryChooser selectionarDirectorio = new DirectoryChooser();
        selectionarDirectorio.setInitialDirectory(new File(System.getProperty("user.home")));

        File direccion = selectionarDirectorio.showDialog(Aplicacion.plataforma);
        if (direccion != null) {
            ControladorExportaciones.FormatoDeArchivo formatoDeExportacion = this.formatoDeExportacion.getValue();
            ControladorExportaciones.exportar(formatoDeExportacion, tablaArticulos.getItems(), Articulo.class,
                    direccion);
        }

    }

    @FXML
    public void atras() {
        Aplicacion.cambiarEscena("menu");
    }

}
