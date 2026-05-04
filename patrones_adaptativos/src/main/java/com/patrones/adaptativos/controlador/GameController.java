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

/**
 * GameController: Es el "Director de Orquesta" de la pantalla de juego.
 * Su trabajo es conectar lo que el usuario ve (FXML) con la lógica matemática
 * manteniendo un diseño limpio y sin variables estáticas.
 */
public class GameController {
   
    // --- ELEMENTOS DE LA INTERFAZ (VISTA) ---
    @FXML private Label patronLabel;
    @FXML private Label statusLabel;
    @FXML private Label puntajeLabel;
    @FXML private TextField respuestaField;
    @FXML private TableView<Intento> tablaIntentos;
    @FXML private TableColumn<Intento, Integer> colIntento;
    @FXML private TableColumn<Intento, String> colRespuesta;
    @FXML private TableColumn<Intento, String> colResultado;

    // --- VARIABLES DE LÓGICA Y DATOS ---
    private ObservableList<Intento> lista = FXCollections.observableArrayList();
    private PerfilJugador perfil = new PerfilJugador();
    private Reglas reglas;

    private int intentos = 0;      
    private int valorActual;      
    private int reglaActual;      
    private int contadorRegla;    
    private int puntaje;          
   
    // Variables para reemplazar el estado estático
    private String nombreJugador;
    private int nivelSeleccionado;

    /**
     * Método para inicializar los datos desde el controlador de niveles (sin usar variables estáticas).
     */
    public void inicializarDatos(String nombre, int nivel) {
        this.nombreJugador = nombre;
        this.nivelSeleccionado = nivel;
        this.puntaje = 0; // Inicializamos puntaje en 0 por cada partida
       
        // Configuraciones iniciales dependientes de los parámetros
        this.reglas = new Reglas();
        actualizarPuntajeUI();
        cargarNivel();
       
        System.out.println("Juego inicializado para: " + this.nombreJugador + " en el nivel: " + this.nivelSeleccionado);
    }

    /**
     * initialize(): Se ejecuta automáticamente al cargar la pantalla.
     */
    @FXML
    public void initialize() {
        colIntento.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIntento()).asObject());
        colRespuesta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRespuesta()));
        colResultado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getResultado()));
       
        // Formato visual de la tabla
        colResultado.setCellFactory(column -> new TableCell<Intento, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    setText(item);
                    setStyle(item.equals("Correcto") ?
                        "-fx-text-fill: green; -fx-font-weight: bold;" :
                        "-fx-text-fill: red; -fx-font-weight: bold;");
                } else {
                    setText(null); setStyle("");
                }
            }
        });

        tablaIntentos.setItems(lista);
    }

    /**
     * cargarNivel(): Prepara la pantalla según el nivel actual.
     */
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

    /**
     * comprobar(): Es el método principal. Se activa al dar clic en "COMPROBAR".
     */
    @FXML
    private void comprobar() throws IOException {
        int respuesta;
        try {
            respuesta = Integer.parseInt(respuestaField.getText());
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Ingresa solo números.");
            return;
        }

        intentos++;
        int esperado = reglas.aplicarRegla(nivelSeleccionado, valorActual, reglaActual, perfil.getTasaExito());

        if (respuesta == esperado) {
            puntaje += 10;
            perfil.registrarIntento(true);
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Correcto"));
            statusLabel.setText("¡Correcto!");
            valorActual = respuesta;
            contadorRegla++;

            if (contadorRegla == 3) {
                if (nivelSeleccionado < 4) {
                    nivelSeleccionado++;
                    statusLabel.setText("¡Felicidades! Dificultad aumentada.");
                    cargarNivel();
                } else {
                    patronLabel.setText("🎉 ¡Juego Completado!");
                    // CORRECCIÓN AQUÍ: pasamos los parámetros requeridos
                    ScoresController.registrarPuntajeFinal(nombreJugador, puntaje, nivelSeleccionado);
                }
            }
        } else {
            puntaje -= 5;
            perfil.registrarIntento(false);
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Incorrecto"));
            statusLabel.setText("Incorrecto.");
        }
       
        actualizarPuntajeUI();
        respuestaField.clear();
    }

    private void actualizarPuntajeUI() {
        if (puntajeLabel != null) {
            puntajeLabel.setText("Puntaje: " + puntaje);
        }
    }

    /**
     * Vuelve a la pantalla de selección de niveles.
     */
    @FXML
    private void volverNiveles() throws IOException {
        // CORRECCIÓN AQUÍ: pasamos los parámetros requeridos antes de salir
        ScoresController.registrarPuntajeFinal(nombreJugador, puntaje, nivelSeleccionado);
       
        // Pasa los datos de vuelta a LevelsController de manera segura
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/levels.fxml"));
        Parent root = loader.load();
        LevelsController controller = loader.getController();
        controller.recibirNombreJugador(nombreJugador);

        App.getPrimaryStage().setScene(new Scene(root, 800, 600));
    }

    /**
     * Resetea los valores para empezar de cero sin variables estáticas.
     */
    @FXML
    private void reiniciar() {
        this.puntaje = 0;
        lista.clear();
        actualizarPuntajeUI();
        cargarNivel();
    }
}