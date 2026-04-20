package com.patrones.adaptativos.modelo; // <-- Asegúrate de mantener este paquete

public class Score {

    private String jugador;
    private int puntaje;

    // Constructor completo
    public Score(String jugador, int puntaje) {
        this.jugador = jugador;
        this.puntaje = puntaje;
    }

    // Getters públicos (Obligatorios para la TableView)
    public String getJugador() {
        return jugador;
    }

    public int getPuntaje() {
        return puntaje;
    }

    // Opcional: Setters por si quieres modificar un puntaje más adelante
    public void setJugador(String jugador) { this.jugador = jugador; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }
}