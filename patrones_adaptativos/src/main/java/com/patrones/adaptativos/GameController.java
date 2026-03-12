package com.patrones.adaptativos;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

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

    private int respuestaCorrecta;

    @FXML
    public void initialize() {

        colIntento.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getIntento()).asObject());

        colRespuesta.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getRespuesta()));

        colResultado.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getResultado()));

        tablaIntentos.setItems(lista);

        // NIVELES

        if (App.nivelSeleccionado == 1) {

            patronLabel.setText("2 4 6 ?");
            respuestaCorrecta = 8;

        }

        if (App.nivelSeleccionado == 2) {

            patronLabel.setText("3 6 12 ?");
            respuestaCorrecta = 24;

        }

        if (App.nivelSeleccionado == 3) {

            patronLabel.setText("1 1 2 3 ?");
            respuestaCorrecta = 5;

        }

        if (App.nivelSeleccionado == 4) {

            patronLabel.setText("5 10 20 40 ?");
            respuestaCorrecta = 80;

        }

        // COLOR DE RESULTADOS

        colResultado.setCellFactory(column -> new TableCell<Intento, String>() {

            @Override
            protected void updateItem(String item, boolean empty) {

                super.updateItem(item, empty);

                if (empty || item == null) {

                    setText(null);
                    setStyle("");

                } else {

                    setText(item);

                    if (item.equals("Correcto")) {

                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

                    } else {

                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

                    }
                }
            }
        });
    }

    @FXML
    private void comprobar() {

        if (intentos >= 3) {
            return;
        }

        int respuesta;

        try {

            respuesta = Integer.parseInt(respuestaField.getText());

        } catch (Exception e) {

            respuestaField.clear();
            return;

        }

        intentos++;

        if (respuesta == respuestaCorrecta) {

            lista.add(new Intento(intentos,
                    String.valueOf(respuesta),
                    "Correcto"));

            respuestaField.setDisable(true);

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
        respuestaField.setDisable(false);

    }

    @FXML
    private void volverNiveles() throws IOException {

        App.setRoot("levels");

    }

}