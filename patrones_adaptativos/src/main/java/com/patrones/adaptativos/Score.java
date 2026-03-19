package com.patrones.adaptativos;

public class Score {

    private String jugador;
    private int puntaje;

    public Score(String jugador, int puntaje) {
        this.jugador = jugador;
        this.puntaje = puntaje;
    }

    public String getJugador() {
        return jugador;
    }

    public int getPuntaje() {
        return puntaje;
    }
}