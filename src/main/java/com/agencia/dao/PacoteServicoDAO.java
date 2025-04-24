package com.agencia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.agencia.util.Conexao;
import com.agencia.model.ServicoAdicional;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;


public class PacoteServicoDAO {
    public void associarServicoAoPacote(int idPacote, int idServico) {
        String verificaSQL = "SELECT * FROM pacote_servico WHERE id_pacote = ? AND id_servico = ?";
        String insereSQL = "INSERT INTO pacote_servico (id_pacote, id_servico) VALUES (?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement verificaStmt = conn.prepareStatement(verificaSQL)) {

            verificaStmt.setInt(1, idPacote);
            verificaStmt.setInt(2, idServico);
            ResultSet rs = verificaStmt.executeQuery();

            if (!rs.next()) { // se ainda não existe, então insere
                try (PreparedStatement insereStmt = conn.prepareStatement(insereSQL)) {
                    insereStmt.setInt(1, idPacote);
                    insereStmt.setInt(2, idServico);
                    insereStmt.executeUpdate();
                }
            } else {
                System.out.println("Serviço já está associado a este pacote.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean desassociarServicoDoPacote(int idPacote, int idServico) {
        String sql = "DELETE FROM pacote_servico WHERE id_pacote = ? AND id_servico = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPacote);
            stmt.setInt(2, idServico);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<ServicoAdicional> listarServicosPorPacote(int pacoteId) {
        List<ServicoAdicional> servicos = new ArrayList<>();
        String sql = "SELECT sa.id, sa.nome, sa.preco FROM servico_adicional sa " +
                "JOIN pacote_servico ps ON sa.id = ps.id_servico " +
                "WHERE ps.id_pacote = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pacoteId);
            ResultSet rs = stmt.executeQuery();
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
    public boolean removerServicoDoPacote(int pacoteId, int servicoId) {
        String sql = "DELETE FROM pacote_servico WHERE id_pacote = ? AND id_servico = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pacoteId);
            stmt.setInt(2, servicoId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}