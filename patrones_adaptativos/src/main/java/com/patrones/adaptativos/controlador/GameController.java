package com.patrones.adaptativos.controlador;

import java.io.IOException;

import com.patrones.adaptativos.modelo.Intento;
import com.patrones.adaptativos.modelo.PerfilJugador;
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
    @FXML private Label statusLabel;
    @FXML private Label puntajeLabel; 
    @FXML private TextField respuestaField;
    @FXML private TableView<Intento> tablaIntentos;
    @FXML private TableColumn<Intento, Integer> colIntento;
    @FXML private TableColumn<Intento, String> colRespuesta;
    @FXML private TableColumn<Intento, String> colResultado;

    private ObservableList<Intento> lista = FXCollections.observableArrayList();
    private PerfilJugador perfil = new PerfilJugador();
    private int intentos = 0, valorActual, reglaActual, contadorRegla;
    private int puntaje;

    @FXML
    public void initialize() {
        this.puntaje = App.puntajeGlobal; 
        
        colIntento.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIntento()).asObject());
        colRespuesta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRespuesta()));
        colResultado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getResultado()));
        
        colResultado.setCellFactory(column -> new TableCell<Intento, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    setText(item);
                    setStyle(item.equals("Correcto") ? "-fx-text-fill: green; -fx-font-weight: bold;" : "-fx-text-fill: red; -fx-font-weight: bold;");
                } else {
                    setText(null); setStyle("");
                }
            }
        });

        tablaIntentos.setItems(lista);
        actualizarPuntajeUI();
        cargarNivel();
    }

    private void cargarNivel() {
        int nivel = App.nivelSeleccionado;
        valorActual = Reglas.obtenerUltimoNumero(nivel);
        reglaActual = 1;
        contadorRegla = 0;
        intentos = 0;
        patronLabel.setText("Adivina el patrón: " + Reglas.obtenerPatron(nivel));
        statusLabel.setText("Nivel " + nivel + " activo.");
    }

    @FXML
    private void comprobar() {
        int respuesta;
        try {
            respuesta = Integer.parseInt(respuestaField.getText());
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Ingresa solo números.");
            return;
        }

        intentos++;
        int esperado = Reglas.aplicarRegla(App.nivelSeleccionado, valorActual, reglaActual, perfil.getTasaExito());

        if (respuesta == esperado) {
            puntaje += 10;
            perfil.registrarIntento(true);
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Correcto"));
            statusLabel.setText("¡Correcto!");
            valorActual = respuesta;
            contadorRegla++;

            if (contadorRegla == 3) {
                if (App.nivelSeleccionado < 4) {
                    App.nivelSeleccionado++;
                    statusLabel.setText("¡Felicidades! Dificultad aumentada.");
                    cargarNivel();
                } else {
                    patronLabel.setText("🎉 ¡Juego Completado!");
                    ScoresController.registrarPuntajeFinal(); // Registro automático al ganar
                }
            }
        } else {
            puntaje -= 5;
            perfil.registrarIntento(false);
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Incorrecto"));
            statusLabel.setText("Incorrecto.");
        }
        
        App.puntajeGlobal = puntaje; 
        actualizarPuntajeUI();
        respuestaField.clear();
    }

    private void actualizarPuntajeUI() {
        if (puntajeLabel != null) puntajeLabel.setText("Puntaje: " + puntaje);
    }

    @FXML
    private void volverNiveles() throws IOException { 
        // AL VOLVER, REGISTRAMOS POR SI EL USUARIO QUIERE SALIR Y GUARDAR
        ScoresController.registrarPuntajeFinal();
        App.setRoot("levels"); 
    }

    @FXML
    private void reiniciar() {
        puntaje = 0; App.puntajeGlobal = 0; lista.clear(); actualizarPuntajeUI(); cargarNivel();
    }
}