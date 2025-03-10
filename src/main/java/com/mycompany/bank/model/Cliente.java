package com.mycompany.bank.model;

import com.mycompany.bank.exceptions.SaldoInsuficienteException;
import com.mycompany.bank.service.SistemaBancario;
import com.mycompany.bank.model.*;
import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Representa o Cliente do banco.
 */
public class Cliente extends Usuario {
    @Expose
    private static final String TIPO = "Cliente";
    
    @Expose
    private static final List<Integer> investimentos = new ArrayList<>();

    public Cliente(String nome, String cpf, String senha, SistemaBancario sistema) {
        super(nome, cpf, senha, sistema);
    }

    public double consultarSaldo(String numeroConta) {
        final List<Conta> contas = getContas();
        
        for (Conta c : contas) {
            if (c.getNumero().equals(numeroConta)) {
                return c.getSaldo();
            }
        }
        return -1;
    }
    
    public boolean pertenceConta(String numeroConta) {
        final List<Conta> contas = getContas();
        
        for (Conta c : contas) {
            if (c.getNumero().equals(numeroConta)) {
                return true;
            }
        }
        
        return false;
    }

    public boolean transferir(String contaOrigem, String contaDestino, double valor) throws SaldoInsuficienteException {
        Conta origem = null;
        Conta destino = null;

        final List<Conta> contasUsuario = getContas();
        
        for (Conta c : contasUsuario) {
            if (c.getNumero().equals(contaOrigem)) {
                origem = c;
                break;
            }
        }
        
        destino = sistema.buscarContaPorNumero(contaDestino);

        if (origem == null || destino == null) {
            System.out.println("Conta(s) não encontrada(s)!");
            return false;
        }

        origem.transferir(destino, valor);
        
        return true;
    }
    
    public boolean sacar(String contaOrigem, double valor) {
        Conta origem = null;

        final List<Conta> contasUsuario = getContas();
        
        for (Conta c : contasUsuario) {
            if (c.getNumero().equals(contaOrigem)) {
                origem = c;
                break;
            }
        }
        
        try {
            origem.sacar(valor);
            return true;
        } catch (SaldoInsuficienteException error) {
            JOptionPane.showMessageDialog(null, "Saldo da conta é insuficiente!");
            return false;
        }
    }
    
    public void adicionarSolicitacaoCred(double value, String reason) {
        sistema.adicionarSolicitacaoCred(getId(), value, reason);
    }
    
    public List<SolicitacaoCred> getSolicitacoesCredito() {
        return sistema.getSolicitacoesCredito(getId());
    }
    
    public void addBalance(double value) {
        final List<Conta> contas = getContas();
        
        contas.get(0).depositar(value);
    }
    
    public void removeBalance(double value) throws SaldoInsuficienteException {
        final List<Conta> contas = getContas();
        
        contas.get(0).sacar(value);
    }
    
    public List<Investimento> getInvestimentosRendaFixa() {
        return sistema.getInvestimentosRendaFixa();
    }
    
    public List<Investimento> getInvestimentosRendaVariavel() {
        return sistema.getInvestimentosRendaVariavel();
    }
    
    public List<Investimento> getPersonInvestimentos() {
        return sistema.getInvestimentosByIds(investimentos);
    }
    
    public void adicionaInvestimento(int id) {
        investimentos.add(id);
    }
}
