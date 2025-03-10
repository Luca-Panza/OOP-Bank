package com.mycompany.bank.model;

import com.mycompany.bank.service.SistemaBancario;
import java.util.List;
import com.google.gson.annotations.Expose;

/**
 * Classe abstrata para representar um usuário do sistema bancário.
 */
public abstract class Usuario implements Autenticavel {
    @Expose
    private static int contadorId = 0;

    @Expose
    private int id;
    @Expose
    private String nome;
    @Expose
    private String cpf;
    @Expose
    private String senha;
    
    protected SistemaBancario sistema;

    public Usuario(String nome, String cpf, String senha, SistemaBancario sistema) {
        this.id = ++contadorId;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.sistema = sistema;
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
    
    public List<Conta> getContas() {
        return sistema.buscarContaPorUsuario(getId());
    }
    
    @Override
    public String toString() {
        return this.nome;
    }
    
    public void setSistema(SistemaBancario sistema) {
        this.sistema = sistema;
    }
}
