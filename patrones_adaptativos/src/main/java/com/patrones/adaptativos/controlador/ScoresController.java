package com.patrones.adaptativos.controlador; // <-- Paquete correcto

import java.io.IOException;

// Importaciones necesarias para conectar con el modelo y la vista principal
import com.patrones.adaptativos.modelo.Score;
import com.patrones.adaptativos.vista.App;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ScoresController {

    @FXML
    private TableView<Score> tablaScores;

    @FXML
    private TableColumn<Score, String> colJugador;

    @FXML
    private TableColumn<Score, Integer> colPuntaje;

    // Se mantiene static para que GameController pueda añadir puntajes sin instanciar el controlador
    private static ObservableList<Score> lista = FXCollections.observableArrayList();

    /**
     * Método estático para registrar nuevos puntajes desde cualquier parte del juego.
     */
    public static void agregarScore(String jugador, int puntaje) {
        lista.add(new Score(jugador, puntaje));
    }

    @FXML
    public void initialize() {
        // Validación de seguridad para evitar errores de carga
        if (tablaScores == null) return;

        // Configuración de cómo se muestran los datos en las columnas
        colJugador.setCellValueFactory(data -> 
                new SimpleStringProperty(data.getValue().getJugador()));

        colPuntaje.setCellValueFactory(data -> 
                new SimpleIntegerProperty(data.getValue().getPuntaje()).asObject());

        tablaScores.setItems(lista);
    }

    @FXML
    private void volver() throws IOException {
        App.setRoot("levels");
    }
}