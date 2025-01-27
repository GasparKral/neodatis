module es.accesoadatos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires transitive javafx.graphics;
    requires java.logging;
    requires neodatis.odb;
    requires org.json;
    requires java.sql;

    opens es.accesoadatos to javafx.fxml;
    opens es.accesoadatos.vistas to javafx.fxml;
    opens es.accesoadatos.vistas.componentes to javafx.fxml;
    opens es.accesoadatos.modelos to neodatis.odb, javafx.base;

    exports es.accesoadatos;
}
