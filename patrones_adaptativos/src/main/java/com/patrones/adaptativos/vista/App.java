package com.patrones.adaptativos.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class App extends Application {
    private static Scene scene;

    // --- ESTADO GLOBAL ---
    public static int nivelSeleccionado = 1;
    public static String nombreJugador = "Invitado";
    public static int puntajeGlobal = 0;

    @Override
    public void start(Stage stage) throws IOException {
        // Código limpio, sin etiquetas de parámetros
        scene = new Scene(loadFXML("primary"), 800, 600);
        stage.setTitle("Patrones Adaptativos - Juego de Lógica");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL url = App.class.getResource("/com/patrones/adaptativos/" + fxml + ".fxml");
        return new FXMLLoader(url).load();
    }

    public static void main(String[] args) { 
        launch(); 
    }
}