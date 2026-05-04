package com.patrones.adaptativos.controlador;

import java.io.IOException;

import com.patrones.adaptativos.vista.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * SecondaryController: Se encarga de la gestión y validación del apodo del jugador.
 * Sirve para asegurar que el nombre de usuario cumpla con el formato correcto.
 */
public class SecondaryController {

    // Conexión con el cuadro de texto del FXML para el apodo
    @FXML private TextField nicknameField;

    // Conexión con la etiqueta de texto para mostrar mensajes de error al usuario
    @FXML private Label errorLabel;

    /**
     * Método para el botón "Volver".
     * Regresa al usuario a la pantalla de bienvenida inicial.
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    /**
     * Método principal: Valida el nickname y lo pasa de forma segura al siguiente controlador.
     * Solo permite avanzar si el nombre cumple con las reglas establecidas.
     */
    @FXML
    private void guardarNickname() throws IOException {
        // Obtenemos el texto y usamos .trim() para eliminar espacios accidentales
        String nickname = nicknameField.getText().trim();

        // --- VALIDACIÓN 1: ¿Está vacío? ---
        if (nickname.isEmpty()) {
            if (errorLabel != null) {
                errorLabel.setText("El apodo no puede estar vacío");
            }
            return;
        }

        // --- VALIDACIÓN 2: Formato y Largo ---
        if (!nickname.matches("^[a-zA-Z]{1,10}$")) {
            if (errorLabel != null) {
                errorLabel.setText("Máx 10 caracteres (solo letras)");
            }
            return;
        }

        // --- ÉXITO ---
        if (errorLabel != null) {
            errorLabel.setText(""); // Limpiamos cualquier mensaje de error previo
        }
       
        System.out.println("Apodo validado: " + nickname);
       
        // Carga la siguiente vista (LevelsController) y le pasa el nombre
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/levels.fxml"));
        Parent root = loader.load();

        LevelsController controller = loader.getController();
        controller.recibirNombreJugador(nickname);

        // Cambiamos la escena en la ventana principal
        App.getPrimaryStage().setScene(new Scene(root, 800, 600));
    }
}