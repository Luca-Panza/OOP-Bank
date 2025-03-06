package com.mycompany.bank.gui;

import com.mycompany.bank.gui.ClienteTransf;
import com.mycompany.bank.gui.ClienteBalance;
import com.mycompany.bank.gui.SaqueCaixa;
import com.mycompany.bank.gui.CaixaTransf;

import com.mycompany.bank.service.SistemaBancario;
import com.mycompany.bank.model.Usuario;
import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.model.Caixa;
import com.mycompany.bank.model.Gerente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ActionsView extends JFrame {
    public ActionsView(SistemaBancario sistema, Usuario usuario) {
        super("Sistema Bancário - Ações");
        
        ActionsView instancia = this;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());

        if (usuario instanceof Cliente) {
            // Opções disponíveis para o cliente
            JButton btnTransferencia = new JButton("Transferência");

            btnTransferencia.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ClienteTransf clienteTransf = new ClienteTransf(instancia, (Cliente) usuario);
                    
                    clienteTransf.setVisible(true);
                    instancia.setVisible(false);
                }
            });

            JButton btnSaldoExtrato = new JButton("Consulta Saldo/Extrato");
            
            btnSaldoExtrato.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ClienteBalance clienteBalance = new ClienteBalance(instancia, (Cliente) usuario);
                    
                    clienteBalance.setVisible(true);
                    instancia.setVisible(false);
                }
            });
            
            JButton btnRendaFixa = new JButton("Investimento Renda Fixa");
            JButton btnRendaVariavel = new JButton("Investimento Renda Variável");
            JButton btnSolicitacaoCredito = new JButton("Solicitação de Crédito");

            add(btnTransferencia);
            add(btnSaldoExtrato);
            add(btnRendaFixa);
            add(btnRendaVariavel);
            add(btnSolicitacaoCredito);

        } else if (usuario instanceof Caixa) {
            setSize(300, 150);
            JButton btnTransferencia = new JButton("Atendimento de Saque");
            
            btnTransferencia.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SaqueCaixa saqueCaixa = new SaqueCaixa(instancia, (Caixa) usuario);
                    
                    saqueCaixa.setVisible(true);
                    instancia.setVisible(false);
                }
            });
            
            JButton btnProssDep = new JButton("Processamento de Depósitos");
            
            btnProssDep.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ProssDeposito prossDeposito = new ProssDeposito(instancia, (Caixa) usuario);
                    
                    prossDeposito.setVisible(true);
                    instancia.setVisible(false);
                }
            });
            
            JButton btnTransf = new JButton("Transferência");
            
            btnTransf.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CaixaTransf caixaTransf = new CaixaTransf(instancia, (Caixa) usuario);
                    
                    caixaTransf.setVisible(true);
                    instancia.setVisible(false);
                }
            });

            add(btnTransferencia);
            add(btnProssDep);
            add(btnTransf);

        } else if (usuario instanceof Gerente) {
            // Exemplo de opções para o gerente
            setSize(300, 180);
            JButton btnAnaliseCredito = new JButton("Apoio Movimentações Financeiras");
            JButton btnRendaFixa = new JButton("Cadastro Opções Renda Fixa");
            JButton btnRendaVariavel = new JButton("Cadastro Opções Renda Variável");
            JButton btnAvCred = new JButton("Avaliação de Crédito");

            add(btnAnaliseCredito);
            add(btnRendaFixa);
            add(btnRendaVariavel);
            add(btnAvCred);
        }

        setLocationRelativeTo(null);
    }
}
