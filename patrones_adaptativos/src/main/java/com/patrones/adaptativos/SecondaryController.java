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

    // Volver a la pantalla inicial
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    // Guardar apodo y pasar a niveles
    @FXML
    private void guardarNickname() throws IOException {

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

        // AQUÍ ESTABA EL PROBLEMA
        App.setRoot("levels");
    }
}