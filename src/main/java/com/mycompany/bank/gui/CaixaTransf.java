package com.mycompany.bank.gui;

import com.mycompany.bank.model.Caixa;
import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.model.Gerente;
import com.mycompany.bank.exceptions.SaldoInsuficienteException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CaixaTransf extends JFrame {
    private JTextField originAccountField;
    private JTextField destAccountField;
    private JTextField valueField;
    private JPasswordField passwordField;
    private JButton confirmButton;

    public CaixaTransf(ActionsView actionsView, Caixa caixa) {
        super("Sistema Bancário - Transferência");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(280, 260);
        setLayout(new FlowLayout());

        add(new JLabel("Conta de origem:"));
        originAccountField = new JTextField(20);
        add(originAccountField);

        add(new JLabel("Conta de destino:"));
        destAccountField = new JTextField(20);
        add(destAccountField);

        add(new JLabel("Valor:"));
        valueField = new JTextField(20);

        valueField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                String text = valueField.getText();

                if (!Character.isDigit(c) && c != '.' || (c == '.' && text.contains("."))) {
                    System.out.print(c + text);
                    evt.consume();
                }
            }
        });

        add(valueField);

        add(new JLabel("Senha:"));
        passwordField = new JPasswordField(20);
        add(passwordField);

        confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String originAccount = originAccountField.getText();
                String destAccount = destAccountField.getText();
                String password = new String(passwordField.getPassword());
                String value = valueField.getText();

                if (originAccount.isBlank() || destAccount.isBlank() || password.isBlank() || value.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Por favor preencha todos os campos.");
                    return;
                }
                
                if (originAccount.equals(destAccount)) {
                    JOptionPane.showMessageDialog(null, "Conta de origem e destino iguais.");
                    return;
                }

                double numberValue = Double.parseDouble(value);

                Cliente clienteConta = caixa.getClienteByAccount(originAccount);

                if (clienteConta == null) {
                    JOptionPane.showMessageDialog(null, "Conta de origem inválida");
                    return;
                }

                boolean verificaSenha = clienteConta.autenticar(password);

                if (!verificaSenha) {
                    JOptionPane.showMessageDialog(null, "Senha inválida");
                    return;
                }

                try {
                    boolean destAccountFound = clienteConta.transferir(originAccount, destAccount, numberValue);
                    
                    if (!destAccountFound) {
                        JOptionPane.showMessageDialog(null, "Conta de destino inválida !");
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(null, "Transferência realizada !");
                    actionsView.setVisible(true);
                    setVisible(false);
                } catch (SaldoInsuficienteException error) {
                    JOptionPane.showMessageDialog(null, "Saldo da conta é insuficiente!");
                }
            }
        });

        add(confirmButton);

        setLocationRelativeTo(null);
    }
    
    public CaixaTransf(ActionsView actionsView, Gerente gerente) {
        super("Sistema Bancário - Transferência");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(280, 260);
        setLayout(new FlowLayout());

        add(new JLabel("Conta de origem:"));
        originAccountField = new JTextField(20);
        add(originAccountField);

        add(new JLabel("Conta de destino:"));
        destAccountField = new JTextField(20);
        add(destAccountField);

        add(new JLabel("Valor:"));
        valueField = new JTextField(20);

        valueField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                String text = valueField.getText();

                if (!Character.isDigit(c) && c != '.' || (c == '.' && text.contains("."))) {
                    System.out.print(c + text);
                    evt.consume();
                }
            }
        });

        add(valueField);

        add(new JLabel("Senha:"));
        passwordField = new JPasswordField(20);
        add(passwordField);

        confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String originAccount = originAccountField.getText();
                String destAccount = destAccountField.getText();
                String password = new String(passwordField.getPassword());
                String value = valueField.getText();

                if (originAccount.isBlank() || destAccount.isBlank() || password.isBlank() || value.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Por favor preencha todos os campos.");
                    return;
                }
                
                if (originAccount.equals(destAccount)) {
                    JOptionPane.showMessageDialog(null, "Conta de origem e destino iguais.");
                    return;
                }

                double numberValue = Double.parseDouble(value);
                
                if (numberValue < 1000000) {
                    JOptionPane.showMessageDialog(null, "Apenas valores a partir de R$ 1.000.000,00");
                    return;
                }

                Cliente clienteConta = gerente.getClienteByAccount(originAccount);

                if (clienteConta == null) {
                    JOptionPane.showMessageDialog(null, "Conta de origem inválida");
                    return;
                }

                boolean verificaSenha = clienteConta.autenticar(password);

                if (!verificaSenha) {
                    JOptionPane.showMessageDialog(null, "Senha inválida");
                    return;
                }

                try {
                    boolean destAccountFound = clienteConta.transferir(originAccount, destAccount, numberValue);
                    
                    if (!destAccountFound) {
                        JOptionPane.showMessageDialog(null, "Conta de destino inválida !");
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(null, "Transferência realizada !");
                    actionsView.setVisible(true);
                    setVisible(false);
                } catch (SaldoInsuficienteException error) {
                    JOptionPane.showMessageDialog(null, "Saldo da conta é insuficiente!");
                }
            }
        });

        add(confirmButton);

        setLocationRelativeTo(null);
    }
}
