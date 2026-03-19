package com.patrones.adaptativos;

import java.io.IOException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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
    private int respuestaCorrecta;

    @FXML
    public void initialize() {

        // 🔥 VALIDACIÓN (evita crash silencioso)
        if (patronLabel == null || tablaIntentos == null) {
            System.out.println("Error: fx:id no conectados");
            return;
        }

        // TABLA
        colIntento.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getIntento()).asObject());

        colRespuesta.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getRespuesta()));

        colResultado.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getResultado()));

        tablaIntentos.setItems(lista);

        // 🔥 NIVELES (MÁS LIMPIO)
        cargarNivel();

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

    // 🔥 MÉTODO NUEVO (MEJOR ORGANIZACIÓN)
    private void cargarNivel() {

        switch (App.nivelSeleccionado) {

            case 1:
                patronLabel.setText("Nivel 1: 2 4 6 ?");
                respuestaCorrecta = 8;
                break;

            case 2:
                patronLabel.setText("Nivel 2: 3 6 12 ?");
                respuestaCorrecta = 24;
                break;

            case 3:
                patronLabel.setText("Nivel 3: 1 1 2 3 ?");
                respuestaCorrecta = 5;
                break;

            case 4:
                patronLabel.setText("Nivel 4: 5 10 20 40 ?");
                respuestaCorrecta = 80;
                break;

            default:
                patronLabel.setText("Nivel desconocido");
                respuestaCorrecta = 0;
        }
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

            // 🔥 MENSAJE PRO
            System.out.println("¡Nivel completado!");

        } else {

            lista.add(new Intento(intentos,
                    String.valueOf(respuesta),
                    "Incorrecto"));

            if (intentos == 3) {
                System.out.println("Perdiste el nivel");
            }
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