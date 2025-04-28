-- Cria o banco
CREATE DATABASE agencia_viagens;
USE agencia_viagens;

-- Tabela base de clientes
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    tipo ENUM('NACIONAL', 'ESTRANGEIRO') NOT NULL
);

-- Pacotes de viagem
CREATE TABLE pacote_viagem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    destino VARCHAR(100) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    tipo TEXT
);


-- Serviços adicionais
CREATE TABLE servico_adicional (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10, 2) NOT NULL
);


-- Relacionamento cliente ↔ pacote
CREATE TABLE cliente_pacote (
    id_cliente INT,
    id_pacote INT,
    PRIMARY KEY (id_cliente, id_pacote),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (id_pacote) REFERENCES pacote_viagem(id)
);

-- Relacionamento pacote ↔ serviço adicional
CREATE TABLE pacote_servico (
    id_pacote INT,
    id_servico INT,
    PRIMARY KEY (id_pacote, id_servico),
    FOREIGN KEY (id_pacote) REFERENCES pacote_viagem(id),
    FOREIGN KEY (id_servico) REFERENCES servico_adicional(id)
);
select*from cliente;
select*from pacote_viagem;
select*from servico_adicional;
select*from cliente_pacote;
select*from pacote_servico;




