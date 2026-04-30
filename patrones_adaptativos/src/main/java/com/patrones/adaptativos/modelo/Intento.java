package com.patrones.adaptativos.modelo; // Indica que este archivo pertenece a la capa de datos (modelo)

/**
 * Clase Intento: Es un modelo de datos (POJO).
 * Representa una única fila en la tabla de intentos que ve el jugador.
 */
public class Intento {

    // Atributos: Son las características que definen a un intento
    private int intento;      // El número de la jugada (1, 2, 3...)
    private String respuesta; // Lo que el usuario escribió en el cuadro de texto
    private String resultado; // El veredicto: "Correcto" o "Incorrecto"

    /**
     * Constructor completo:
     * Sirve para crear el objeto con todos sus datos de una sola vez.
     */
    public Intento(int intento, String respuesta, String resultado) {
        this.intento = intento;
        this.respuesta = respuesta;
        this.resultado = resultado;
    }

    /**
     * Getters públicos:
     * ¡OJO! Son indispensables para JavaFX. 
     * La TableView usa estos métodos automáticamente para "dibujar" el texto en las celdas.
     */
    public int getIntento() {
        return intento;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public String getResultado() {
        return resultado;
    }

    /**
     * Setters:
     * Permiten modificar los datos de un intento que ya fue creado.
     */
    public void setIntento(int intento) { 
        this.intento = intento; 
    }
    
    public void setRespuesta(String respuesta) { 
        this.respuesta = respuesta; 
    }
    
    public void setResultado(String resultado) { 
        this.resultado = resultado; 
    }
}