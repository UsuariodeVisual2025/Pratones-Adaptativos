package com.patrones.adaptativos.modelo;

public class Reglas {

    public static String obtenerPatron(int nivel) {
        return switch (nivel) {
            case 1 -> "2, 4, 6 ...";
            case 2 -> "3, 6, 12 ...";
            case 3 -> "1, 3, 9 ...";
            case 4 -> "5, 10, 20 ...";
            default -> "¡Completado!";
        };
    }

    public static int obtenerUltimoNumero(int nivel) {
        return switch (nivel) {
            case 1 -> 6; case 2 -> 12; case 3 -> 9; case 4 -> 20; default -> 0;
        };
    }

    public static int aplicarRegla(int nivel, int actual, int regla, double tasaExito) {
        // En nivel 1, lógica fija para evitar confusión
        if (nivel == 1) return actual + 2;
        
        boolean modoExperto = tasaExito > 0.8;
        return switch (nivel) {
            case 2 -> (regla == 1) ? actual * 2 : actual + 2;
            case 3 -> (regla == 1) ? actual * 3 : actual + 3;
            case 4 -> (regla == 1) ? actual * 2 : actual + 10;
            default -> actual + 2;
        };
    }
}