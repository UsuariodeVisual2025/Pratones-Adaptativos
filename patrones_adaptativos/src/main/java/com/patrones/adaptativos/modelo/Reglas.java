package com.patrones.adaptativos.modelo;

/**
 * Clase Reglas: Define la lógica matemática y los patrones de cada nivel.
 * Es la encargada de calcular qué número sigue en la secuencia.
 */
public class Reglas {

    /**
     * Constructor vacío: Permite crear una instancia de Reglas 
     * para acceder a sus métodos desde los controladores.
     */
    public Reglas() {
    }

    /**
     * obtenerPatron: Devuelve el texto inicial que el usuario verá en pantalla.
     * Ayuda a que el jugador entienda la lógica inicial del nivel.
     * @param nivel El nivel actual seleccionado.
     * @return Una cadena de texto con el ejemplo del patrón.
     */
    public String obtenerPatron(int nivel) {
        // Usamos un switch moderno para asignar el texto según el nivel
        return switch (nivel) {
            case 1 -> "2, 4, 6 ...";   // Nivel básico: incrementos de 2
            case 2 -> "3, 6, 12 ...";  // Nivel intermedio: duplicados
            case 3 -> "1, 3, 9 ...";   // Nivel avanzado: potencias de 3
            case 4 -> "5, 10, 20 ..."; // Nivel experto: duplicados grandes
            default -> "¡Completado!";
        };
    }

    /**
     * obtenerUltimoNumero: Define el punto de partida numérico para el cálculo.
     * Es el último valor visible antes de que el usuario empiece a adivinar.
     */
    public int obtenerUltimoNumero(int nivel) {
        return switch (nivel) {
            case 1 -> 6; 
            case 2 -> 12; 
            case 3 -> 9; 
            case 4 -> 20; 
            default -> 0;
        };
    }

    /**
     * aplicarRegla: El método más importante para la adaptabilidad.
     * Calcula el siguiente número esperado basándose en el nivel y el rendimiento.
     * 
     * @param nivel El nivel actual.
     * @param actual El último número de la secuencia.
     * @param regla Un identificador de sub-regla (para variar la lógica).
     * @param tasaExito El rendimiento del jugador (proviene de PerfilJugador).
     * @return El número que el sistema espera que el usuario escriba.
     */
    public int aplicarRegla(int nivel, int actual, int regla, double tasaExito) {
        // En nivel 1, la lógica es fija (siempre +2) para no frustrar al principiante
        if (nivel == 1) return actual + 2;
        
        /**
         * Lógica Adaptativa:
         * Si la tasa de éxito es superior al 80% (0.8), podríamos activar 
         * comportamientos más complejos en el futuro.
         */
        boolean modoExperto = tasaExito > 0.8;
        
        // Dependiendo del nivel y de la 'regla' activa, el cálculo cambia
        return switch (nivel) {
            case 2 -> (regla == 1) ? actual * 2 : actual + 2; // Alterna entre doblar o sumar 2
            case 3 -> (regla == 1) ? actual * 3 : actual + 3; // Alterna entre triplicar o sumar 3
            case 4 -> (regla == 1) ? actual * 2 : actual + 10; // Alterna entre doblar o sumar 10
            default -> actual + 2;
        };
    }
}