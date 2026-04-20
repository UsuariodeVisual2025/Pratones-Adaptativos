module com.patrones.adaptativos {
    requires javafx.controls;
    requires javafx.fxml;

    // Permitir acceso a los controladores para la inyección FXML
    opens com.patrones.adaptativos.controlador to javafx.fxml;
    
    // Exportar el paquete de la vista para que el launcher pueda iniciar la app
    exports com.patrones.adaptativos.vista;
    
    // Abrir el modelo para que las tablas (TableView) puedan leer los datos
    opens com.patrones.adaptativos.modelo to javafx.base;

    // Opcional: Si el modelo debe ser visible globalmente, descomenta la siguiente línea:
    // exports com.patrones.adaptativos.modelo;
}