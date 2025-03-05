package com.mycompany.bank.model;

import com.mycompany.bank.service.SistemaBancario;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o Gerente do banco.
 * Pode ter responsabilidades de cadastrar investimentos, avaliar crédito etc.
 */
public class Gerente extends Usuario {

    private List<Investimento> opcoesInvestimento;

    public Gerente(String nome, String cpf, String senha, SistemaBancario sistema) {
        super(nome, cpf, senha, sistema);
        this.opcoesInvestimento = new ArrayList<>();
    }

    public List<Investimento> getOpcoesInvestimento() {
        return opcoesInvestimento;
    }

    public void adicionarInvestimento(Investimento inv) {
        this.opcoesInvestimento.add(inv);
        System.out.println("Investimento cadastrado: " + inv.getNome());
    }

    public void removerInvestimento(String nome) {
        this.opcoesInvestimento.removeIf(inv -> inv.getNome().equalsIgnoreCase(nome));
        System.out.println("Investimento removido: " + nome);
    }

    public boolean avaliarCredito(Cliente cliente, double valorSolicitado) {
        // Exemplo de lógica fictícia
        return valorSolicitado < 100000;
    }
}
