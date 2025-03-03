package com.mycompany.bank;

import com.mycompany.bank.gui.MainView;
import com.mycompany.bank.service.SistemaBancario;
import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.model.Caixa;
import com.mycompany.bank.model.Gerente;
import com.mycompany.bank.model.Conta;
import javax.swing.SwingUtilities;

/**
 * Classe principal. 
 * Pode instanciar o sistema, criar usuários, abrir a GUI.
 */
public class Main {
    public static void main(String[] args) {
        // Cria o sistema
        SistemaBancario sistema = new SistemaBancario();

        // Adiciona alguns usuários de exemplo
        Cliente c1 = new Cliente("João da Silva", "111.111.111-11", "123");
        Caixa caixa1 = new Caixa("Caixa Fulano", "222.222.222-22", "abc");
        Gerente g1 = new Gerente("Gerente Beltrano", "333.333.333-33", "xyz");

        sistema.adicionarUsuario(c1);
        sistema.adicionarUsuario(caixa1);
        sistema.adicionarUsuario(g1);

        // Associa uma conta ao cliente
        Conta contaJoao = new Conta("0001", 1000.0);
        c1.adicionarConta(contaJoao);
        sistema.adicionarConta(contaJoao);

        // Inicia a GUI (Swing)
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView(sistema);
            mainView.setVisible(true);
        });
    }
}
