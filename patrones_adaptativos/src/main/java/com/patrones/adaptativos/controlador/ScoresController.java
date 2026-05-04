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
 * Conecta la interfaz de usuario con la base de datos a través del DAO.
 */
public class ScoresController {

    // --- CONEXIONES CON LA TABLA VISUAL (FXML) ---
    @FXML private TableView<Score> tablaScores;
    @FXML private TableColumn<Score, Integer> colId;
    @FXML private TableColumn<Score, String> colJugador;
    @FXML private TableColumn<Score, Integer> colPuntaje;
    @FXML private TableColumn<Score, Integer> colNivel;
    @FXML private TableColumn<Score, Integer> colIntentos;

    // Lista que contiene los puntajes y el objeto DAO para comunicarse con la base de datos
    private static ObservableList<Score> lista = FXCollections.observableArrayList();
    private static DAOScore dao = new DAOScore();

    /**
     * initialize(): Se ejecuta al abrir la pantalla de puntajes.
     * Carga los datos de la base de datos y los pone en la tabla.
     */
    @FXML
    public void initialize() {
        // Pedimos al DAO que lea todos los registros guardados y los ponga en la lista
        lista.setAll(dao.readAll());
        
        // Configuramos cada columna para que sepa qué dato mostrar del objeto 'Score'
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        
        // Columna Jugador: Lógica para "limpiar" el nombre si tiene paréntesis
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
        
        // Asignamos la lista de datos a la tabla visual
        tablaScores.setItems(lista);
    }

    /**
     * registrarPuntajeFinal(String nombre, int puntaje, int nivel):
     * Registra el puntaje en la base de datos de manera desacoplada.
     */
    public static void registrarPuntajeFinal(String nombreJugador, int puntajeFinal, int nivelJugador) {
        System.out.println("Entrando a registrarPuntajeFinal...");

        // Evitamos guardar basura si el puntaje es 0
        if (puntajeFinal <= 0 && !lista.isEmpty()) {
            System.out.println("No se guarda: puntaje <= 0");
            return;
        }

        // Calculamos intentos totales para este jugador
        int totalIntentos = (int) lista.stream()
                .filter(s -> s.getJugador().contains(nombreJugador))
                .count() + 1;

        Score nuevoScore = new Score(nombreJugador, puntajeFinal, nivelJugador, totalIntentos);

        String resultado = dao.create(nuevoScore);
        
        if ("ERROR".equals(resultado)) {
            Platform.runLater(() -> {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error de Sistema");
                alerta.setHeaderText("No se pudo guardar el progreso");
                alerta.setContentText("Ocurrió un problema al conectar con la base de datos.");
                alerta.showAndWait();
            });
        } else {
            System.out.println("Guardado exitoso: " + resultado);
            lista.setAll(dao.readAll());
        }
    }

    /**
     * Método para el botón "Volver".
     * Regresa a la pantalla de selección de niveles (levels.fxml).
     * Actualizado a public para asegurar la conexión visual con el FXML.
     */
 @FXML
    public void volver() {
        System.out.println(">>> ¡CONEXIÓN EXITOSA! Intentando cambio forzado de pantalla...");
        
        try {
            // 1. Cargamos el archivo levels.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/levels.fxml"));
            Parent root = loader.load();
            
            // 2. Obtenemos el Stage (ventana) actual y cambiamos la raíz directamente
            // Esto ignora cualquier error que pueda tener el método App.setRoot
            App.getPrimaryStage().getScene().setRoot(root);
            
            System.out.println(">>> Cambio de pantalla realizado con éxito.");
            
        } catch (IOException e) {
            System.err.println(">>> ERROR: No se pudo cargar la raíz de levels.fxml");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(">>> ERROR CRÍTICO al acceder a la ventana principal:");
            e.printStackTrace();
        }
    }
}