package com.patrones.adaptativos.modelo;

public class Score {
    private int id;
    private String jugador;
    private int puntaje;
    private int nivelAlcanzado;
    private int intentosTotales;

    // Constructor para cuando ya tienes el ID (ej: al hacer un SELECT de la BD)
    public Score(int id, String jugador, int puntaje, int nivelAlcanzado, int intentosTotales) {
        this.id = id;
        this.jugador = jugador;
        this.puntaje = puntaje;
        this.nivelAlcanzado = nivelAlcanzado;
        this.intentosTotales = intentosTotales;
    }

    // Constructor para cuando vas a crear un nuevo registro (sin ID, porque es auto-incremental)
    public Score(String jugador, int puntaje, int nivelAlcanzado, int intentosTotales) {
        this.jugador = jugador;
        this.puntaje = puntaje;
        this.nivelAlcanzado = nivelAlcanzado;
        this.intentosTotales = intentosTotales;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getNivelAlcanzado() {
        return nivelAlcanzado;
    }

    public void setNivelAlcanzado(int nivelAlcanzado) {
        this.nivelAlcanzado = nivelAlcanzado;
    }

    public int getIntentosTotales() {
        return intentosTotales;
    }

    public void setIntentosTotales(int intentosTotales) {
        this.intentosTotales = intentosTotales;
    }
}