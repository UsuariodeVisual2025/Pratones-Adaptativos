package com.patrones.adaptativos;

import java.io.IOException;
import javafx.fxml.FXML;

public class LevelsController {

    @FXML
    private void volver() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void nivel1() throws IOException {

        App.nivelSeleccionado = 1;
        App.setRoot("game");

    }

    @FXML
    private void nivel2() throws IOException {

        App.nivelSeleccionado = 2;
        App.setRoot("game");

    }

    @FXML
    private void nivel3() throws IOException {

        App.nivelSeleccionado = 3;
        App.setRoot("game");

    }

    @FXML
    private void nivel4() throws IOException {

        App.nivelSeleccionado = 4;
        App.setRoot("game");

    }

}