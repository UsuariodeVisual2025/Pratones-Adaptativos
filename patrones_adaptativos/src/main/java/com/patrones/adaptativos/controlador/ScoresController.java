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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * ScoresController: Gestiona la visualización y el registro de los puntajes.
 * Conecta la interfaz de usuario con la base de datos (a través del DAO).
 */
public class ScoresController {

    // --- CONEXIONES CON LA TABLA VISUAL (FXML) ---
    @FXML private TableView<Score> tablaScores;
    @FXML private TableColumn<Score, Integer> colId;
    @FXML private TableColumn<Score, String> colJugador;
    @FXML private TableColumn<Score, Integer> colPuntaje;
    @FXML private TableColumn<Score, Integer> colNivel;
    @FXML private TableColumn<Score, Integer> colIntentos;

    // Instancias de dominio (Viven y mueren con el controlador)
    private final ObservableList<Score> lista = FXCollections.observableArrayList();
    private final DAOScore dao = new DAOScore();

    /**
     * initialize(): Se ejecuta al abrir la pantalla de puntajes.
     * Carga los datos de la base de datos y los pone en la tabla.
     */
    @FXML
    public void initialize() {
        configurarColumnas();
        cargarDatosDesdeDB();
        tablaScores.setItems(lista);
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        
        // Columna Jugador: Incluye una lógica para "limpiar" el nombre si tiene paréntesis
        colJugador.setCellValueFactory(data -> {
            String nombre = data.getValue().getJugador();
            // Limpieza de nombre: quitamos metadatos adicionales si existen
            String nombreLimpio = (nombre != null && nombre.contains(" (")) ? 
                                 nombre.split(" \\(")[0] : nombre;
            return new SimpleStringProperty(nombreLimpio);
        });

        colPuntaje.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPuntaje()).asObject());
        colNivel.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNivelAlcanzado()).asObject());
        colIntentos.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIntentosTotales()).asObject());
    }

    private void cargarDatosDesdeDB() {
        // Ejecutamos la carga en un hilo aparte si la base de datos es lenta
        try {
            lista.setAll(dao.readAll());
        } catch (Exception e) {
            System.err.println("Error crítico al leer la DB: " + e.getMessage());
        }
    }

    /**
     * Registro de puntaje. Mantenemos el acceso estático para facilitar la llamada
     * desde el GameController, pero delegamos el trabajo a una instancia nueva del DAO.
     */
    public static void registrarPuntajeFinal(String nombreJugador, int puntajeFinal, int nivelJugador) {
        if (puntajeFinal <= 0) return;

        // Instancia de servicio temporal para la transacción
        DAOScore service = new DAOScore();
        
        try {
            // Cálculo dinámico de intentos
            int totalIntentos = (int) service.readAll().stream()
                    .filter(s -> s.getJugador().contains(nombreJugador))
                    .count() + 1;

            Score nuevoScore = new Score(nombreJugador, puntajeFinal, nivelJugador, totalIntentos);
            String resultado = service.create(nuevoScore);
            
            if ("ERROR".equals(resultado)) {
                Platform.runLater(() -> lanzarAlertaError());
            }
        } catch (Exception e) {
            System.err.println("Fallo en el registro de score: " + e.getMessage());
        }
    }

    private static void lanzarAlertaError() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error de Persistencia");
        alerta.setHeaderText("No se pudo sincronizar el puntaje");
        alerta.setContentText("El servidor de base de datos no respondió correctamente.");
        alerta.showAndWait();
    }

    @FXML
    public void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/levels.fxml"));
            Parent root = loader.load();
            
            // Reutilizamos el Stage principal definido en la clase App
            App.getPrimaryStage().getScene().setRoot(root);
            
        } catch (IOException e) {
            System.err.println("Error en la navegación: " + e.getMessage());
        }
    }
}