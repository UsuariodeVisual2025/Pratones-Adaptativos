package com.patrones.adaptativos.controlador;

import java.io.IOException;

import com.patrones.adaptativos.modelo.Intento;
import com.patrones.adaptativos.modelo.Reglas;
import com.patrones.adaptativos.vista.App;

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

    @FXML private Label patronLabel;
    @FXML private Label statusLabel; // <-- PARA EL FEEDBACK DE LA HISTORIA DE USUARIO
    @FXML private TextField respuestaField;
    @FXML private TableView<Intento> tablaIntentos;
    @FXML private TableColumn<Intento, Integer> colIntento;
    @FXML private TableColumn<Intento, String> colRespuesta;
    @FXML private TableColumn<Intento, String> colResultado;

    private ObservableList<Intento> lista = FXCollections.observableArrayList();
    private int intentos = 0;
    private int valorActual;
    private int reglaActual;
    private int contadorRegla;
    private int puntaje = 0;

    @FXML
    public void initialize() {
        // Configuración de columnas para la tabla
        colIntento.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIntento()).asObject());
        colRespuesta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRespuesta()));
        colResultado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getResultado()));
        tablaIntentos.setItems(lista);

        cargarNivel();

        // Estilos para la tabla (Correcto/Incorrecto)
        colResultado.setCellFactory(column -> new TableCell<Intento, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null); setStyle("");
                } else {
                    setText(item);
                    setStyle(item.equals("Correcto") ? "-fx-text-fill: green; -fx-font-weight: bold;" : "-fx-text-fill: red; -fx-font-weight: bold;");
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
        puntaje = 0;
        patronLabel.setText("Nivel " + nivel + ": " + patron);
        statusLabel.setText(""); // Limpiar feedback al cargar
    }

    @FXML
    private void comprobar() {
        if (intentos >= 6) return;

        int respuesta;
        try {
            respuesta = Integer.parseInt(respuestaField.getText());
        } catch (Exception e) {
            respuestaField.clear();
            return;
        }

        intentos++;
        int esperado = Reglas.aplicarRegla(App.nivelSeleccionado, valorActual, reglaActual);

        // LÓGICA DE VALIDACIÓN Y FEEDBACK INMEDIATO
        if (respuesta == esperado) {
            puntaje += 10;
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Correcto"));
            
            // Feedback visual inmediato
            statusLabel.setText("¡Correcto!");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            
            valorActual = respuesta;
            contadorRegla++;

            if (contadorRegla == 3) {
                reglaActual++;
                contadorRegla = 0;
                if (reglaActual > 2) {
                    patronLabel.setText("🎉 Nivel completado | Puntaje: " + puntaje);
                    respuestaField.setDisable(true);
                } else {
                    patronLabel.setText("🔄 Nueva regla → " + Reglas.obtenerPista(App.nivelSeleccionado, valorActual, reglaActual));
                }
            } else {
                patronLabel.setText("Nivel " + App.nivelSeleccionado + " → Correcto (" + contadorRegla + "/3)");
            }
        } else {
            puntaje -= 5;
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Incorrecto"));
            
            // Feedback visual inmediato
            statusLabel.setText("Incorrecto");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            
            if (intentos == 6) {
                patronLabel.setText("❌ Perdiste | Puntaje: " + puntaje);
                respuestaField.setDisable(true);
            }
        }
        respuestaField.clear();
    }

    @FXML
    private void reiniciar() {
        intentos = 0; contadorRegla = 0; reglaActual = 1; puntaje = 0;
        lista.clear(); respuestaField.clear(); respuestaField.setDisable(false);
        statusLabel.setText("");
        cargarNivel();
    }

    @FXML
    private void volverNiveles() throws IOException {
        App.setRoot("levels");
    }
}