package es.accesoadatos;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.accesoadatos.modelos.auxiliares.ConstructorDeCarpetas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Aplicacion extends Application {

    public static Stage plataforma;
    public static Scene escenario;

    @Override
    public void start(Stage stage) throws IOException {
        plataforma = stage;
        escenario = new Scene(loadFXML("inicioSesion"), 640, 480);
        plataforma.setScene(escenario);
        plataforma.show();
    }

    static void setRoot(String fxml) throws IOException {
        escenario.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {

        // Configuraciones básicas

        Logger logger = Logger.getLogger(Constantes.NOMBRE_LOGGER);
        logger.setLevel(Level.FINEST);
        logger.info("Aplicacion Iniciada");

        // Controlador de Carpetas

        ConstructorDeCarpetas.inicializar();

        launch();
    }

    public static void cambiarEscenaConControllador(String fxml, Object controllador) {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(controllador);
        try {
            escenario.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            Logger.getLogger(Constantes.NOMBRE_LOGGER).log(Level.SEVERE,
                    "[Aplicacion.class]: No se ha podido cambiar de escena", e);
        }
    }

    public static void cambiarEscena(String fxml) {
        try {
            escenario.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            Logger.getLogger(Constantes.NOMBRE_LOGGER).log(Level.SEVERE,
                    "[Aplicacion.class]: No se ha podido cambiar de escena", e);
        }
    }

    public static void abrirNuevaVentana(String fxml, Object controlador, String titulo, double ancho, double alto) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource(fxml + ".fxml"));
            if (controlador != null) {
                fxmlLoader.setController(controlador);
            }
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setTitle(titulo);
            newStage.setScene(new Scene(root, ancho, alto));
            newStage.initOwner(plataforma);
            newStage.initModality(Modality.WINDOW_MODAL); // Opcional, para bloquear la ventana principal
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}