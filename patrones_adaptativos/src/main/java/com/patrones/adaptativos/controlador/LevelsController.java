package com.patrones.adaptativos.controlador;

import java.io.IOException;

import com.patrones.adaptativos.vista.App;

import javafx.fxml.FXML;

/**
 * LevelsController: Este controlador gestiona la pantalla de selección de niveles.
 * Su función principal es capturar la elección del usuario y cambiar la escena.
 */
public class LevelsController {

    /**
     * Método para el botón "Volver".
     * Nos regresa a la pantalla anterior (la pantalla principal o "secondary").
     */
    @FXML
    private void volver() throws IOException {
        // App.setRoot cambia la vista que el usuario ve en su ventana
        App.setRoot("secondary");
    }

    /**
     * Los siguientes métodos (nivel1 al nivel4) se activan cuando
     * el usuario hace clic en los botones numerados de la interfaz.
     */

    @FXML
    private void nivel1() throws IOException {
        iniciarNivel(1); // Llama al método auxiliar enviando el número 1
    }

    @FXML
    private void nivel2() throws IOException {
        iniciarNivel(2); // Llama al método auxiliar enviando el número 2
    }

    @FXML
    private void nivel3() throws IOException {
        iniciarNivel(3); // Llama al método auxiliar enviando el número 3
    }

    @FXML
    private void nivel4() throws IOException {
        iniciarNivel(4); // Llama al método auxiliar enviando el número 4
    }

    /**
     * Método auxiliar (iniciarNivel):
     * Se usa para no repetir el mismo código en cada botón.
     * @param nivel El número del nivel que el usuario eligió.
     */
    private void iniciarNivel(int nivel) throws IOException {
        // Guardamos el nivel elegido en una variable global dentro de 'App'
        // para que el GameController sepa qué nivel debe cargar después.
        App.nivelSeleccionado = nivel;
        
        // Cambiamos la pantalla a la vista del juego ("game.fxml")
        App.setRoot("game");
    }

    /**
     * Método para el botón de "Ver Puntajes".
     * Cambia la vista hacia la tabla de récords o mejores puntajes.
     */
    @FXML
    private void verPuntajes() throws IOException {
        App.setRoot("scores");
    }
}