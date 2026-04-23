package com.patrones.adaptativos.modelo;

public class Score {
    private String jugador;
    private int puntaje;
    private int nivelAlcanzado;
    private int intentosTotales;

    public Score(String jugador, int puntaje, int nivelAlcanzado, int intentosTotales) {
        this.jugador = jugador;
        this.puntaje = puntaje;
        this.nivelAlcanzado = nivelAlcanzado;
        this.intentosTotales = intentosTotales;
    }

    // Getters
    public String getJugador() {
        return jugador;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getNivelAlcanzado() {
        return nivelAlcanzado;
    }

    public int getIntentosTotales() {
        return intentosTotales;
    }

    // Setters (útiles si decides modificar algún objeto Score en el futuro)
    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public void setNivelAlcanzado(int nivelAlcanzado) {
        this.nivelAlcanzado = nivelAlcanzado;
    }

    public void setIntentosTotales(int intentosTotales) {
        this.intentosTotales = intentosTotales;
    }
}