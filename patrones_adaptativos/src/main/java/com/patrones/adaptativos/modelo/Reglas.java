package com.patrones.adaptativos.modelo; // <-- Paquete correcto

public class Reglas {

    // 🔹 Patrón inicial mostrado al usuario
    public static String obtenerPatron(int nivel) {
        return switch (nivel) {
            case 1 -> "2 4 6 ?";
            case 2 -> "3 6 12 ?";
            case 3 -> "1 1 2 3 ?";
            case 4 -> "5 10 20 40 ?";
            default -> "Finalizaste el juego, ¡Felicidades!";
        };
    }

    // 🔹 Último número del patrón
    public static int obtenerUltimoNumero(int nivel) {
        return switch (nivel) {
            case 1 -> 6;
            case 2 -> 12;
            case 3 -> 3;
            case 4 -> 40;
            default -> 0;
        };
    }

    // 🔥 APLICAR REGLA (Lógica matemática del juego)
    public static int aplicarRegla(int nivel, int actual, int regla) {
        return switch (nivel) {
            case 1 -> (regla == 1) ? actual + 2 : actual - 2;
            case 2 -> (regla == 1) ? actual * 2 : (actual * 2) + 2;
            case 3 -> (actual % 2 == 0) ? actual * 3 : actual + 3;
            case 4 -> (regla == 1) ? actual * 2 : actual + 10; // Ajustado para ser coherente con el patrón 5, 10, 20...
            default -> actual;
        };
    }

    // 🔥 DESCRIPCIÓN DE REGLAS (Para mostrar al usuario)
    public static String obtenerDescripcionRegla(int nivel, int regla) {
        return switch (nivel) {
            case 1 -> (regla == 1) ? "n + 2" : "n - 2";
            case 2 -> (regla == 1) ? "n * 2" : "(n * 2) + 2";
            case 3 -> "si es par *3, si es impar +3";
            case 4 -> (regla == 1) ? "n * 2" : "n + 10";
            default -> "Regla desconocida";
        };
    }

    // 🔥 Genera una pista basada en el estado actual
    public static String obtenerPista(int nivel, int actual, int regla) {
        int a = actual;
        int b = aplicarRegla(nivel, a, regla);
        int c = aplicarRegla(nivel, b, regla);
        return a + " " + b + " " + c + " ?";
    }
}