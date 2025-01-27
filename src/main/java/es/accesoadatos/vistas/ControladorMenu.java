package es.accesoadatos.vistas;

import java.io.File;

import es.accesoadatos.Aplicacion;
import es.accesoadatos.Constantes;
import es.accesoadatos.controladores.controladores_de_modelo.ControladorUsuario;
import es.accesoadatos.controladores.seguridad.ControladorCopiasDeSeguridad;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

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
    public void crearCopiaSeguridad() {
        ControladorCopiasDeSeguridad.hacerCopiaDeSeguridad();
    }

    @FXML
    public void importCopiaSeguridad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar copia de seguridad");
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("Archivos de copia de seguridad", "*.bin"));
        fileChooser.setInitialDirectory(new File(Constantes.DIRECCION_COPIAS_DE_SEGURIDAD));
        File archivo = fileChooser.showOpenDialog(Aplicacion.plataforma);
        if (archivo != null) {
            ControladorCopiasDeSeguridad.restaurarCopiaDeSeguridad(archivo);
        }
    }

    @FXML
    public void cerrarSesion() {
        ControladorUsuario.cerrarSesion();
        Aplicacion.cambiarEscena("inicioSesion");
    }

}
