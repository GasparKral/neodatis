package es.accesoadatos.vistas;

import es.accesoadatos.controladores.controladores_de_modelo.ControladorArticulos;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class ControladorFiltros {

    @FXML
    Spinner<Integer> codigo;

    @FXML
    TextField denominacion;

    @FXML
    Spinner<Double> pvp;

    @FXML
    ComboBox<String> categoria;

    @FXML
    Spinner<Integer> uv;

    @FXML
    Spinner<Integer> stock;

    @FXML
    public void initialize() {
        codigo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
        pvp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0, 1));
        uv.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
        stock.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));

        categoria.getItems().addAll(ControladorArticulos.getInstance().getCategorias());
    }

    @FXML
    public void query() {
    }
}
