package com.patrones.adaptativos.controlador;

import java.io.IOException;

import com.patrones.adaptativos.modelo.Score;
import com.patrones.adaptativos.servicios.DAOScore;
import com.patrones.adaptativos.vista.App;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ScoresController {

    @FXML private TableView<Score> tablaScores;
    @FXML private TableColumn<Score, Integer> colId; 
    @FXML private TableColumn<Score, String> colJugador;
    @FXML private TableColumn<Score, Integer> colPuntaje;
    @FXML private TableColumn<Score, Integer> colNivel;
    @FXML private TableColumn<Score, Integer> colIntentos;

    private static ObservableList<Score> lista = FXCollections.observableArrayList();
    private static DAOScore dao = new DAOScore();

    @FXML
    public void initialize() {
        lista.setAll(dao.readAll());
        
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        
        colJugador.setCellValueFactory(data -> {
            String nombreOriginal = data.getValue().getJugador();
            if (nombreOriginal != null && nombreOriginal.contains(" (")) {
                String nombreLimpio = nombreOriginal.split(" \\(")[0];
                return new SimpleStringProperty(nombreLimpio);
            }
            return new SimpleStringProperty(nombreOriginal);
        });

        colPuntaje.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPuntaje()).asObject());
        colNivel.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNivelAlcanzado()).asObject());
        colIntentos.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIntentosTotales()).asObject());
        
        tablaScores.setItems(lista);
    }

    public static void registrarPuntajeFinal() {
        System.out.println("Entrando a registrarPuntajeFinal...");

        if (App.puntajeGlobal <= 0 && !lista.isEmpty()) {
            System.out.println("No se guarda: puntaje <= 0");
            return;
        }

        int totalIntentos = (int) lista.stream()
                .filter(s -> s.getJugador().contains(App.nombreJugador))
                .count() + 1;

        String registro = App.nombreJugador;
        Score nuevoScore = new Score(registro, App.puntajeGlobal, App.nivelSeleccionado, totalIntentos);

        // --- GESTIÓN DE ERRORES PARA GAME-82 ---
        String resultado = dao.create(nuevoScore);
        
        if ("ERROR".equals(resultado)) {
            Platform.runLater(() -> {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error de Sistema");
                alerta.setHeaderText("No se pudo guardar el progreso");
                alerta.setContentText("Ocurrió un problema al conectar con la base de datos. Inténtalo de nuevo.");
                alerta.showAndWait();
            });
        } else {
            System.out.println("Guardado exitoso: " + resultado);
            lista.setAll(dao.readAll());
        }
    }

    @FXML
    private void volver() throws IOException {
        App.setRoot("levels");
    }
}