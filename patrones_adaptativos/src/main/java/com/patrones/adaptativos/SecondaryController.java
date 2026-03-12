package com.patrones.adaptativos;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML
    private TextField nicknameField;

    @FXML
    private Label errorLabel;

    /*
     * Este método permite regresar a la ventana principal
     * cuando el usuario presiona el botón de volver.
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    /*
     * Este método valida el apodo ingresado por el jugador.
     * Debe cumplir las reglas: no vacío, máximo 10 caracteres
     * y solo letras o números.
     */
    @FXML
    private void guardarNickname() {

        String nickname = nicknameField.getText();

        if (nickname.isEmpty()) {
            errorLabel.setText("El apodo no puede estar vacío");
            return;
        }

        if (!nickname.matches("[a-zA-Z0-9]{1,10}")) {
            errorLabel.setText("Máx 10 caracteres (solo letras y números)");
            return;
        }

        errorLabel.setText("");

        System.out.println("Apodo guardado: " + nickname);

        // Aquí luego se puede pasar a la pantalla del juego
        // App.setRoot("game");
    }
}