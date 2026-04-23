package com.patrones.adaptativos.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.patrones.adaptativos.modelo.Score;

public class DAOScore implements CRUD<Score> {

    @Override
    public String create(Score score) {
        String sql = "INSERT INTO scores (jugador, puntaje, nivel, intentos) VALUES (?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, score.getJugador());
            ps.setInt(2, score.getPuntaje());
            ps.setInt(3, score.getNivelAlcanzado());
            ps.setInt(4, score.getIntentosTotales());
            
            ps.executeUpdate();
            return "Puntaje guardado exitosamente en MySQL";
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al guardar: " + e.getMessage();
        }
    }

    @Override
    public List<Score> readAll() {
        return null; // Pendiente de implementar cuando necesites cargar la tabla
    }
}