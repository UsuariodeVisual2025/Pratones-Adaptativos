package com.patrones.adaptativos.modelo; // <-- Asegúrate que esté en la carpeta 'modelo'

public class Intento {

    private int intento;
    private String respuesta;
    private String resultado;

    // Constructor completo
    public Intento(int intento, String respuesta, String resultado) {
        this.intento = intento;
        this.respuesta = respuesta;
        this.resultado = resultado;
    }

    // Getters públicos (Indispensables para que la TableView de JavaFX los lea)
    public int getIntento() {
        return intento;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public String getResultado() {
        return resultado;
    }

    // Opcional: Métodos Setters por si más adelante necesitas modificar un intento
    public void setIntento(int intento) { this.intento = intento; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
    public void setResultado(String resultado) { this.resultado = resultado; }
}