package es.accesoadatos.vistas;

import es.accesoadatos.Aplicacion;
import es.accesoadatos.controladores.controladores_de_modelo.ControladorUsuario;
import javafx.fxml.FXML;

public class ControladorMenu {

    @FXML
    public void gestionar() {
        Aplicacion.cambiarEscena("gestorArticulos");
    }

    @FXML
    public void cambioDatosUsuario() {
        Aplicacion.cambiarEscena("cambioDatosUsuario");
    }

    @FXML
    public void cerrarSesion() {
        ControladorUsuario.cerrarSesion();
        Aplicacion.cambiarEscena("inicioSesion");
    }

}
