package com.agencia;

import com.agencia.dao.*;
import com.agencia.model.*;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();
        PacoteViagemDAO pacoteDAO = new PacoteViagemDAO();
        ServicoAdicionalDAO servicoDAO = new ServicoAdicionalDAO();
        ClientePacoteDAO clientePacoteDAO = new ClientePacoteDAO();
        PacoteServicoDAO pacoteServicoDAO = new PacoteServicoDAO();

        String[] opcoesMenuPrincipal = {
                "Gerenciar Clientes", "Gerenciar Pacotes de Viagem", "Gerenciar Serviços Adicionais",
                "Associar Cliente a Pacote", "Listar Pacotes de um Cliente",
                "Remover Cliente de um Pacote", "Associar Serviço a Pacote", "Listar Serviços de um Pacote",
                "Remover Serviço de Pacote", "Sair"
        };

        while (true) {
            int escolha = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Menu Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesMenuPrincipal, opcoesMenuPrincipal[0]);

            switch (escolha) {
                case 0 -> menuClientes(clienteDAO);
                case 1 -> menuPacotes(pacoteDAO);
                case 2 -> menuServicos(servicoDAO);
                case 3 -> {
                    int idCliente = Integer.parseInt(JOptionPane.showInputDialog("ID do cliente:"));
                    int idPacote = Integer.parseInt(JOptionPane.showInputDialog("ID do pacote:"));
                    clientePacoteDAO.associarClienteAoPacote(idCliente, idPacote);
                }
                case 4 -> {
                    int idCliente = Integer.parseInt(JOptionPane.showInputDialog("ID do cliente:"));
                    List<PacoteViagem> pacotes = clientePacoteDAO.listarPacotesPorCliente(idCliente);
                    StringBuilder sb = new StringBuilder("Pacotes do Cliente:\n");
                    for (PacoteViagem p : pacotes) {
                        sb.append("ID: ").append(p.getId()).append(" | Nome: ").append(p.getNome()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                }
                case 5 -> {
                    int idCliente = Integer.parseInt(JOptionPane.showInputDialog("ID do cliente:"));
                    int idPacote = Integer.parseInt(JOptionPane.showInputDialog("ID do pacote:"));
                    clientePacoteDAO.desassociarClientePacote(idCliente, idPacote);
                    JOptionPane.showMessageDialog(null, "Cliente desassociado do pacote com sucesso.");
                }
                case 6 -> {
                    int idPacote = Integer.parseInt(JOptionPane.showInputDialog("ID do pacote:"));
                    int idServico = Integer.parseInt(JOptionPane.showInputDialog("ID do serviço adicional:"));
                    pacoteServicoDAO.associarServicoAoPacote(idPacote, idServico);
                }
                case 7 -> {
                    int idPacote = Integer.parseInt(JOptionPane.showInputDialog("ID do pacote:"));
                    List<ServicoAdicional> servicos = pacoteServicoDAO.listarServicosPorPacote(idPacote);
                    StringBuilder sb = new StringBuilder("Serviços do Pacote:\n");
                    for (ServicoAdicional s : servicos) {
                        sb.append("ID: ").append(s.getId()).append(" | Nome: ").append(s.getNome()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                }
                case 8 -> {
                    int idPacote = Integer.parseInt(JOptionPane.showInputDialog("ID do pacote:"));
                    int idServico = Integer.parseInt(JOptionPane.showInputDialog("ID do serviço a remover:"));
                    pacoteServicoDAO.removerServicoDoPacote(idPacote, idServico);
                }
                default -> System.exit(0);
            }
        }
    }

    private static void menuClientes(ClienteDAO dao) {
        String[] opcoes = {"Inserir Cliente", "Listar Clientes", "Buscar Cliente por ID", "Excluir Cliente por ID", "Voltar"};
        while (true) {
            int escolha = JOptionPane.showOptionDialog(null, "Clientes - Escolha uma opção:", "Menu Clientes",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            switch (escolha) {
                case 0 -> {
                    String[] tipos = {"Nacional", "Estrangeiro"};
                    int tipo = JOptionPane.showOptionDialog(null, "Tipo de Cliente:", "Tipo",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
                    String nome = JOptionPane.showInputDialog("Nome:");
                    String telefone = JOptionPane.showInputDialog("Telefone:");
                    String email = JOptionPane.showInputDialog("Email:");
                    if (tipo == 0) {
                        String cpf = JOptionPane.showInputDialog("CPF:");
                        dao.inserir(new ClienteNacional(nome, telefone, email, cpf));
                    } else {
                        String passaporte = JOptionPane.showInputDialog("Passaporte:");
                        dao.inserir(new ClienteEstrangeiro(nome, telefone, email, passaporte));
                    }
                }
                case 1 -> {
                    List<Cliente> clientes = dao.listar();
                    StringBuilder lista = new StringBuilder("Clientes:\n");
                    for (Cliente c : clientes) {
                        lista.append("ID: ").append(c.getId())
                                .append(" | Nome: ").append(c.getNome())
                                .append(" | Email: ").append(c.getEmail())
                                .append(" | Identificador: ").append(c.getIdentificador()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, lista.toString());
                }
                case 2 -> {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("ID do cliente:"));
                    Cliente cliente = dao.buscarPorId(id);
                    if (cliente != null) {
                        JOptionPane.showMessageDialog(null, "ID: " + cliente.getId() +
                                "\nNome: " + cliente.getNome() +
                                "\nEmail: " + cliente.getEmail() +
                                "\nIdentificador: " + cliente.getIdentificador());
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
                    }
                }
                case 3 -> {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("ID do cliente para excluir:"));
                    dao.excluir(id);
                }
                default -> {
                    return;
                }
            }
        }
    }

    private static void menuPacotes(PacoteViagemDAO dao) {
        String[] opcoes = {"Inserir Pacote", "Listar Pacotes", "Buscar Pacote por ID", "Excluir Pacote por ID", "Voltar"};
        while (true) {
            int escolha = JOptionPane.showOptionDialog(null, "Pacotes de Viagem - Escolha uma opção:", "Menu Pacotes",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            switch (escolha) {
                case 0 -> {
                    String nome = JOptionPane.showInputDialog("Nome:");
                    String destino = JOptionPane.showInputDialog("Destino:");
                    int duracao = Integer.parseInt(JOptionPane.showInputDialog("Duração (dias):"));
                    double preco = Double.parseDouble(JOptionPane.showInputDialog("Preço:"));
                    String tipo = JOptionPane.showInputDialog("Tipo do pacote:");
                    dao.inserir(new PacoteViagem(nome, destino, duracao, preco, tipo));
                }
                case 1 -> {
                    List<PacoteViagem> pacotes = dao.listar();
                    StringBuilder sb = new StringBuilder("Pacotes:\n");
                    for (PacoteViagem p : pacotes) {
                        sb.append("ID: ").append(p.getId()).append(" | Nome: ").append(p.getNome())
                                .append(" | Destino: ").append(p.getDestino())
                                .append(" | Duração: ").append(p.getDuracao())
                                .append(" | Preço: ").append(p.getPreco())
                                .append(" | Tipo: ").append(p.getTipo()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                }
                case 2 -> {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("ID do pacote:"));
                    PacoteViagem pacote = dao.buscarPorId(id);
                    if (pacote != null) {
                        JOptionPane.showMessageDialog(null, "ID: " + pacote.getId() +
                                "\nNome: " + pacote.getNome() +
                                "\nDestino: " + pacote.getDestino() +
                                "\nDuração: " + pacote.getDuracao() +
                                "\nPreço: " + pacote.getPreco() +
                                "\nTipo: " + pacote.getTipo());
                    } else {
                        JOptionPane.showMessageDialog(null, "Pacote não encontrado.");
                    }
                }
                case 3 -> {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("ID do pacote a excluir:"));
                    dao.excluir(id);
                }
                default -> {
                    return;
                }
            }
        }
    }

    private static void menuServicos(ServicoAdicionalDAO dao) {
        String[] opcoes = {"Inserir Serviço", "Listar Serviços", "Buscar Serviço por ID", "Excluir Serviço por ID", "Voltar"};
        while (true) {
            int escolha = JOptionPane.showOptionDialog(null, "Serviços Adicionais - Escolha uma opção:", "Menu Serviços",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            switch (escolha) {
                case 0 -> {
                    String nome = JOptionPane.showInputDialog("Nome do Serviço:");
                    double preco = Double.parseDouble(JOptionPane.showInputDialog("Preço:"));
                    dao.inserir(new ServicoAdicional(nome, preco));
                }
                case 1 -> {
                    List<ServicoAdicional> servicos = dao.listar();
                    StringBuilder sb = new StringBuilder("Serviços:\n");
                    for (ServicoAdicional s : servicos) {
                        sb.append("ID: ").append(s.getId()).append(" | Nome: ").append(s.getNome())
                                .append(" | Preço: ").append(s.getPreco()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                }
                case 2 -> {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("ID do serviço:"));
                    ServicoAdicional servico = dao.buscarPorId(id);
                    if (servico != null) {
                        JOptionPane.showMessageDialog(null, "ID: " + servico.getId() +
                                "\nNome: " + servico.getNome() +
                                "\nPreço: " + servico.getPreco());
                    } else {
                        JOptionPane.showMessageDialog(null, "Serviço não encontrado.");
                    }
                }
                case 3 -> {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("ID do serviço para excluir:"));
                    dao.excluir(id);
                }
                default -> {
                    return;
                }
            }
        }
    }
}