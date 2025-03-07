package com.mycompany.bank.model;

import com.google.gson.annotations.Expose;

public abstract class Investimento {

    @Expose
    private String nome;
    @Expose
    private String descricao;
    @Expose
    private double valorMinimo;

    public Investimento(String nome, String descricao, double valorMinimo) {
        this.nome = nome;
        this.descricao = descricao;
        this.valorMinimo = valorMinimo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValorMinimo() {
        return valorMinimo;
    }

    public abstract void aplicar(double valor);

    public abstract void resgatar(double valor);
}
