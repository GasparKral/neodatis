package es.accesoadatos.vistas;

import es.accesoadatos.controladores.controladores_de_modelo.ControladorArticulos;
import es.accesoadatos.modelos.auxiliares.FilterBuilder;
import es.accesoadatos.vistas.componentes.BotonDeLimite;

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
    BotonDeLimite limitePVP;

    @FXML
    BotonDeLimite limiteUV;

    @FXML
    BotonDeLimite limiteStock;

    @FXML
    public void initialize() {
        codigo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
        pvp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0, 1));
        uv.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
        stock.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));

        categoria.getItems().addAll(ControladorArticulos.getInstance().getCategorias());

        limitePVP.setOnAction(event -> limitePVP.onAction());
        limiteUV.setOnAction(event -> limiteUV.onAction());
        limiteStock.setOnAction(event -> limiteStock.onAction());

    }

    @FXML
    public void limpiar() {
        return;
    }

    @SuppressWarnings("unchecked")
    @FXML
    public void query() {

        FilterBuilder filtros = new FilterBuilder();

        Integer valorCodigo = codigo.getValue();
        String valorDenominacion = denominacion.getText();
        Double valorPVP = pvp.getValue();
        String valorCategoria = categoria.getSelectionModel().getSelectedItem();
        Integer valorUV = uv.getValue();
        Integer valorSock = stock.getValue();

        if (valorCodigo != null && valorCodigo > 0) {
            filtros.agregarFiltro(articulo -> articulo.getCodigo() == valorCodigo);
        }

        if (valorDenominacion != null && !valorDenominacion.isEmpty()) {
            filtros.agregarFiltro(articulo -> articulo.getDenominacion().equalsIgnoreCase(valorDenominacion));
        }

        if (valorPVP != null && valorPVP > 0.0) {
            switch (limitePVP.getProperty().getValue()) {
                case IGUAL:
                    filtros.agregarFiltro(articulo -> articulo.getPvp() == valorPVP);
                    break;
                case MAYOR:
                    filtros.agregarFiltro(articulo -> articulo.getPvp() >= valorPVP);
                    break;

                case MENOR:
                    filtros.agregarFiltro(articulo -> articulo.getPvp() <= valorPVP);
                    break;
            }

        }

        if (valorCategoria != null && !valorCategoria.isEmpty()) {
            filtros.agregarFiltro(articulo -> articulo.getCategoria().equalsIgnoreCase(valorCategoria));
        }

        if (valorUV != null && valorUV >= 0) {
            switch (limiteUV.getProperty().getValue()) {
                case IGUAL:
                    filtros.agregarFiltro(articulo -> articulo.getUv() == valorUV);
                    break;
                case MAYOR:
                    filtros.agregarFiltro(articulo -> articulo.getUv() >= valorUV);
                    break;

                case MENOR:
                    filtros.agregarFiltro(articulo -> articulo.getUv() <= valorUV);
                    break;
            }

        }

        if (valorSock != null && valorSock >= 0) {
            switch (limiteUV.getProperty().getValue()) {
                case IGUAL:
                    filtros.agregarFiltro(articulo -> articulo.getStock() == valorSock);
                    break;
                case MAYOR:
                    filtros.agregarFiltro(articulo -> articulo.getStock() >= valorSock);
                    break;

                case MENOR:
                    filtros.agregarFiltro(articulo -> articulo.getStock() <= valorSock);
                    break;
            }

        }

        System.out.println(ControladorArticulos.getInstance().articulos.size());
        System.out.println(filtros.filtrar(ControladorArticulos.getInstance().articulos));
        
        System.out.println(ControladorArticulos.getInstance().articulos.size());

    }

}
