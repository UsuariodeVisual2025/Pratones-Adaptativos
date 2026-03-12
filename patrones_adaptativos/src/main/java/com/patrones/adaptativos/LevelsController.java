package com.patrones.adaptativos;

import java.io.IOException;

import javafx.fxml.FXML;

public class LevelsController {

    @FXML
    private void volver() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void principiante() throws IOException {
        App.setRoot("game");
    }

    @FXML
    private void intermedio() throws IOException {
        App.setRoot("game");
    }

    @FXML
    private void avanzado() throws IOException {
        App.setRoot("game");
    }

    @FXML
    private void experto() throws IOException {
        App.setRoot("game");
    }
}