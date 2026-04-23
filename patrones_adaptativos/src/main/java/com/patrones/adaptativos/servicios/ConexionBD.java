package com.patrones.adaptativos.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    // Bloque estático para cargar el driver manualmente sin errores de módulos
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver de MySQL: " + e.getMessage());
        }
    }

    private static ConexionBD instancia;
    private static final String URL = "jdbc:mysql://localhost:3306/patrones_adaptativos";
    private static final String USER = "camilo";
    private static final String PASS = "Admin123*";

    private ConexionBD() {}

    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) instancia = new ConexionBD();
        return instancia;
    }

    public Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}