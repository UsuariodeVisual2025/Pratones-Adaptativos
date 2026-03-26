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

    private int valorActual;
    private int reglaActual;
    private int contadorRegla;

    private int puntaje = 0; // 🔥 puntaje del jugador

    @FXML
    public void initialize() {

        if (patronLabel == null || tablaIntentos == null) {
            System.out.println("Error: fx:id no conectados");
            return;
        }

        colIntento.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getIntento()).asObject());

        colRespuesta.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getRespuesta()));

        colResultado.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getResultado()));

        tablaIntentos.setItems(lista);

        cargarNivel();

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

    private void cargarNivel() {

        int nivel = App.nivelSeleccionado;

        String patron = Reglas.obtenerPatron(nivel);

        valorActual = Reglas.obtenerUltimoNumero(nivel);

        reglaActual = 1;
        contadorRegla = 0;
        intentos = 0;
        puntaje = 0; // 🔥 reinicia puntaje

        patronLabel.setText("Nivel " + nivel + ": " + patron);
    }

    @FXML
    private void comprobar() {

        if (intentos >= 6) {
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

        int esperado = Reglas.aplicarRegla(
                App.nivelSeleccionado,
                valorActual,
                reglaActual
        );

        if (respuesta == esperado) {

            puntaje += 10; // 🔥 suma puntos

            lista.add(new Intento(intentos,
                    String.valueOf(respuesta),
                    "Correcto"));

            valorActual = respuesta;
            contadorRegla++;

            if (contadorRegla == 3) {

                reglaActual++;
                contadorRegla = 0;

                if (reglaActual > 2) {

                    patronLabel.setText("🎉 Nivel completado | Puntaje: " + puntaje);

                    ScoresController.agregarScore(App.nombreJugador, puntaje); // 🔥 GUARDA SCORE

                    respuestaField.setDisable(true);

                } else {

                    String pista = Reglas.obtenerPista(
                            App.nivelSeleccionado,
                            valorActual,
                            reglaActual
                    );

                    patronLabel.setText("🔄 Nueva regla → " + pista);
                }

            } else {

                patronLabel.setText("Nivel " + App.nivelSeleccionado +
                        " → Correcto (" + contadorRegla + "/3)");
            }

        } else {

            puntaje -= 5; // 🔥 resta puntos

            lista.add(new Intento(intentos,
                    String.valueOf(respuesta),
                    "Incorrecto"));

            if (intentos == 6) {

                patronLabel.setText("❌ Perdiste | Puntaje: " + puntaje);

                ScoresController.agregarScore(App.nombreJugador, puntaje); // 🔥 GUARDA SCORE

                respuestaField.setDisable(true);
            }
        }

        respuestaField.clear();
    }

    @FXML
    private void reiniciar() {

        intentos = 0;
        contadorRegla = 0;
        reglaActual = 1;
        puntaje = 0; // 🔥 reinicia puntaje

        lista.clear();
        respuestaField.clear();
        respuestaField.setDisable(false);

        cargarNivel();
    }

    @FXML
    private void volverNiveles() throws IOException {
        App.setRoot("levels");
    }
}