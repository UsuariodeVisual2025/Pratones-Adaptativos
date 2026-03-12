package com.patrones.adaptativos;

import java.io.IOException;

import javafx.fxml.FXML;

public class LevelsController {

    @FXML
    private void volver() throws IOException {
        App.setRoot("secondary");
    }

}