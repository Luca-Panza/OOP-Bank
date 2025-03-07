package com.mycompany.bank.model;

import com.mycompany.bank.exceptions.SaldoInsuficienteException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;


/**
 * Representa uma conta bancária genérica.
 */
public class Conta {

    @Expose
    private String numero;
    @Expose
    private double saldo;
    @Expose
    private int idUsuario;
    @Expose
    private List<Double> historico = new ArrayList<>();

    public Conta(String numero, double saldoInicial, int idUsuario) {
        this.numero = numero;
        this.saldo = saldoInicial;
        this.idUsuario = idUsuario;
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor de depósito não pode ser negativo.");
        }
        historico.add(valor);
        this.saldo += valor;
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor de saque não pode ser negativo.");
        }
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente para saque.");
        }
        historico.add(valor * -1);
        this.saldo -= valor;
    }

    public void transferir(Conta destino, double valor) throws SaldoInsuficienteException {
        this.sacar(valor);
        destino.depositar(valor);
    }
    
    public int getUserId() {
        return idUsuario;
    }
    
    public List<Double> getHistorico() {
        return historico;
    }
}
