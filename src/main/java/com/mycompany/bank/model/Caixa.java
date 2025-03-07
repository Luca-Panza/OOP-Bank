package com.mycompany.bank.model;

import com.mycompany.bank.exceptions.SaldoInsuficienteException;
import com.mycompany.bank.service.SistemaBancario;
import com.google.gson.annotations.Expose;


/**
 * Representa o Caixa do banco.
 */
public class Caixa extends Usuario {
    @Expose
    private static final String TIPO = "Caixa";

    public Caixa(String nome, String cpf, String senha, SistemaBancario sistema) {
        super(nome, cpf, senha, sistema);
    }

    public void processarDeposito(Conta conta, double valor) {
        conta.depositar(valor);
        System.out.println("Depósito de R$" + valor + " realizado com sucesso.");
    }

    public void processarSaque(Conta conta, double valor) throws SaldoInsuficienteException {
        conta.sacar(valor);
        System.out.println("Saque de R$" + valor + " realizado com sucesso.");
    }

    public void processarTransferencia(Conta origem, Conta destino, double valor) throws SaldoInsuficienteException {
        origem.transferir(destino, valor);
        System.out.println("Transferência de R$" + valor + " realizada com sucesso.");
    }
    
    public Cliente getClienteByAccount(String conta) {
        return sistema.getClienteByAccount(conta);
    }
    
    public Conta getContaByNumero(String conta) {
        return sistema.buscarContaPorNumero(conta);
    }
}
