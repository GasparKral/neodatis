package es.accesoadatos.vistas;

import es.accesoadatos.controladores.controladores_de_modelo.ControladorArticulos;
import es.accesoadatos.modelos.Articulo;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorCampos {

    public ControladorCampos() {
        this.articulo = null;
        this.original = null;
    }

    public ControladorCampos(Articulo articuloAModifica) {
        this.articulo = articuloAModifica;
        this.original = articuloAModifica;
    }

    private Articulo articulo = null;
    private Articulo original;
    private ControladorArticulos instancia = ControladorArticulos.getInstance();

    @FXML
    Button siguiente;

    @FXML
    Button anterior;

    @FXML
    Label error;

    @FXML
    Spinner<Integer> codigo;

    @FXML
    TextField denominacion;

    @FXML
    Spinner<Double> pvp;

    @FXML
    TextField categoria;

    @FXML
    Spinner<Integer> uv;

    @FXML
    Spinner<Integer> stock;

    @FXML
    public void initialize() {
        setteraValores();
        siguiente.setOnAction(event -> siguiente());
        anterior.setOnAction(event -> anterior());

    }

    private void siguiente() {
        original = instancia.siguienteArticulo(original);
        articulo = original;
        setteraValores();
    }

    private void anterior() {
        original = instancia.anteriorArticulo(original);
        articulo = original;
        setteraValores();
    }

    @FXML
    public void atras(Event evento) {
        Stage plataformaActual = (Stage) ((Node) evento.getSource()).getScene().getWindow();

        // Cierra la ventana actual
        plataformaActual.close();
    }

    @FXML
    public void guardar() {

        if (instancia.codigoUsado(codigo.getValue()) && original == null) {
            error.setVisible(true);
            return;
        }

        error.setVisible(false);

        if (original != null) {
            instancia.borrarArticulo(original);
        }
        constructor();
        instancia.agregarArticulo(articulo);
    }

    @FXML
    public void cancelar(Event evento) {
        Stage plataformaActual = (Stage) ((Node) evento.getSource()).getScene().getWindow();

        // Cierra la ventana actual
        plataformaActual.close();
    }

    private void setteraValores() {
        Integer codigoValorInicial = 1;
        Double pvpValorInicial = 0.0;
        Integer uvValorInicial = 0;
        Integer stockValorInicial = 0;

        if (this.articulo != null) {
            siguiente.setVisible(true);
            anterior.setVisible(true);

            codigoValorInicial = this.articulo.getCodigo();
            denominacion.setText(this.articulo.getDenominacion());
            pvpValorInicial = this.articulo.getPvp();
            categoria.setText(this.articulo.getCategoria());
            uvValorInicial = this.articulo.getUv();
            stockValorInicial = this.articulo.getStock();
        }

        codigo.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, codigoValorInicial, 1));
        pvp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, pvpValorInicial, 1));
        uv.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, uvValorInicial, 1));
        stock.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, stockValorInicial, 1));

    }

    private void constructor() {
        Integer codigo = this.codigo.getValue();
        String denominacion = this.denominacion.getText();
        Double pvp = this.pvp.getValue();
        String categoria = this.categoria.getText();
        Integer uv = this.uv.getValue();
        Integer stock = this.stock.getValue();

        articulo = new Articulo(codigo, denominacion, pvp, categoria, uv, stock);
    }
}
