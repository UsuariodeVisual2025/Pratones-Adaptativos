package com.patrones.adaptativos.controlador;

import java.io.IOException;

import com.patrones.adaptativos.vista.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

/**
 * PrimaryController: Gestiona la pantalla de inicio y la validación del perfil inicial.
 */
public class PrimaryController {

    @FXML
    private TextField nombreField;

    /**
     * switchToSecondary(): Valida el nombre e inicia la transición hacia el tutorial/niveles.
     * Utiliza una instancia del siguiente controlador para pasar datos de forma segura.
     */
    @FXML
    private void switchToSecondary() throws IOException {
        System.out.println("--- INICIANDO VALIDACIÓN DEL APODO ---");

        // Verificación de seguridad del nodo FXML
        if (nombreField == null) {
            System.err.println("ERROR CRÍTICO: nombreField es nulo. Revisa el fx:id en el FXML.");
            return;
        }

        String nombre = nombreField.getText().trim();
        System.out.println("Texto ingresado: '" + nombre + "'");

        // --- VALIDACIONES DE NEGOCIO ---
        
        // 1. Validar si está vacío
        if (nombre.isEmpty()) {
            mostrarAlerta("Error de validación", "El nombre no puede estar vacío.");
            return;
        }

        // 2. Validar longitud (Máximo 10 caracteres para la base de datos)
        if (nombre.length() > 10) {
            mostrarAlerta("Error de longitud", "El apodo no puede tener más de 10 caracteres.");
            return;
        }

        // 3. Validar solo letras (Evita inyecciones extrañas o números en el nombre)
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            mostrarAlerta("Error de caracteres", "El apodo solo debe contener letras.");
            return;
        }
        
        try {
            // --- NAVEGACIÓN BASADA EN INSTANCIAS ---
            
            // Cargamos el FXML del tutorial o selección de niveles
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/tutorial.fxml"));
            Parent root = loader.load();

            // Obtenemos la INSTANCIA del controlador de la siguiente pantalla
            // Nota: Asegúrate de que el controlador en tutorial.fxml sea el correcto (ej. LevelsController o TutorialController)
            LevelsController nextController = loader.getController();
            
            // Pasamos el dato a la instancia del nuevo controlador
            nextController.recibirNombreJugador(nombre);
            
            // Cambiamos la escena en el escenario principal
            Scene nuevaEscena = new Scene(root, 800, 640);
            App.getPrimaryStage().setScene(nuevaEscena);
            
            System.out.println("Apodo validado con éxito. Jugador: " + nombre);
            
        } catch (IOException e) {
            System.err.println("Error al cargar la siguiente pantalla: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error de Sistema", "No se pudo cargar la siguiente interfaz.");
        }
    }

    /**
     * Muestra una ventana emergente de error vinculada a la ventana principal.
     */
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        
        // Vinculamos la alerta al stage principal para que aparezca centrada
        alerta.initOwner(App.getPrimaryStage());
        alerta.showAndWait();
    }
    
    /**
     * Método opcional para cerrar la aplicación desde la pantalla principal.
     */
    @FXML
    private void salirApp() {
        System.out.println("Cerrando aplicación...");
        System.exit(0);
    }
}