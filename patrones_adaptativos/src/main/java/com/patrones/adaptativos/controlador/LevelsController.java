package com.patrones.adaptativos.controlador;

import java.io.IOException;
import com.patrones.adaptativos.vista.App;
import javafx.fxml.FXML;

public class LevelsController {

    @FXML
    private void volver() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void nivel1() throws IOException {
        iniciarNivel(1);
    }

    @FXML
    private void nivel2() throws IOException {
        iniciarNivel(2);
    }

    @FXML
    private void nivel3() throws IOException {
        iniciarNivel(3);
    }

    @FXML
    private void nivel4() throws IOException {
        iniciarNivel(4);
    }

    // Método auxiliar para evitar repetir código
    private void iniciarNivel(int nivel) throws IOException {
        App.nivelSeleccionado = nivel;
        App.setRoot("game");
    }

    @FXML
    private void verPuntajes() throws IOException {
        App.setRoot("scores");
    }
}