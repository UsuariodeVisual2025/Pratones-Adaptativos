package com.patrones.adaptativos.modelo;

public class Reglas {

    public String obtenerPatron(int nivel) {
        return switch (nivel) {
            case 1 -> "10, 12, 14..."; 
            case 2 -> "4, 8, 16...";   
            case 3 -> "7, 10, 30...";  
            case 4 -> "2, 6, 10...";   // Cambiado para ser manejable
            default -> "¡Serie completada!";
        };
    }

    public int obtenerUltimoNumero(int nivel) {
        return switch (nivel) {
            case 1 -> 14; 
            case 2 -> 16; 
            case 3 -> 30; 
            case 4 -> 10; // Empieza en 10
            default -> 0;
        };
    }

    public int aplicarRegla(int nivel, int actual, int contadorRegla, double tasaExito) {
        boolean faseA = (contadorRegla < 3);

        return switch (nivel) {
            case 1 -> faseA ? (actual + 2) : (actual - 2);
            case 2 -> faseA ? (actual * 2) : (actual * 2 + 2);
            case 3 -> (actual % 2 != 0) ? (actual + 3) : (actual * 3);
            case 4 -> faseA ? (actual + 4) : (actual - 5); // Regla fija y segura
            default -> actual + 2;
        };
    }
}