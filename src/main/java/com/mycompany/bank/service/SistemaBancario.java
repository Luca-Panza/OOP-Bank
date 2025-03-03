package com.mycompany.bank.service;

import com.mycompany.bank.model.Usuario;
import com.mycompany.bank.model.Conta;
import com.mycompany.bank.model.Investimento;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de serviço que gerencia as listas de usuários, contas, etc.
 */
public class SistemaBancario {

    private List<Usuario> usuarios;
    private List<Conta> contas;
    private List<Investimento> investimentos;

    public SistemaBancario() {
        this.usuarios = new ArrayList<>();
        this.contas = new ArrayList<>();
        this.investimentos = new ArrayList<>();
    }

    public void adicionarUsuario(Usuario u) {
        this.usuarios.add(u);
    }

    public Usuario buscarUsuarioPorCpf(String cpf) {
        for (Usuario u : usuarios) {
            if (u.getCpf().equalsIgnoreCase(cpf)) {
                return u;
            }
        }
        return null;
    }

    public void removerUsuario(String cpf) {
        usuarios.removeIf(u -> u.getCpf().equalsIgnoreCase(cpf));
    }

    public Usuario login(String cpf, String senha) {
        Usuario u = buscarUsuarioPorCpf(cpf);
        if (u != null && u.autenticar(senha)) {
            return u;
        }
        return null;
    }

    // Métodos para gerenciar contas
    public void adicionarConta(Conta c) {
        this.contas.add(c);
    }

    public Conta buscarContaPorNumero(String numero) {
        for (Conta c : contas) {
            if (c.getNumero().equals(numero)) {
                return c;
            }
        }
        return null;
    }

    // Métodos para gerenciar investimentos
    public void adicionarInvestimento(Investimento i) {
        this.investimentos.add(i);
    }

    public List<Investimento> getInvestimentos() {
        return investimentos;
    }

    // Exemplos de métodos de persistência (incompletos):
    /*
    public void salvarUsuariosEmJSON(String caminho) {
        // Exemplo usando Gson (precisa da dependência no classpath)
        // ...
    }

    public void carregarUsuariosDeJSON(String caminho) {
        // ...
    }
    */

    // ...
}
