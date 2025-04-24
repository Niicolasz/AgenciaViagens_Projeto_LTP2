package com.agencia.dao;

import com.agencia.model.Cliente;
import com.agencia.model.PacoteViagem;
import com.agencia.util.Conexao;
import com.agencia.model.ClienteNacional;
import com.agencia.model.ClienteEstrangeiro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientePacoteDAO {

    public void associarClientePacote(int clienteId, int pacoteId) {
        String sql = "INSERT INTO cliente_pacote (id_cliente, id_pacote) VALUES (?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            stmt.setInt(2, pacoteId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desassociarClientePacote(int clienteId, int pacoteId) {
        String sql = "DELETE FROM cliente_pacote WHERE id_cliente = ? AND id_pacote = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            stmt.setInt(2, pacoteId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PacoteViagem> listarPacotesPorCliente(int clienteId) {
        List<PacoteViagem> pacotes = new ArrayList<>();

        String sql = "SELECT pv.id, pv.nome, pv.destino, pv.duracao, pv.preco, pv.tipo " +
                "FROM pacote_viagem pv " +
                "JOIN cliente_pacote cp ON cp.id_pacote = pv.id " +
                "WHERE cp.id_cliente = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

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

    public List<Cliente> listarClientesPorPacote(int pacoteId) {
        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT c.*, cn.cpf, ce.passaporte " +
                "FROM cliente c " +
                "JOIN cliente_pacote cp ON cp.id_cliente = c.id " +
                "LEFT JOIN cliente_nacional cn ON cn.id = c.id " +
                "LEFT JOIN cliente_estrangeiro ce ON ce.id = c.id " +
                "WHERE cp.id_pacote = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pacoteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente;

                if (rs.getString("cpf") != null) {
                    cliente = new ClienteNacional(
                            rs.getString("nome"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("cpf")
                    );
                } else {
                    cliente = new ClienteEstrangeiro(
                            rs.getString("nome"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("passaporte")
                    );
                }

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }
    public boolean associarClienteAoPacote(int clienteId, int pacoteId) {
        String sql = "INSERT INTO cliente_pacote (id_cliente, id_pacote) VALUES (?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setInt(2, pacoteId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}