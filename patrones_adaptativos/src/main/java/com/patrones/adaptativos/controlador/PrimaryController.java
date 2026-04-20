package com.patrones.adaptativos.controlador;

import java.io.IOException;

import com.patrones.adaptativos.vista.App;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PrimaryController {
    @FXML private TextField nombreField;

    @FXML
    private void switchToSecondary() throws IOException {
        String nombre = nombreField.getText().trim();
        App.nombreJugador = (!nombre.isEmpty()) ? nombre : "Invitado";
        App.setRoot("levels");
    }
}