package com.patrones.adaptativos.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            return "SUCCESS"; // Señal clara para el controlador
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR";   // Señal clara para que el controlador dispare la alerta
        }
    }

    @Override
    public List<Score> readAll() {
        List<Score> lista = new ArrayList<>();
        String sql = "SELECT id, jugador, puntaje, nivel, intentos FROM scores";
        
        try (Connection con = ConexionBD.getInstancia().getConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Score s = new Score(
                    rs.getInt("id"),
                    rs.getString("jugador"),
                    rs.getInt("puntaje"),
                    rs.getInt("nivel"),
                    rs.getInt("intentos")
                );
                lista.add(s);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Para la lectura, retornamos la lista vacía si hay error 
            // para evitar que la app se detenga por completo.
        }
        return lista;
    }
}