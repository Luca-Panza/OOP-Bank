package com.mycompany.bank.model;

import com.mycompany.bank.service.SistemaBancario;
import java.util.List;

/**
 * Representa o Gerente do banco.
 */
public class Gerente extends Usuario {

    public Gerente(String nome, String cpf, String senha, SistemaBancario sistema) {
        super(nome, cpf, senha, sistema);
    }

    public void adicionarInvestimento(Investimento inv) {
        System.out.println("Investimento cadastrado: " + inv.getNome());
    }

    public Cliente getClienteByAccount(String conta) {
        return sistema.getClienteByAccount(conta);
    }

    public void cadastrarRendaFixa(String nome, String descricao, double valorMinimo, double taxaRendimento, int prazoMinimo, int prazoMaximo) {
        sistema.adicionarInvestimento(new RendaFixa(nome, descricao, valorMinimo, taxaRendimento, prazoMinimo, prazoMaximo));
    }

    public void cadastrarRendaVariavel(String nome, String descricao, double valorMinimo,
            double percentualRisco, double rentabilidadeEsperada) {
        sistema.adicionarInvestimento(new RendaVariavel(nome, descricao, valorMinimo, percentualRisco, rentabilidadeEsperada));
    }
    
    public List<SolicitacaoCred> getNotApproved() {
        return sistema.getNotApprovedCred();
    }
    
    public String getClientNameById(int id) {
        Usuario usuarioEncontrado = sistema.buscarUsuarioPorId(id);
        
        if (usuarioEncontrado != null) {
            return usuarioEncontrado.getNome();
        }
        
        return "Usuario não encontrado";
    }
    
    public void aceitarSolicitacao(int id) {
        sistema.aceitaSolicitacao(id);
    }
    
    public void recusarSolicitacao(int id) {
        sistema.recusarSolicitacao(id);
    }
}
