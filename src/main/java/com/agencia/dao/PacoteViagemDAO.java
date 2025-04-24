package com.agencia.dao;

import com.agencia.model.PacoteViagem;
import com.agencia.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class PacoteViagemDAO {

    public void inserir(PacoteViagem pacote) {
        String sql = "INSERT INTO pacote_viagem (nome, destino, duracao, preco, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pacote.getNome());
            stmt.setString(2, pacote.getDestino());
            stmt.setInt(3, pacote.getDuracao());
            stmt.setDouble(4, pacote.getPreco());
            stmt.setString(5, pacote.getTipo());

            stmt.executeUpdate();
            System.out.println("Pacote de viagem inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir pacote de viagem: " + e.getMessage());
        }
    }
    public List<PacoteViagem> listar() {
        List<PacoteViagem> pacotes = new ArrayList<>();
        String sql = "SELECT * FROM pacote_viagem";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PacoteViagem pacote = new PacoteViagem(
                        rs.getString("nome"),
                        rs.getString("destino"),
                        rs.getInt("duracao"),
                        rs.getDouble("preco"),
                        rs.getString("tipo")
                );
                pacotes.add(pacote);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacotes;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM pacote_viagem WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PacoteViagem buscarPorId(int id) {
        PacoteViagem pacote = null;
        String sql = "SELECT * FROM pacote_viagem WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery(); // <- Aqui criamos 'rs'

            if (rs.next()) {
                pacote = new PacoteViagem(
                        rs.getString("nome"),
                        rs.getString("destino"),
                        rs.getInt("duracao"),
                        rs.getDouble("preco"),
                        rs.getString("tipo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacote;
    }
}