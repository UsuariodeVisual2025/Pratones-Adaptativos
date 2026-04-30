module com.patrones.adaptativos {
    // --- LIBRERÍAS REQUERIDAS (DEPENDENCIAS) ---
    // 'requires' le dice a Java qué herramientas externas necesita el proyecto
    requires javafx.controls; // Necesario para botones, etiquetas y ventanas
    requires javafx.fxml;     // Necesario para cargar los archivos de diseño .fxml
    requires java.sql;        // ¡IMPORTANTE! Permite el uso de JDBC y conexiones a MySQL

    // --- PERMISOS DE ACCESO (REFLEXIÓN) ---
    
    /**
     * opens ... to javafx.fxml:
     * Permite que JavaFX "inyecte" los elementos del diseño en tus clases Java.
     * Sin esto, los @FXML private TextField no funcionarían.
     */
    opens com.patrones.adaptativos.controlador to javafx.fxml;
    
    /**
     * opens ... to javafx.base:
     * Permite que las TableView lean los atributos de tus modelos (como Score o Intento).
     * Esto es lo que permite que el juego muestre los datos en las tablas.
     */
    opens com.patrones.adaptativos.modelo to javafx.base;
    
    // --- EXPORTACIÓN ---
    
    /**
     * exports: Hace que el paquete de la vista sea visible para el sistema.
     * Es indispensable para que el lanzador de JavaFX pueda iniciar la clase App.
     */
    exports com.patrones.adaptativos.vista;
}