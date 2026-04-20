package com.patrones.adaptativos.controlador;

import java.io.IOException;

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

    @FXML private TableView<Score> tablaScores;
    @FXML private TableColumn<Score, String> colJugador;
    @FXML private TableColumn<Score, Integer> colPuntaje;

    private static ObservableList<Score> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colJugador.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJugador()));
        colPuntaje.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPuntaje()).asObject());
        tablaScores.setItems(lista);
    }

    public static void registrarPuntajeFinal() {
        // Validación de seguridad: solo registrar si hay un puntaje válido
        if (App.puntajeGlobal <= 0 && !lista.isEmpty()) return;

        long intentosPrevios = lista.stream()
                .filter(s -> s.getJugador().startsWith(App.nombreJugador))
                .count();

        String registro = App.nombreJugador + " (Intento " + (intentosPrevios + 1) + ")";
        
        lista.add(new Score(registro, App.puntajeGlobal));
    }

    @FXML
    private void volver() throws IOException {
        App.setRoot("levels");
    }
}