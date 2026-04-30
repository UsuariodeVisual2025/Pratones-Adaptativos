package com.patrones.adaptativos.modelo;

/**
 * PerfilJugador: Gestiona las estadísticas de rendimiento del usuario.
 * Es la base para que el juego pueda adaptarse al nivel de habilidad del jugador.
 */
public class PerfilJugador {
    // Atributos privados para llevar la cuenta interna
    private int aciertos;      // Cantidad de respuestas correctas
    private int fallos;        // Cantidad de respuestas incorrectas
    private int totalIntentos; // Suma total de veces que ha comprobado una respuesta

    /**
     * Constructor: Inicializa todas las estadísticas en cero al empezar una nueva sesión.
     */
    public PerfilJugador() {
        this.aciertos = 0;
        this.fallos = 0;
        this.totalIntentos = 0;
    }

    /**
     * registrarIntento: Actualiza las estadísticas cada vez que el usuario juega.
     * @param esCorrecto Booleano que indica si la respuesta fue acertada o no.
     */
    public void registrarIntento(boolean esCorrecto) {
        totalIntentos++; // Independientemente del resultado, el contador total aumenta
        if (esCorrecto) {
            aciertos++; // Si acertó, sumamos a aciertos
        } else {
            fallos++;   // Si falló, sumamos a fallos
        }
    }

    /**
     * getTasaExito: Calcula el rendimiento del jugador.
     * @return Un número decimal entre 0.0 (0% éxito) y 1.0 (100% éxito).
     */
    public double getTasaExito() {
        // Validación para evitar el error matemático de "división por cero"
        if (totalIntentos == 0) return 0.0;
        
        // Realizamos la división: (aciertos / total)
        // Usamos (double) para que el resultado tenga decimales y sea preciso
        return (double) aciertos / totalIntentos;
    }

    /**
     * Getter para obtener la cantidad de aciertos desde otros controladores.
     */
    public int getAciertos() { 
        return aciertos; 
    }
}