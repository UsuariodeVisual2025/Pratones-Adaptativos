package com.patrones.adaptativos.controlador; // Define la ubicación de este archivo en el proyecto

import java.io.IOException;

import com.patrones.adaptativos.vista.App; // Importa la clase principal para manejar la navegación

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * SecondaryController: Se encarga de la gestión y validación del apodo del jugador.
 * Sirve para asegurar que el nombre de usuario cumpla con el formato correcto.
 */
public class SecondaryController {

    // Conexión con el cuadro de texto del FXML para el apodo
    @FXML
    private TextField nicknameField;

    // Conexión con la etiqueta de texto para mostrar mensajes de error al usuario
    @FXML
    private Label errorLabel;

    /**
     * Método para el botón "Volver".
     * Regresa al usuario a la pantalla de bienvenida inicial.
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    /**
     * Método principal: Valida el nickname y lo guarda.
     * Solo permite avanzar si el nombre cumple con las reglas establecidas.
     */
    @FXML
    private void guardarNickname() throws IOException {
        // Obtenemos el texto y usamos .trim() para eliminar espacios accidentales al inicio o final
        String nickname = nicknameField.getText().trim(); 

        // --- VALIDACIÓN 1: ¿Está vacío? ---
        if (nickname.isEmpty()) {
            errorLabel.setText("El apodo no puede estar vacío");
            return; // El 'return' detiene el proceso aquí para que no cambie de pantalla
        }

        // --- VALIDACIÓN 2: Formato y Largo ---
        // matches("[a-zA-Z0-9]{1,10}"): Es una expresión regular que significa:
        // "Solo se permiten letras y números, con un mínimo de 1 y máximo de 10 caracteres"
        if (!nickname.matches("[a-zA-Z0-9]{1,10}")) {
            errorLabel.setText("Máx 10 caracteres (letras y números)");
            return; // Detiene el proceso si el formato es inválido
        }

        // --- ÉXITO ---
        // Si el código llega aquí, significa que pasó todas las validaciones
        errorLabel.setText(""); // Limpiamos cualquier mensaje de error previo
        App.nombreJugador = nickname; // Guardamos el apodo en la variable global de la aplicación
        
        // Mensaje de control en la consola para el programador
        System.out.println("Apodo guardado: " + nickname);
        
        // Finalmente, enviamos al usuario a la pantalla de selección de niveles
        App.setRoot("levels");
    }
}