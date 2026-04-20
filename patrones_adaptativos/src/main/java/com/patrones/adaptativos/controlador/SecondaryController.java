package com.patrones.adaptativos.controlador; // <-- Paquete correcto

import java.io.IOException;
import com.patrones.adaptativos.vista.App; // <-- Importación correcta desde vista
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML
    private TextField nicknameField;

    @FXML
    private Label errorLabel;

    /**
     * Regresa a la pantalla de bienvenida.
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    /**
     * Valida el nickname y lo guarda en la clase global App antes de cambiar de pantalla.
     */
    @FXML
    private void guardarNickname() throws IOException {
        String nickname = nicknameField.getText().trim(); // .trim() para evitar espacios en blanco

        // Validación de vacío
        if (nickname.isEmpty()) {
            errorLabel.setText("El apodo no puede estar vacío");
            return;
        }

        // Validación de formato: solo alfanuméricos y máximo 10 caracteres
        if (!nickname.matches("[a-zA-Z0-9]{1,10}")) {
            errorLabel.setText("Máx 10 caracteres (letras y números)");
            return;
        }

        // Si es válido, guardamos y navegamos
        errorLabel.setText("");
        App.nombreJugador = nickname; 
        
        System.out.println("Apodo guardado: " + nickname);
        App.setRoot("levels");
    }
}