module com.patrones.adaptativos {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.patrones.adaptativos to javafx.fxml;
    exports com.patrones.adaptativos;
}
