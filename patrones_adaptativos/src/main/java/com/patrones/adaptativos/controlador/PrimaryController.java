package com.patrones.adaptativos.controlador; // 

import java.io.IOException;
import com.patrones.adaptativos.vista.App; // 
import javafx.fxml.FXML;

public class PrimaryController {

    /* Método que te lleva a la pantalla secundaria */
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}