package com.patrones.adaptativos.modelo;

/**
 * Clase Score: Representa el resultado final de una partida.
 * Se utiliza tanto para mostrar datos en la tabla como para enviarlos a la base de datos.
 */
public class Score {
    // Atributos que coinciden con las columnas de tu tabla en la base de datos
    private int id;              // Identificador único (llave primaria)
    private String jugador;      // El apodo del usuario
    private int puntaje;         // Puntos acumulados en la sesión
    private int nivelAlcanzado;  // El nivel máximo en el que estuvo el jugador
    private int intentosTotales; // Cantidad de veces que interactuó con el sistema

    /**
     * Constructor 1: Se usa para LEER datos.
     * Cuando traemos información de la base de datos, ya conocemos el ID asignado.
     */
    public Score(int id, String jugador, int puntaje, int nivelAlcanzado, int intentosTotales) {
        this.id = id;
        this.jugador = jugador;
        this.puntaje = puntaje;
        this.nivelAlcanzado = nivelAlcanzado;
        this.intentosTotales = intentosTotales;
    }

    /**
     * Constructor 2: Se usa para GUARDAR datos nuevos.
     * No incluimos el ID porque la base de datos lo genera automáticamente (Auto-incremental).
     */
    public Score(String jugador, int puntaje, int nivelAlcanzado, int intentosTotales) {
        this.jugador = jugador;
        this.puntaje = puntaje;
        this.nivelAlcanzado = nivelAlcanzado;
        this.intentosTotales = intentosTotales;
    }

    // --- GETTERS Y SETTERS ---
    // Permiten que JavaFX y el DAO accedan a la información privada de forma segura.

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