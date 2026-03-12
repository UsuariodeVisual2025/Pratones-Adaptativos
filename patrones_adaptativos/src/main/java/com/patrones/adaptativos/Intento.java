package com.patrones.adaptativos;

public class Intento {

    private int intento;
    private String respuesta;
    private String resultado;

    public Intento(int intento, String respuesta, String resultado) {
        this.intento = intento;
        this.respuesta = respuesta;
        this.resultado = resultado;
    }

    public int getIntento() {
        return intento;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public String getResultado() {
        return resultado;
    }
}