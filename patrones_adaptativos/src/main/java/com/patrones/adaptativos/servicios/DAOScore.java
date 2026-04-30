package com.patrones.adaptativos.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.patrones.adaptativos.modelo.Score;

/**
 * DAOScore: Implementa las operaciones de la interfaz CRUD para el modelo Score.
 * Se encarga de la comunicación directa con la tabla 'scores' en MySQL.
 */
public class DAOScore implements CRUD<Score> {

    /**
     * create: Toma un objeto Score y lo guarda permanentemente en la base de datos.
     * @param score El objeto con el nombre, puntaje, nivel e intentos del jugador.
     * @return "SUCCESS" si se guardó bien, "ERROR" si algo falló.
     */
    @Override
    public String create(Score score) {
        // La sentencia SQL con signos de pregunta (?) para evitar inyecciones de código malicioso
        String sql = "INSERT INTO scores (jugador, puntaje, nivel, intentos) VALUES (?, ?, ?, ?)";
        
        // El try-with-resources asegura que la conexión se cierre sola al terminar, evitando fugas de memoria
        try (Connection con = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Reemplazamos los signos '?' por los datos reales del objeto score
            ps.setString(1, score.getJugador());
            ps.setInt(2, score.getPuntaje());
            ps.setInt(3, score.getNivelAlcanzado());
            ps.setInt(4, score.getIntentosTotales());
            
            // Ejecutamos la orden en la base de datos
            ps.executeUpdate();
            return "SUCCESS"; // Señal para que el controlador sepa que todo salió bien
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR";   // Señal para que el controlador dispare la alerta visual
        }
    }

    /**
     * readAll: Consulta la base de datos para obtener todos los puntajes registrados.
     * @return Una lista de objetos Score para llenar la tabla visual.
     */
    @Override
    public List<Score> readAll() {
        List<Score> lista = new ArrayList<>();
        String sql = "SELECT id, jugador, puntaje, nivel, intentos FROM scores";
        
        try (Connection con = ConexionBD.getInstancia().getConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Recorremos los resultados de la base de datos fila por fila
            while (rs.next()) {
                // Por cada fila, creamos un nuevo objeto Score en Java
                Score s = new Score(
                    rs.getInt("id"),
                    rs.getString("jugador"),
                    rs.getInt("puntaje"),
                    rs.getInt("nivel"),
                    rs.getInt("intentos")
                );
                // Lo añadimos a nuestra lista
                lista.add(s);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Si hay error, devolvemos la lista vacía para que la app no se bloquee
        }
        return lista;
    }
}