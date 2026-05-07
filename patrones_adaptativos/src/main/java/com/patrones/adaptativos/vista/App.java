package com.patrones.adaptativos.vista;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * App: Es la clase principal del proyecto. 
 * Inicia la interfaz de JavaFX y gestiona el intercambio de pantallas (vistas).
 */
public class App extends Application {
   
    private static Scene scene;
    private static Stage primaryStage;

    /**
     * start: Es el primer método que se ejecuta al abrir la aplicación.
     * Configura la ventana principal (Stage) y carga la primera pantalla.
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("primary"), 800, 600);
        
        stage.setTitle("Patrones Adaptativos - Juego de Lógica"); // Título de la ventana
        stage.setScene(scene); // Colocamos la escena en el escenario
        stage.show(); // Mostramos la ventana al usuario
    }

    /**
     * setRoot: Este método permite cambiar de una pantalla a otra.
     * Los controladores lo llaman (ej: App.setRoot("game")) para navegar.
     * @param fxml El nombre del archivo .fxml que se desea cargar.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        URL url = App.class.getResource("/com/patrones/adaptativos/" + fxml + ".fxml");
        
        // Validación de seguridad por si el archivo no existe o el nombre está mal escrito
        if (url == null) {
            throw new IOException("No se pudo encontrar el archivo FXML: " + fxml);
        }
        
        // El FXMLLoader es el que traduce el XML a objetos de la interfaz
        return new FXMLLoader(url).load();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}