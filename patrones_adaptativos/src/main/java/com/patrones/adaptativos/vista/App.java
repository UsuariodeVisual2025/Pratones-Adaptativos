package com.patrones.adaptativos.vista;

import java.io.IOException;
import java.net.URL;

import com.patrones.adaptativos.modelo.Score; // Importación del modelo para el registro final

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
    // La escena es el contenedor principal de todo el contenido visual
    private static Scene scene;

    // --- ESTADO GLOBAL (Variables estáticas) ---
    // Estas variables permiten compartir datos entre diferentes controladores
    public static int nivelSeleccionado = 1;      // Nivel que el usuario eligió jugar
    public static String nombreJugador = "Invitado"; // Nombre capturado al inicio
    public static int puntajeGlobal = 0;          // Puntaje acumulado durante la partida
    
    // Referencia al objeto Score de la partida actual para el guardado final
    public static Score scoreActual = null; 

    /**
     * start: Es el primer método que se ejecuta al abrir la aplicación.
     * Configura la ventana principal (Stage) y carga la primera pantalla.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargamos la pantalla inicial llamada "primary.fxml" con un tamaño de 800x600
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

    /**
     * loadFXML: Método privado encargado de buscar y leer los archivos FXML.
     * Convierte el archivo de diseño visual en un objeto de Java (Parent).
     */
    private static Parent loadFXML(String fxml) throws IOException {
        /**
         * CORRECCIÓN DE RUTA:
         * Se cambió "/com/patrones.adaptativos/" por "/com/patrones/adaptativos/".
         * Las rutas de recursos deben usar barras diagonales (/) para representar las carpetas físicas.
         */
        URL url = App.class.getResource("/com/patrones/adaptativos/" + fxml + ".fxml");
        
        // Validación de seguridad por si el archivo no existe o el nombre está mal escrito
        if (url == null) {
            throw new IOException("No se pudo encontrar el archivo FXML: " + fxml);
        }
        
        // El FXMLLoader es el que traduce el XML a objetos de la interfaz
        return new FXMLLoader(url).load();
    }

    /**
     * main: Punto de arranque estándar de Java.
     * Llama al método launch() para iniciar todo el ciclo de vida de JavaFX.
     */
    public static void main(String[] args) { 
        launch(); 
    }
}