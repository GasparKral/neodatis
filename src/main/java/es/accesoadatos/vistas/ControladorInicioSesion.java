package es.accesoadatos.vistas;

import java.util.logging.Level;
import java.util.logging.Logger;

import es.accesoadatos.Aplicacion;
import es.accesoadatos.Constantes;
import es.accesoadatos.controladores.controladores_de_modelo.ControladorUsuario;
import es.accesoadatos.modelos.Usuario;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ControladorInicioSesion {

    @FXML
    Button iniciarSesionBtn;

    @FXML
    TextField usuario;

    @FXML
    TextField clave;

    @FXML
    Text error;

    @FXML
    public void initialize() {
        iniciarSesionBtn.disableProperty().bind(usuario.textProperty().isEmpty().or(clave.textProperty().isEmpty()));
    }

    @FXML
    public void iniciarSesion() {

        String nombreUsuario = usuario.getText();
        String claveUsuario = clave.getText();

        try {
            ControladorUsuario.iniciarSesion(new Usuario(nombreUsuario, claveUsuario));
        } catch (IllegalAccessError e) {
            error.setText(e.getMessage());
            error.setVisible(true);
        } catch (Exception e) {
            Logger.getLogger(Constantes.NOMBRE_LOGGER).log(Level.SEVERE,
                    "[ControladorInicioSesion.class]: No se ha podido iniciar sesion", e);
            return;
        }

        Aplicacion.cambiarEscena("menu");

    }

}
