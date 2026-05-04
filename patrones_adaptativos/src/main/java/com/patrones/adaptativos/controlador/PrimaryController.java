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

public class PrimaryController {

    @FXML
    private TextField nombreField;

    @FXML
    private void switchToSecondary() throws IOException {
        System.out.println("--- INICIANDO VALIDACIÓN DEL APODO ---");

        if (nombreField == null) {
            System.err.println("ERROR CRÍTICO: nombreField es nulo. Revisa el fx:id en el FXML.");
            return;
        }

        String nombre = nombreField.getText().trim();
        System.out.println("Texto ingresado: '" + nombre + "'");

        // 1. Validar si está vacío
        if (nombre.isEmpty()) {
            mostrarAlerta("Error de validación", "El nombre no puede estar vacío.");
            return;
        }

        // 2. Validar longitud
        if (nombre.length() > 10) {
            mostrarAlerta("Error de longitud", "El apodo no puede tener más de 10 caracteres.");
            return;
        }

        // 3. Validar solo letras
        if (!nombre.matches("^[a-zA-Z]+$")) {
            mostrarAlerta("Error de caracteres", "El apodo solo debe contener letras (sin números).");
            return;
        }
       
        // Carga la siguiente vista y le pasa el nombre al controlador de destino sin usar variables estáticas
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/patrones/adaptativos/tutorial.fxml"));
        Parent root = loader.load();

        LevelsController controller = loader.getController();
        controller.recibirNombreJugador(nombre);
       
        // Cambiamos la escena de forma segura en la ventana principal
        App.getPrimaryStage().setScene(new Scene(root, 800, 600));
        System.out.println("Apodo validado con éxito y enviado al tutorial. Nombre: " + nombre);
    }

    /**
     * Muestra una ventana emergente en caso de error.
     */
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.initOwner(App.getPrimaryStage());
        alerta.showAndWait();
    }
}