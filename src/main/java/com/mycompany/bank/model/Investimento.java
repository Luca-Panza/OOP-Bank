package com.mycompany.bank.model;

public abstract class Investimento {

    private String nome;
    private String descricao;
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
