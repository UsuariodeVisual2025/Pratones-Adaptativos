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

/**
 * GameController: Es el "Director de Orquesta" de la pantalla de juego.
 * Su trabajo es conectar lo que el usuario ve (FXML) con la lógica matemática.
 */
public class GameController {
    
    // --- ELEMENTOS DE LA INTERFAZ (VISTA) ---
    @FXML private Label patronLabel;      // Etiqueta que muestra la secuencia (ej. 2, 4, 6...)
    @FXML private Label statusLabel;      // Etiqueta para mensajes como "¡Correcto!" o "Error"
    @FXML private Label puntajeLabel;     // Etiqueta que muestra los puntos acumulados
    @FXML private TextField respuestaField; // Cuadro de texto para que el usuario escriba
    @FXML private TableView<Intento> tablaIntentos; // La tabla que registra el historial
    @FXML private TableColumn<Intento, Integer> colIntento;  // Columna del número de intento
    @FXML private TableColumn<Intento, String> colRespuesta; // Columna de la cifra escrita
    @FXML private TableColumn<Intento, String> colResultado; // Columna de "Correcto/Incorrecto"

    // --- VARIABLES DE LÓGICA Y DATOS ---
    // ObservableList: Es una lista "inteligente". Si agregas algo aquí, la tabla se actualiza sola.
    private ObservableList<Intento> lista = FXCollections.observableArrayList();
    
    // PerfilJugador: Guarda estadísticas del usuario (como su tasa de éxito).
    private PerfilJugador perfil = new PerfilJugador();
    
    // Reglas: Contiene las fórmulas matemáticas de los patrones.
    private Reglas reglas; 

    private int intentos = 0;      // Contador de cuántas veces ha pulsado "Comprobar"
    private int valorActual;       // Guarda el último número válido de la secuencia
    private int reglaActual;       // Define qué operación matemática se está aplicando
    private int contadorRegla;     // Cuenta aciertos seguidos para subir de dificultad
    private int puntaje;           // El puntaje del usuario en la sesión actual

    /**
     * initialize(): Se ejecuta automáticamente al cargar la pantalla.
     * Es como el "set-up" o configuración inicial del juego.
     */
    @FXML
    public void initialize() {
        // Traemos el puntaje que el usuario ya tenía de niveles anteriores
        this.puntaje = App.puntajeGlobal; 
        
        // Creamos la instancia de Reglas para acceder a las secuencias
        this.reglas = new Reglas(); 
        
        // Configuración de las columnas: Le decimos a cada columna qué dato mostrar de la clase 'Intento'
        colIntento.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIntento()).asObject());
        colRespuesta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRespuesta()));
        colResultado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getResultado()));
        
        // Formato visual: Si el texto es "Correcto" se pone verde, si no, se pone rojo
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

        // Vinculamos la lista de datos a la tabla y cargamos el primer nivel
        tablaIntentos.setItems(lista);
        actualizarPuntajeUI();
        cargarNivel();
    }

    /**
     * cargarNivel(): Prepara la pantalla según el nivel (1, 2, 3 o 4).
     */
    private void cargarNivel() {
        int nivel = App.nivelSeleccionado;
        
        // Obtenemos el último número visible de la secuencia para saber desde dónde empezar
        valorActual = reglas.obtenerUltimoNumero(nivel);
        reglaActual = 1;
        contadorRegla = 0;
        intentos = 0;
        
        // Actualizamos las etiquetas de la pantalla
        patronLabel.setText("Adivina el patrón: " + reglas.obtenerPatron(nivel));
        statusLabel.setText("Nivel " + nivel + " activo.");
    }

    /**
     * comprobar(): Es el método principal. Se activa al dar clic en "COMPROBAR".
     */
    @FXML
    private void comprobar() {
        int respuesta;
        try {
            // Intentamos convertir el texto del usuario a un número entero
            respuesta = Integer.parseInt(respuestaField.getText());
        } catch (NumberFormatException e) {
            // Si el usuario escribió letras o dejó vacío, mostramos error
            statusLabel.setText("Error: Ingresa solo números.");
            return;
        }

        intentos++; // Aumentamos el contador de intentos
        
        // Le pedimos a 'reglas' que calcule cuál es el número que debería seguir
        int esperado = reglas.aplicarRegla(App.nivelSeleccionado, valorActual, reglaActual, perfil.getTasaExito());

        // Lógica de validación
        if (respuesta == esperado) {
            // CASO ACIERTO
            puntaje += 10;
            perfil.registrarIntento(true); // Registramos éxito en el perfil
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Correcto")); // Añadimos a la tabla
            statusLabel.setText("¡Correcto!");
            valorActual = respuesta; // El nuevo número base es el que acaba de adivinar
            contadorRegla++;

            // Si adivina 3 seguidos, el juego se vuelve más difícil
            if (contadorRegla == 3) {
                if (App.nivelSeleccionado < 4) {
                    App.nivelSeleccionado++; // Aumentamos nivel
                    statusLabel.setText("¡Felicidades! Dificultad aumentada.");
                    cargarNivel(); // Reiniciamos la pantalla con el nuevo nivel
                } else {
                    // Si ya pasó el nivel 4, gana el juego
                    patronLabel.setText("🎉 ¡Juego Completado!");
                    ScoresController.registrarPuntajeFinal();
                }
            }
        } else {
            // CASO FALLO
            puntaje -= 5;
            perfil.registrarIntento(false); // Registramos fallo en el perfil
            lista.add(new Intento(intentos, String.valueOf(respuesta), "Incorrecto"));
            statusLabel.setText("Incorrecto.");
        }
        
        // Guardamos el puntaje en la App para que no se pierda al cambiar de ventana
        App.puntajeGlobal = puntaje; 
        actualizarPuntajeUI();
        respuestaField.clear(); // Limpiamos el cuadro para el siguiente intento
    }

    /**
     * actualiza la etiqueta de puntaje en la interfaz
     */
    private void actualizarPuntajeUI() {
        if (puntajeLabel != null) puntajeLabel.setText("Puntaje: " + puntaje);
    }

    /**
     * Vuelve a la pantalla de selección de niveles
     */
    @FXML
    private void volverNiveles() throws IOException { 
        ScoresController.registrarPuntajeFinal(); // Guarda el puntaje en el historial antes de salir
        App.setRoot("levels"); // Cambia la vista
    }

    /**
     * Resetea los valores para empezar de cero
     */
    @FXML
    private void reiniciar() {
        puntaje = 0; 
        App.puntajeGlobal = 0; 
        lista.clear(); // Limpia el historial de la tabla
        actualizarPuntajeUI(); 
        cargarNivel(); // Recarga el nivel actual
    }
}