package com.mycompany.bank.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mycompany.bank.model.Caixa;
import com.mycompany.bank.model.Conta;

public class ProssDeposito extends JFrame {
    private JTextField valueField;
    private JPasswordField passwordField;
    private JButton confirmButton;
    private JTextField originAccountField;
    
    public ProssDeposito(ActionsView actionsView, Caixa caixa) {
        super("Sistema Bancário - Depósito");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(280, 200);
        setLayout(new FlowLayout());
        
        add(new JLabel("Conta de destino:"));
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
        
        confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String originAccount = originAccountField.getText();
                String value = valueField.getText();

                if (originAccount.isBlank() || value.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Por favor preencha todos os campos.");
                    return;
                }
                
                double numberValue = Double.parseDouble(value);
                
                Conta contaFound = caixa.getContaByNumero(originAccount);
                
                if (contaFound == null) {
                    JOptionPane.showMessageDialog(null, "Conta inválida !");
                    return;
                }
                
                contaFound.depositar(numberValue);
                
                JOptionPane.showMessageDialog(null, "Depósito realizado !");
                actionsView.setVisible(true);
                setVisible(false);
            }
        });

        add(confirmButton);

        setLocationRelativeTo(null);
    }
}
