package com.patrones.adaptativos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GameController {

    @FXML
    private Label patronLabel;

    @FXML
    private TextField respuestaField;

    @FXML
    private TableView<Intento> tablaIntentos;

    @FXML
    private TableColumn<Intento, Integer> colIntento;

    @FXML
    private TableColumn<Intento, String> colRespuesta;

    @FXML
    private TableColumn<Intento, String> colResultado;

    private ObservableList<Intento> lista = FXCollections.observableArrayList();

    private int intentos = 0;

    private int respuestaCorrecta = 8; // ejemplo nivel 1 (2 4 6 8)

    @FXML
    public void initialize() {

        colIntento.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIntento()).asObject());

        colRespuesta.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getRespuesta()));

        colResultado.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getResultado()));

        tablaIntentos.setItems(lista);
    }

    @FXML
    private void comprobar() {

        if (intentos >= 3) {
            return;
        }

        intentos++;

        int respuesta = Integer.parseInt(respuestaField.getText());

        if (respuesta == respuestaCorrecta) {

            lista.add(new Intento(intentos,
                    String.valueOf(respuesta),
                    "Correcto"));

        } else {

            lista.add(new Intento(intentos,
                    String.valueOf(respuesta),
                    "Incorrecto"));

        }

        respuestaField.clear();
    }

    @FXML
    private void reiniciar() {

        intentos = 0;
        lista.clear();
        respuestaField.clear();

    }

}
