package com.patrones.adaptativos.controlador;

import java.io.IOException;

import com.patrones.adaptativos.modelo.Score;
import com.patrones.adaptativos.servicios.DAOScore;
import com.patrones.adaptativos.vista.App; // Importamos el DAO

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ScoresController {

    @FXML private TableView<Score> tablaScores;
    @FXML private TableColumn<Score, String> colJugador;
    @FXML private TableColumn<Score, Integer> colPuntaje;
    @FXML private TableColumn<Score, Integer> colNivel;
    @FXML private TableColumn<Score, Integer> colIntentos;

    private static ObservableList<Score> lista = FXCollections.observableArrayList();
    // Instancia del servicio DAO
    private static DAOScore dao = new DAOScore();

    @FXML
    public void initialize() {
        colJugador.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJugador()));
        colPuntaje.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPuntaje()).asObject());
        colNivel.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNivelAlcanzado()).asObject());
        colIntentos.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIntentosTotales()).asObject());
        
        tablaScores.setItems(lista);
    }

    public static void registrarPuntajeFinal() {
    System.out.println("Entrando a registrarPuntajeFinal..."); // <-- AGREGA ESTO

    if (App.puntajeGlobal <= 0 && !lista.isEmpty()) {
        System.out.println("No se guarda: puntaje <= 0");
        return;
    }

    int totalIntentos = (int) lista.stream()
            .filter(s -> s.getJugador().contains(App.nombreJugador))
            .count() + 1;

    String registro = App.nombreJugador + " (Intento " + totalIntentos + ")";
    
    Score nuevoScore = new Score(registro, App.puntajeGlobal, App.nivelSeleccionado, totalIntentos);

    lista.add(nuevoScore);
    
    // Aquí forzamos la impresión del resultado
    String resultado = dao.create(nuevoScore);
    System.out.println("INTENTO DE GUARDADO EN BD: " + resultado); // <-- ASEGÚRATE DE VER ESTO
}

    @FXML
    private void volver() throws IOException {
        App.setRoot("levels");
    }
}