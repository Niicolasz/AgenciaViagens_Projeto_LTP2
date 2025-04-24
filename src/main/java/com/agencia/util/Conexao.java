package com.agencia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/agencia_viagens";
    private static final String USUARIO = "root";
    private static final String SENHA = "Nicolas0710"; // Altere conforme sua senha

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public static void main(String[] args) {
        try (Connection conn = getConexao()) {
            if (conn != null) {
                System.out.println("Conectado com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }


}
