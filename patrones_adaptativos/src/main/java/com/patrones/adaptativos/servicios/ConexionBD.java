package com.patrones.adaptativos.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase ConexionBD: Se encarga de establecer el enlace físico con MySQL.
 * Utiliza el patrón Singleton para garantizar que solo exista una instancia de gestión.
 */
public class ConexionBD {
    
    /**
     * Bloque estático: Se ejecuta una sola vez cuando la clase se carga en memoria.
     * Sirve para registrar el "Driver" (el traductor) de MySQL para que Java lo reconozca.
     */
    static {
        try {
            // Buscamos el conector de MySQL en las librerías del proyecto
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Si no se encuentra (por ejemplo, falta el JAR), avisamos por consola
            System.err.println("No se encontró el driver de MySQL: " + e.getMessage());
        }
    }

    // Atributos de configuración para localizar la base de datos
    private static ConexionBD instancia; // La única instancia permitida de esta clase
    private static final String URL = "jdbc:mysql://localhost:3306/patrones_adaptativos"; // Dirección del servidor y nombre de la BD
    private static final String USER = "camilo";     // Usuario configurado en MySQL
    private static final String PASS = "Admin123*";  // Contraseña de acceso

    /**
     * Constructor privado: Al ser privado, nadie fuera de esta clase puede hacer un "new ConexionBD()".
     * Esto obliga a usar el método getInstancia().
     */
    private ConexionBD() {}

    /**
     * getInstancia: Método para obtener la "llave única" de conexión.
     * 'synchronized' asegura que si dos partes del juego piden la conexión al mismo tiempo, 
     * no haya choques ni errores.
     */
    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD(); // Creamos la instancia solo la primera vez que se pide
        }
        return instancia;
    }

    /**
     * getConexion: Es el método que finalmente entrega un objeto 'Connection'.
     * @return Una conexión activa lista para enviar comandos SQL.
     * @throws SQLException Si los datos (usuario/pass) están mal o el servidor está apagado.
     */
    public Connection getConexion() throws SQLException {
        // DriverManager usa la URL, el USER y el PASS para intentar abrir la conexión
        return DriverManager.getConnection(URL, USER, PASS);
    }
}