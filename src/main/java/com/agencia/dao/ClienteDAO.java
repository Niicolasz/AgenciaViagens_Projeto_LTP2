package com.agencia.dao;

import com.agencia.model.Cliente;
import com.agencia.model.ClienteEstrangeiro;
import com.agencia.model.ClienteNacional;
import com.agencia.util.Conexao;
import com.agencia.model.PacoteViagem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;


public class ClienteDAO {

    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, telefone, email, tipo, cpf, passaporte) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEmail());

            if (cliente instanceof ClienteNacional) {
                stmt.setString(4, "NACIONAL");
                stmt.setString(5, ((ClienteNacional) cliente).getCpf());
                stmt.setNull(6, java.sql.Types.VARCHAR);
            } else if (cliente instanceof ClienteEstrangeiro) {
                stmt.setString(4, "ESTRANGEIRO");
                stmt.setNull(5, java.sql.Types.VARCHAR);
                stmt.setString(6, ((ClienteEstrangeiro) cliente).getPassaporte());
            }

            stmt.executeUpdate();
            System.out.println("Cliente inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String tipo = rs.getString("tipo");
                String cpf = rs.getString("cpf");
                String passaporte = rs.getString("passaporte");

                Cliente cliente;
                if ("NACIONAL".equalsIgnoreCase(tipo)) {
                    cliente = new ClienteNacional(nome, telefone, email, cpf);
                } else {
                    cliente = new ClienteEstrangeiro(nome, telefone, email, passaporte);
                }
                cliente.setId(id);
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }
    public Cliente buscarPorId(int idBusca) {
        String sql = "SELECT * FROM cliente WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idBusca);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    String tipo = rs.getString("tipo");
                    String cpf = rs.getString("cpf");
                    String passaporte = rs.getString("passaporte");

                    Cliente cliente;
                    if ("NACIONAL".equalsIgnoreCase(tipo)) {
                        cliente = new ClienteNacional(nome, telefone, email, cpf);
                    } else {
                        cliente = new ClienteEstrangeiro(nome, telefone, email, passaporte);
                    }
                    cliente.setId(idBusca);
                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }

        return null;
    }
    public void excluir(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Cliente removido com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }
    public void contratarPacote(int idCliente, int idPacote) {
        String sql = "INSERT INTO cliente_pacote (id_cliente, id_pacote) VALUES (?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idPacote);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<PacoteViagem> listarPacotesPorCliente(int idCliente) {
        List<PacoteViagem> pacotes = new ArrayList<>();
        String sql = "SELECT p.* FROM pacote_viagem p JOIN cliente_pacote cp ON p.id = cp.id_pacote WHERE cp.id_cliente = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PacoteViagem pacote = new PacoteViagem();
                pacote.setId(rs.getInt("id"));
                pacote.setNome(rs.getString("Nome"));
                pacote.setDestino(rs.getString("destino"));
                pacote.setDuracao(rs.getInt("duracao"));
                pacote.setPreco(rs.getDouble("preco"));
                pacote.setTipo(rs.getString("tipo"));
                pacotes.add(pacote);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacotes;
    }
    public boolean associarClientePacote(int clienteId, int pacoteId) {
        String sql = "INSERT INTO cliente_pacote (id_cliente, id_pacote) VALUES (?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setInt(2, pacoteId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao associar cliente ao pacote: " + e.getMessage());
            return false;
        }
    }


}