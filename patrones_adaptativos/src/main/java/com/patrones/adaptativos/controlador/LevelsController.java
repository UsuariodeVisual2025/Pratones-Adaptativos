package com.patrones.adaptativos.controlador;

import java.io.IOException;

import com.patrones.adaptativos.vista.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * LevelsController: Gestiona la selección de niveles y la navegación.
 */
public class LevelsController {

    private String nombreJugador;

    /**
     * Recibe el nombre del jugador para mantener la sesión activa.
     */
    public void recibirNombreJugador(String nombre) {
        this.nombreJugador = nombre;
        System.out.println("DEBUG: Jugador en memoria: " + this.nombreJugador);
    }

    /**
     * MÉTODO VOLVER: Regresa a la pantalla de registro de apodo (secondary.fxml).
     */
    @FXML
    private void volver() {
        try {
            System.out.println("Regresando a la pantalla de registro de apodo...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/secondary.fxml"));
            Parent root = loader.load();
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("ERROR: No se pudo cargar la pantalla de registro (secondary.fxml)");
            e.printStackTrace();
        }
    }

    /**
     * Se llama desde tutorial.fxml para avanzar al selector de niveles.
     */
    @FXML
    private void switchToLevels() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/levels.fxml"));
            Parent root = loader.load();
            LevelsController nextController = loader.getController();
            nextController.recibirNombreJugador(this.nombreJugador);
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- MÉTODOS DE INICIO DE NIVELES ---

    @FXML private void nivel1() throws IOException { iniciarNivel(1); }
    @FXML private void nivel2() throws IOException { iniciarNivel(2); }
    @FXML private void nivel3() throws IOException { iniciarNivel(3); }
    @FXML private void nivel4() throws IOException { iniciarNivel(4); }

    private void iniciarNivel(int nivel) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/game.fxml"));
        Parent root = loader.load();
        GameController controller = loader.getController();
        controller.inicializarDatos(this.nombreJugador, nivel);
        App.getPrimaryStage().getScene().setRoot(root);
    }

    /**
     * MÉTODO ACTUALIZADO: Carga la tabla de puntajes correctamente.
     */
    @FXML
    private void verPuntajes() {
        try {
            System.out.println("Cargando tabla de puntajes...");
            
            // Usamos FXMLLoader manual para evitar errores de ruta que da App.setRoot
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/scores.fxml"));
            Parent root = loader.load();
            
            // Cambiamos la raíz de la escena actual
            App.getPrimaryStage().getScene().setRoot(root);
            
            System.out.println("Tabla de puntajes cargada con éxito.");
        } catch (IOException e) {
            System.err.println("ERROR: No se pudo cargar scores.fxml. Verifica que el archivo exista en resources.");
            e.printStackTrace();
        }
    }
}