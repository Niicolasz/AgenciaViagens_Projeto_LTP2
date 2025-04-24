package com.agencia.model;

public class PacoteViagem {
    public int id;
    public String nome;
    public String destino;
    public int duracao; // em dias
    public double preco;
    public String tipo; // aventura, luxo, cultural...

    public PacoteViagem(String nome, String destino, int duracao, double preco, String tipo) {
        this.nome = nome;
        this.destino = destino;
        this.duracao = duracao;
        this.preco = preco;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDestino() {
        return destino;
    }

    public int getDuracao() {
        return duracao;
    }

    public double getPreco() {
        return preco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Pode adicionar métodos abstratos específicos para subclasses (luxo, aventura...) no futuro
}