package com.mycompany.bank.model;

import com.mycompany.bank.exceptions.SaldoInsuficienteException;
import com.mycompany.bank.service.SistemaBancario;
import com.mycompany.bank.model.Conta;

import java.util.List;
import javax.swing.JOptionPane;

/**
 * Representa o Cliente do banco.
 */
public class Cliente extends Usuario {
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
}
