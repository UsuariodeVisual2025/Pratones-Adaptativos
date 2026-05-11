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
        contadorRegla = 0; 
        intentos = 0;
        lista.clear();
        
        if (patronLabel != null) {
            patronLabel.setText("Serie: " + reglas.obtenerPatron(nivelSeleccionado));
        }
        if (statusLabel != null) {
            statusLabel.setText("Nivel " + nivelSeleccionado + " activo. ¡Descubre el patrón!");
        }
    }

    @FXML
    public void comprobar() throws IOException {
        String texto = respuestaField.getText().trim();
        if (texto.isEmpty()) return;

        int respuesta;
        try {
            long validacion = Long.parseLong(texto);
            if (validacion > Integer.MAX_VALUE || validacion < Integer.MIN_VALUE) {
                throw new NumberFormatException();
            }
            respuesta = (int) validacion;
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Número inválido o demasiado grande.");
            return;
        }

        intentos++;
        int esperado = reglas.aplicarRegla(nivelSeleccionado, valorActual, contadorRegla, perfil.getTasaExito());

        if (respuesta == esperado) {
            puntaje += 10;
            perfil.registrarIntento(true);
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Correcto"));
            
            valorActual = respuesta;
            contadorRegla++; 

            String serieVisible = patronLabel.getText().replace("...", "");
            
            // --- SISTEMA DE PISTAS AUTOMÁTICAS (Fase A -> Fase B) ---
            
            // PISTA NIVEL 1: Inyecta el 18 tras acertar el 20
            if (nivelSeleccionado == 1 && contadorRegla == 3) {
                int pistaN1 = respuesta - 2; 
                patronLabel.setText(serieVisible + ", " + respuesta + ", " + pistaN1 + "...");
                valorActual = pistaN1;
                statusLabel.setText("¡El patrón cambió! analiza la serie.");
            } 
            // PISTA NIVEL 2: Inyecta el 258 tras acertar el 128 (Regla: n * 2 + 2)
            else if (nivelSeleccionado == 2 && contadorRegla == 3) {
                int pistaN2 = (respuesta * 2) + 2; 
                patronLabel.setText(serieVisible + ", " + respuesta + ", " + pistaN2 + "...");
                valorActual = pistaN2;
                statusLabel.setText("¡El patrón ha evolucionado! Analiza la serie.");
            }
            // PISTA NIVEL 4: Inyecta el 17 tras acertar el 22
            else if (nivelSeleccionado == 4 && contadorRegla == 3) {
                int pistaN4 = respuesta - 5; 
                patronLabel.setText(serieVisible + ", " + respuesta + ", " + pistaN4 + "...");
                valorActual = pistaN4;
                statusLabel.setText("¡Giro final! Analiza el nuevo número.");
            } 
            else {
                patronLabel.setText(serieVisible + ", " + respuesta + "...");
                statusLabel.setText("¡Correcto! Sigue así.");
            }

            // --- CONTROL DE PROGRESO ---
            if (contadorRegla >= 6) {
                if (nivelSeleccionado < 4) {
                    nivelSeleccionado++;
                    statusLabel.setText("¡Nivel superado! Cargando siguiente...");
                    cargarNivel();
                } else {
                    finalizarJuego();
                }
            }
            
        } else {
            puntaje -= 5;
            perfil.registrarIntento(false);
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Incorrecto"));
            statusLabel.setText("Incorrecto. Observa los números anteriores.");
        }
        
        actualizarPuntajeUI();
        respuestaField.clear();
        if (!respuestaField.isDisable()) {
            respuestaField.requestFocus();
        }
    }

    private void finalizarJuego() {
        patronLabel.setText("🎉 ¡VICTORIA TOTAL!");
        statusLabel.setText("¡Felicidades, " + nombreJugador + "!");
        respuestaField.setDisable(true);
        ScoresController.registrarPuntajeFinal(nombreJugador, puntaje, nivelSeleccionado);
        mostrarAlertaGanador();
    }

    private void mostrarAlertaGanador() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Juego Completado");
        alert.setHeaderText("¡MAESTRO DE PATRONES!");
        alert.setContentText("Usuario: " + nombreJugador + "\nPuntaje final: " + puntaje);

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
    public void volverNiveles() throws IOException {
        ScoresController.registrarPuntajeFinal(nombreJugador, puntaje, nivelSeleccionado);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/levels.fxml"));
        Parent root = loader.load();
        LevelsController controller = loader.getController();
        controller.recibirNombreJugador(nombreJugador);
        App.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    public void reiniciar() {
        this.puntaje = 0;
        this.lista.clear();
        this.respuestaField.setDisable(false);
        actualizarPuntajeUI();
        cargarNivel();
    }
}