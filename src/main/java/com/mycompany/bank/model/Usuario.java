package com.mycompany.bank.model;

/**
 * Classe abstrata para representar um usuário do sistema bancário.
 */
public abstract class Usuario implements Autenticavel {

    private static int contadorId = 0;

    private int id;
    private String nome;
    private String cpf;
    private String senha;

    public Usuario(String nome, String cpf, String senha) {
        this.id = ++contadorId;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }

    // Implementação da interface Autenticavel
    @Override
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
