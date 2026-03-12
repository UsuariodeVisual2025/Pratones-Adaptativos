package com.patrones.adaptativos;

import java.io.IOException;

import javafx.fxml.FXML;

public class PrimaryController {
 /*metodo que te lleva a la pantalla secundaria */
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
