package com.agencia.model;

public class ClienteEstrangeiro extends Cliente {
    private String passaporte;

    public ClienteEstrangeiro(String nome, String telefone, String email, String passaporte) {
        super(nome, telefone, email);
        this.passaporte = passaporte;
    }

    public String getPassaporte() {
        return passaporte;
    }

    @Override
    public String getIdentificador() {
        return passaporte;
    }
}
