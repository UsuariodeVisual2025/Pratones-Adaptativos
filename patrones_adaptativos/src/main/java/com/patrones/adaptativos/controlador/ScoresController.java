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
        
        // Columna Jugador: Incluye una lógica para "limpiar" el nombre si tiene paréntesis
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
     * registrarPuntajeFinal(): Este método es especial porque es 'static'.
     * Se puede llamar desde cualquier otra pantalla para guardar el progreso actual.
     */
    public static void registrarPuntajeFinal() {
        System.out.println("Entrando a registrarPuntajeFinal...");

        // Si el puntaje es 0 o negativo, no guardamos nada para no llenar la base de datos de basura
        if (App.puntajeGlobal <= 0 && !lista.isEmpty()) {
            System.out.println("No se guarda: puntaje <= 0");
            return;
        }

        // Calculamos cuántos intentos lleva este jugador específico
        int totalIntentos = (int) lista.stream()
                .filter(s -> s.getJugador().contains(App.nombreJugador))
                .count() + 1;

        // Creamos un nuevo objeto 'Score' con los datos actuales de la sesión
        String registro = App.nombreJugador;
        Score nuevoScore = new Score(registro, App.puntajeGlobal, App.nivelSeleccionado, totalIntentos);

        // --- GESTIÓN DE ENVÍO A BASE DE DATOS ---
        // Le pedimos al DAO que cree (inserte) este nuevo puntaje
        String resultado = dao.create(nuevoScore);
        
        // Si hay un error en la conexión, mostramos una alerta visual al usuario
        if ("ERROR".equals(resultado)) {
            // Platform.runLater asegura que la alerta se muestre correctamente en la interfaz
            Platform.runLater(() -> {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error de Sistema");
                alerta.setHeaderText("No se pudo guardar el progreso");
                alerta.setContentText("Ocurrió un problema al conectar con la base de datos. Inténtalo de nuevo.");
                alerta.showAndWait();
            });
        } else {
            // Si se guardó bien, refrescamos la lista para que aparezca el nuevo puntaje
            System.out.println("Guardado exitoso: " + resultado);
            lista.setAll(dao.readAll());
        }
    }

    /**
     * Método para el botón "Volver"
     */
    @FXML
    private void volver() throws IOException {
        App.setRoot("levels");
    }
}