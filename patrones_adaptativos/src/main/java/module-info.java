module com.patrones.adaptativos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Permite el uso de conexiones JDBC

    // Permite la inyección FXML en tus controladores
    opens com.patrones.adaptativos.controlador to javafx.fxml;
    
    // Permite que las tablas (TableView) lean los datos de tus modelos
    opens com.patrones.adaptativos.modelo to javafx.base;
    
    // Exporta la vista para que el lanzador pueda iniciar
    exports com.patrones.adaptativos.vista;
}