package es.accesoadatos.vistas.componentes;

import java.net.URL;

import es.accesoadatos.Aplicacion;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BotonDeLimite extends Button {

    public BotonDeLimite() {
        this.imagen = new ImageView();
        imagen.setImage(new Image(imagenActual.getValue().getImagen().toString()));
        imagen.setFitWidth(30);
        imagen.setFitHeight(30);
        this.setGraphic(imagen);

        this.getStyleClass().add("opciones");
    }

    public static enum ImagenLimiteBoton {
        IGUAL(Aplicacion.class.getResource("imagenes/equal.png")),
        MAYOR(Aplicacion.class.getResource("imagenes/more.png")),
        MENOR(Aplicacion.class.getResource("imagenes/less.png"));

        private final URL imagen;

        ImagenLimiteBoton(URL imagen) {
            this.imagen = imagen;
        }

        public URL getImagen() {
            return imagen;
        }

    }

    private Property<ImagenLimiteBoton> imagenActual = new SimpleObjectProperty<>(ImagenLimiteBoton.IGUAL);
    private ImageView imagen;

    private void siguienteImagen() {
        ImagenLimiteBoton siguiente = ImagenLimiteBoton.values()[(imagenActual.getValue().ordinal() + 1)
                % ImagenLimiteBoton.values().length];
        imagenActual.setValue(siguiente);
        cambiarImagen();
    }

    private void cambiarImagen() {
        imagen.setImage(new Image(imagenActual.getValue().getImagen().toString()));
        imagen.setFitWidth(30);
        imagen.setFitHeight(30);
        this.setGraphic(imagen);
    }

    public Property<ImagenLimiteBoton> getProperty() {
        return imagenActual;
    }

    public void setImagenActual(ImagenLimiteBoton imagenActual) {
        this.imagenActual.setValue(imagenActual);
    }

    public void onAction() {
        siguienteImagen();
    }

}
