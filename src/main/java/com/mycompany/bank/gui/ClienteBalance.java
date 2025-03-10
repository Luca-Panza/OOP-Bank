package com.mycompany.bank.gui;

import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.model.Conta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteBalance extends JFrame {
    Cliente cliente;
    ActionsView actionsView;

    private JPasswordField passwordField;
    private JButton confirmButton;
    
    public ClienteBalance(ActionsView actionsView, Cliente cliente) {
        super("Sistema Bancário - Transferência");

        this.actionsView = actionsView;
        this.cliente = cliente;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(280, 260);
        setLayout(new FlowLayout());

        add(new JLabel("Senha:"));
        passwordField = new JPasswordField(20);
        add(passwordField);

        confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordField.getPassword());

                boolean verificaSenha = cliente.autenticar(password);

                if (!verificaSenha) {
                    JOptionPane.showMessageDialog(null, "Senha inválida");
                    return;
                }
                
                getContentPane().removeAll();
                revalidate();
                repaint();
                
                showBalanceAndTransactions();
            }
        });

        add(confirmButton);

        setLocationRelativeTo(null);
    }
    
    private void showBalanceAndTransactions() {
        double totalBalance = cliente.getContas().stream()
                .mapToDouble(Conta::getSaldo)
                .sum();

        List<Double> allTransactions = new ArrayList<>();
        for(Conta conta : cliente.getContas()) {
            allTransactions.addAll(conta.getHistorico());
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel balanceLabel = new JLabel("Saldo Total: R$ " + String.format("%.2f", totalBalance));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel transactionsLabel = new JLabel("Histórico de Transações:");
        transactionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(Double transaction : allTransactions) {
            String formatted = String.format("R$ %,.2f", transaction);
            listModel.addElement(formatted);
        }
        
        JList<String> transactionsList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(transactionsList);
        scrollPane.setPreferredSize(new Dimension(250, 150));

        JButton backButton = new JButton("Voltar");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> {
            actionsView.setVisible(true);
            setVisible(false);
        });

        mainPanel.add(balanceLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(transactionsLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(backButton);

        add(mainPanel);
        revalidate();
        repaint();
    }
}
