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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GameController {
    
    // --- ELEMENTOS DE LA INTERFAZ ---
    @FXML private Label patronLabel;
    @FXML private Label statusLabel;
    @FXML private Label puntajeLabel;
    @FXML private TextField respuestaField;
    @FXML private TableView<Intento> tablaIntentos;
    @FXML private TableColumn<Intento, Integer> colIntento;
    @FXML private TableColumn<Intento, String> colRespuesta;
    @FXML private TableColumn<Intento, String> colResultado;

    // --- INSTANCIAS DE LÓGICA Y DATOS ---
    // Instanciamos la lista y el perfil aquí para que existan durante toda la partida
    private final ObservableList<Intento> lista = FXCollections.observableArrayList();
    private final PerfilJugador perfil = new PerfilJugador();
    private Reglas reglas; // Se instanciará al recibir los datos

    private int intentos = 0;      
    private int valorActual;       
    private int reglaActual;       
    private int contadorRegla;    
    private int puntaje;           
    
    private String nombreJugador;
    private int nivelSeleccionado;

    /**
     * initialize(): Configura la parte visual de la tabla al cargar el FXML.
     */
    @FXML
    public void initialize() {
        // Configuración de columnas
        colIntento.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIntento()).asObject());
        colRespuesta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRespuesta()));
        colResultado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getResultado()));
        
        // Formato de colores para la columna de resultado
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

    /**
     * Inyecta los datos necesarios e instancia la lógica de reglas.
     */
    public void inicializarDatos(String nombre, int nivel) {
        this.nombreJugador = nombre;
        this.nivelSeleccionado = nivel;
        this.puntaje = 0;
        
        // Instanciamos la lógica de reglas para esta partida
        this.reglas = new Reglas();
        
        actualizarPuntajeUI();
        cargarNivel();
        
        System.out.println("Juego iniciado: " + nombreJugador + " - Nivel: " + nivelSeleccionado);
    }

    private void cargarNivel() {
        // Usamos la instancia 'reglas' para obtener los datos del modelo
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
    private void comprobar() throws IOException {
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
        // Aplicamos la lógica desde la instancia de reglas y perfil
        int esperado = reglas.aplicarRegla(nivelSeleccionado, valorActual, reglaActual, perfil.getTasaExito());

        if (respuesta == esperado) {
            puntaje += 10;
            perfil.registrarIntento(true); // Actualiza la tasa de éxito en la instancia
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Correcto"));
            statusLabel.setText("¡Excelente!");
            valorActual = respuesta;
            contadorRegla++;

            // Lógica de progresión
            if (contadorRegla == 3) {
                if (nivelSeleccionado < 4) {
                    nivelSeleccionado++;
                    statusLabel.setText("¡Subiste de nivel!");
                    cargarNivel();
                } else {
                    patronLabel.setText("🎉 ¡Victoria total!");
                    ScoresController.registrarPuntajeFinal(nombreJugador, puntaje, nivelSeleccionado);
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
        respuestaField.requestFocus();
    }

    private void actualizarPuntajeUI() {
        if (puntajeLabel != null) {
            puntajeLabel.setText("Puntaje: " + puntaje);
        }
    }

    @FXML
    private void volverNiveles() throws IOException {
        // Guardar progreso antes de salir
        ScoresController.registrarPuntajeFinal(nombreJugador, puntaje, nivelSeleccionado);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/levels.fxml"));
        Parent root = loader.load();
        
        LevelsController controller = loader.getController();
        controller.recibirNombreJugador(nombreJugador);

        App.getPrimaryStage().setScene(new Scene(root, 800, 640));
    }

    @FXML
    private void reiniciar() {
        this.puntaje = 0;
        this.lista.clear();
        actualizarPuntajeUI();
        cargarNivel();
        statusLabel.setText("Juego reiniciado.");
    }
}