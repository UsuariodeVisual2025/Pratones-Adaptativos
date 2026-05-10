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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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

    private final ObservableList<Intento> lista = FXCollections.observableArrayList();
    private final PerfilJugador perfil = new PerfilJugador();
    private Reglas reglas; 

    private int intentos = 0;      
    private int valorActual;       
    private int reglaActual;       
    private int contadorRegla;    
    private int puntaje;           
    
    private String nombreJugador;
    private int nivelSeleccionado;

    @FXML
    public void initialize() {
        colIntento.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIntento()).asObject());
        colRespuesta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRespuesta()));
        colResultado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getResultado()));
        
        colResultado.setCellFactory(column -> new TableCell<Intento, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    setText(item);
                    setStyle(item.equals("Correcto") ? 
                        "-fx-text-fill: #2ecc71; -fx-font-weight: bold;" : 
                        "-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                } else {
                    setText(null); setStyle("");
                }
            }
        });

        tablaIntentos.setItems(lista);
    }

    public void inicializarDatos(String nombre, int nivel) {
        this.nombreJugador = nombre;
        this.nivelSeleccionado = nivel;
        this.puntaje = 0;
        this.reglas = new Reglas();
        
        actualizarPuntajeUI();
        cargarNivel();
    }

    private void cargarNivel() {
        valorActual = reglas.obtenerUltimoNumero(nivelSeleccionado);
        reglaActual = 1;
        contadorRegla = 0;
        intentos = 0;
        
        if (patronLabel != null) {
            patronLabel.setText("Adivina el patrón: " + reglas.obtenerPatron(nivelSeleccionado));
        }
        if (statusLabel != null) {
            statusLabel.setText("Nivel " + nivelSeleccionado + " activo.");
        }
    }

    @FXML
    public void comprobar() throws IOException { // Cambiado a public para evitar advertencias
        String texto = respuestaField.getText().trim();
        if (texto.isEmpty()) return;

        int respuesta;
        try {
            respuesta = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Ingresa solo números.");
            return;
        }

        intentos++;
        
        // CORRECCIÓN AQUÍ: Se cambió applyRule por aplicarRegla
        int esperado = reglas.aplicarRegla(nivelSeleccionado, valorActual, reglaActual, perfil.getTasaExito());

        if (respuesta == esperado) {
            puntaje += 10;
            perfil.registrarIntento(true);
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Correcto"));
            statusLabel.setText("¡Excelente!");
            valorActual = respuesta;
            contadorRegla++;

            if (contadorRegla == 3) {
                if (nivelSeleccionado < 4) {
                    nivelSeleccionado++;
                    statusLabel.setText("¡Subiste de nivel!");
                    cargarNivel();
                } else {
                    patronLabel.setText("🎉 ¡VICTORIA TOTAL!");
                    statusLabel.setText("¡Felicidades, " + nombreJugador + "!");
                    respuestaField.setDisable(true);
                    
                    ScoresController.registrarPuntajeFinal(nombreJugador, puntaje, nivelSeleccionado);
                    mostrarAlertaGanador();
                }
            }
        } else {
            puntaje -= 5;
            perfil.registrarIntento(false);
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Incorrecto"));
            statusLabel.setText("Inténtalo de nuevo.");
        }
        
        actualizarPuntajeUI();
        respuestaField.clear();
        if (!respuestaField.isDisable()) {
            respuestaField.requestFocus();
        }
    }

    private void mostrarAlertaGanador() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("¡Juego Completado!");
        alert.setHeaderText("¡ERES UN MAESTRO DE LOS PATRONES!");
        alert.setContentText("Increíble trabajo, " + nombreJugador + ".\n" +
                           "Has superado el Nivel 4.\n" +
                           "Puntaje final: " + puntaje);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    App.setRoot("scores");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void actualizarPuntajeUI() {
        if (puntajeLabel != null) {
            puntajeLabel.setText("Puntaje: " + puntaje);
        }
    }

    @FXML
    public void volverNiveles() throws IOException { // Cambiado a public
        ScoresController.registrarPuntajeFinal(nombreJugador, puntaje, nivelSeleccionado);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/levels.fxml"));
        Parent root = loader.load();
        
        LevelsController controller = loader.getController();
        controller.recibirNombreJugador(nombreJugador);

        App.getPrimaryStage().setScene(new Scene(root, 800, 640));
    }

    @FXML
    public void reiniciar() { // Cambiado a public
        this.puntaje = 0;
        this.lista.clear();
        this.respuestaField.setDisable(false);
        actualizarPuntajeUI();
        cargarNivel();
        statusLabel.setText("Juego reiniciado.");
    }
}