package com.patrones.adaptativos.modelo;

public class PerfilJugador {
    private int aciertos;
    private int fallos;
    private int totalIntentos;

    public PerfilJugador() {
        this.aciertos = 0;
        this.fallos = 0;
        this.totalIntentos = 0;
    }

    public void registrarIntento(boolean esCorrecto) {
        totalIntentos++;
        if (esCorrecto) {
            aciertos++;
        } else {
            fallos++;
        }
    }

    // Calcula el rendimiento (0.0 a 1.0)
    public double getTasaExito() {
        if (totalIntentos == 0) return 0.0;
        return (double) aciertos / totalIntentos;
    }

    // Getters y setters si los necesitas
    public int getAciertos() { return aciertos; }
}