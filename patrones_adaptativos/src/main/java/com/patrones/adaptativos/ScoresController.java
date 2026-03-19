package com.patrones.adaptativos;

import java.io.IOException;

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

    private static ObservableList<Score> lista = FXCollections.observableArrayList();

    public static void agregarScore(String jugador, int puntaje) {
        lista.add(new Score(jugador, puntaje));
    }

    @FXML
    public void initialize() {

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