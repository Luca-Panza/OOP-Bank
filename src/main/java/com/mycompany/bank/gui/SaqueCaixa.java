package com.mycompany.bank.gui;

import com.mycompany.bank.exceptions.SaldoInsuficienteException;
import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.model.Caixa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SaqueCaixa extends JFrame {
    private JTextField valueField;
    private JPasswordField passwordField;
    private JButton confirmButton;
    private JTextField originAccountField;
    
    public SaqueCaixa(ActionsView actionsView, Caixa caixa) {
        super("Sistema Bancário - Saque");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(280, 260);
        setLayout(new FlowLayout());
        
        add(new JLabel("Conta de origem:"));
        originAccountField = new JTextField(20);
        add(originAccountField);
        
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
                String password = new String(passwordField.getPassword());
                String value = valueField.getText();

                if (originAccount.isBlank() || password.isBlank() || value.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Por favor preencha todos os campos.");
                    return;
                }
                
                double numberValue = Double.parseDouble(value);
                
                Cliente cliente = caixa.getClienteByAccount(originAccount);
                
                if (cliente == null) {
                    JOptionPane.showMessageDialog(null, "Conta inválida");
                    return;
                }

                boolean verificaSenha = cliente.autenticar(password);

                if (!verificaSenha) {
                    JOptionPane.showMessageDialog(null, "Senha inválida");
                    return;
                }
                
                boolean success = cliente.sacar(originAccount, numberValue);
                
                if (success) {
                    JOptionPane.showMessageDialog(null, "Saque autorizado !");
                    actionsView.setVisible(true);
                    setVisible(false);
                }
            }
        });

        add(confirmButton);

        setLocationRelativeTo(null);
    }
}
