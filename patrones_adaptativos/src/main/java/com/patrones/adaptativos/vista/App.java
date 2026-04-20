package com.patrones.adaptativos.vista; // <-- Paquete correcto

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App - Clase Principal
 */
public class App extends Application {

    private static Scene scene;

    // Variables globales compartidas entre controladores
    public static int nivelSeleccionado = 1;
    public static String nombreJugador = "Invitado";

    @Override
    public void start(Stage stage) throws IOException {
        // Cargamos la pantalla inicial (primary.fxml)
        scene = new Scene(loadFXML("primary"), 800, 600); // Ajusté el tamaño para que se vea mejor la tabla
        stage.setTitle("Patrones Adaptativos - Juego de Lógica");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia la raíz de la escena actual por un nuevo FXML.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Carga un archivo FXML desde la carpeta de recursos.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        // IMPORTANTE: Al estar App en .vista, usamos la ruta absoluta desde resources
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/patrones/adaptativos/" + fxml + ".fxml"));
        
        if (fxmlLoader.getLocation() == null) {
            throw new IOException("No se pudo encontrar el archivo FXML: " + fxml);
        }
        
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}