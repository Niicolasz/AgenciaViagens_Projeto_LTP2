package com.agencia.dao;

import com.agencia.model.ServicoAdicional;
import com.agencia.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoAdicionalDAO {

    public void inserir(ServicoAdicional servico) {
        String sql = "INSERT INTO servico_adicional (nome, preco) VALUES (?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servico.getNome());
            stmt.setDouble(2, servico.getPreco());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ServicoAdicional> listar() {
        List<ServicoAdicional> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servico_adicional";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ServicoAdicional servico = new ServicoAdicional(
                        rs.getString("nome"),
                        rs.getDouble("preco")
                );
                servicos.add(servico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicos;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM servico_adicional WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ServicoAdicional buscarPorId(int id) {
        ServicoAdicional servico = null;
        String sql = "SELECT * FROM servico_adicional WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                servico = new ServicoAdicional(
                        rs.getString("nome"),
                        rs.getDouble("preco")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servico;
    }

}