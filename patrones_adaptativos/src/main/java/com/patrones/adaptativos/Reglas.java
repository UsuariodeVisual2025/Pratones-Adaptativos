package com.patrones.adaptativos;

public class Reglas {

    // 🔹 Patrón inicial
    public static String obtenerPatron(int nivel) {

        switch (nivel) {
            case 1: return "2 4 6 ?";
            case 2: return "3 6 12 ?";
            case 3: return "1 1 2 3 ?";
            case 4: return "5 10 20 40 ?";
            default: return "Finalizaste el juego Felicidades!";
        }
    }

    // 🔹 Último número del patrón
    public static int obtenerUltimoNumero(int nivel) {

        switch (nivel) {
            case 1: return 6;
            case 2: return 12;
            case 3: return 3;
            case 4: return 40;
            default: return 0;
        }
    }

    // 🔥 APLICAR REGLA
    public static int aplicarRegla(int nivel, int actual, int regla) {

        switch (nivel) {

            case 1:
                return (regla == 1) ? actual + 2 : actual - 2;

            case 2:
                return (regla == 1) ? actual * 2 : (actual * 2) + 2;

            case 3:
                return (actual % 2 == 0) ? actual * 3 : actual + 3;

            case 4:
                return (regla == 1)
                        ? actual * (actual + 1)
                        : (actual * (actual + 1)) / 2;

            default:
                return actual;
        }
    }

    // 🔥 DESCRIPCIÓN DE REGLAS (si la quieres usar después)
    public static String obtenerDescripcionRegla(int nivel, int regla) {

        switch (nivel) {

            case 1:
                return (regla == 1) ? "n + 2" : "n - 2";

            case 2:
                return (regla == 1) ? "n * 2" : "(n * 2) + 2";

            case 3:
                return "si es impar +3, si es par *3";

            case 4:
                return (regla == 1) ? "n(n+1)" : "n(n+1)/2";

            default:
                return "Regla desconocida";
        }
    }

    //  
    public static String obtenerPista(int nivel, int actual, int regla) {

        int a = actual;
        int b = aplicarRegla(nivel, a, regla);
        int c = aplicarRegla(nivel, b, regla);

        return a + " " + b + " " + c + " ?";
    }
}