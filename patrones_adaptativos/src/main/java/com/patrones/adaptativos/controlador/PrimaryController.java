package com.patrones.adaptativos.controlador;

import java.io.IOException;

import com.patrones.adaptativos.vista.App;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * PrimaryController: Es el encargado de la pantalla de inicio o bienvenida.
 * Su objetivo principal es registrar el nombre del usuario antes de empezar.
 */
public class PrimaryController {

    // Conexión con el cuadro de texto donde el usuario escribe su nombre
    @FXML private TextField nombreField;

    /**
     * Este método se activa cuando el usuario hace clic en el botón para iniciar 
     * o entrar al juego (usualmente un botón llamado "Jugar" o "Siguiente").
     */
    @FXML
    private void switchToSecondary() throws IOException {
        // .getText() obtiene lo que se escribió. 
        // .trim() quita espacios vacíos al inicio o al final para evitar errores.
        String nombre = nombreField.getText().trim();
        
        /**
         * Lógica de identificación:
         * Si el nombre NO está vacío, se guarda ese nombre en la App.
         * Si el usuario dejó el cuadro vacío, se le asigna el nombre "Invitado".
         */
        App.nombreJugador = (!nombre.isEmpty()) ? nombre : "Invitado";
        
        // Una vez guardado el nombre, cambiamos la pantalla a la selección de niveles ("levels.fxml")
        App.setRoot("levels");
    }
}