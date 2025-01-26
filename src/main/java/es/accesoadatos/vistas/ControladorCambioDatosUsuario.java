package es.accesoadatos.vistas;

import es.accesoadatos.Aplicacion;
import es.accesoadatos.controladores.controladores_de_modelo.ControladorUsuario;
import es.accesoadatos.modelos.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControladorCambioDatosUsuario {

    @FXML
    TextField nuevoNombreUsuario;

    @FXML
    TextField nuevaClaveUsuario;

    @FXML
    public void initialize() {
        Usuario usuarioLoggeado = ControladorUsuario.getUsuarioLoggeado();

        nuevoNombreUsuario.setText(usuarioLoggeado.getNombre());
        nuevaClaveUsuario.setText(usuarioLoggeado.getClave());
    }

    @FXML
    public void guardarCambios() {
        String nuevoNombre = nuevoNombreUsuario.getText();
        String nuevaClave = nuevaClaveUsuario.getText();

        ControladorUsuario.modificarUsuario(new Usuario(nuevoNombre, nuevaClave));
        Aplicacion.cambiarEscena("menu");
    }

    @FXML
    public void cancelar() {
        Aplicacion.cambiarEscena("menu");
    }

}
