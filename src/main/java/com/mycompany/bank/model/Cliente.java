package com.mycompany.bank.model;

import com.mycompany.bank.exceptions.SaldoInsuficienteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o Cliente do banco.
 */
public class Cliente extends Usuario {

    private List<Conta> contas;

    public Cliente(String nome, String cpf, String senha) {
        super(nome, cpf, senha);
        this.contas = new ArrayList<>();
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void adicionarConta(Conta conta) {
        this.contas.add(conta);
    }

    public double consultarSaldo(String numeroConta) {
        for (Conta c : contas) {
            if (c.getNumero().equals(numeroConta)) {
                return c.getSaldo();
            }
        }
        return -1; // ou lançar exceção
    }

    /**
     * Exemplo de transferência a partir do próprio cliente.
     * (Em um sistema real, a conta de destino poderia pertencer a outro cliente.)
     */
    public void transferir(String contaOrigem, String contaDestino, double valor) throws SaldoInsuficienteException {
        Conta origem = null;
        Conta destino = null;

        // busca conta de origem
        for (Conta c : contas) {
            if (c.getNumero().equals(contaOrigem)) {
                origem = c;
                break;
            }
        }
        // busca conta de destino
        for (Conta c : contas) {
            if (c.getNumero().equals(contaDestino)) {
                destino = c;
                break;
            }
        }

        if (origem == null || destino == null) {
            System.out.println("Conta(s) não encontrada(s)!");
            return;
        }

        origem.transferir(destino, valor);
    }
}
