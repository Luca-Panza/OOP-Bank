package com.mycompany.bank.gui;

import com.mycompany.bank.exceptions.SaldoInsuficienteException;
import com.mycompany.bank.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteInvestimentosRendaFixa extends JFrame {

    private Cliente cliente;
    private ActionsView actionsView;
    private JPasswordField passwordField;

    public ClienteInvestimentosRendaFixa(ActionsView actionsView, Cliente cliente) {
        super("Investimentos Renda Fixa");
        this.actionsView = actionsView;
        this.cliente = cliente;
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JButton btnMeusInvestimentos = new JButton("Meus Investimentos");
        JButton btnInvestir = new JButton("Investir");

        btnMeusInvestimentos.addActionListener(e -> showInvestmentsList(true));
        btnInvestir.addActionListener(e -> showInvestmentsList(false));

        topPanel.add(btnMeusInvestimentos);
        topPanel.add(btnInvestir);

        add(topPanel, BorderLayout.NORTH);

        addBackButton();

        setLocationRelativeTo(null);
    }

    private void showInvestmentsList(boolean existingInvestments) {
        getContentPane().removeAll();
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        List<Investimento> investments = existingInvestments ? cliente.getPersonInvestimentos()
                : cliente.getInvestimentosRendaFixa();

        if (existingInvestments) {
            DefaultListModel<Investimento> listModel = new DefaultListModel<>();
            investments.forEach(listModel::addElement);

            JList<Investimento> investmentList = new JList<>(listModel);
            JScrollPane scrollPane = new JScrollPane(investmentList);
            containerPanel.add(scrollPane);
        } else {
            JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            passPanel.add(new JLabel("Digite sua senha:"));
            passwordField = new JPasswordField(10);
            passPanel.add(passwordField);
            containerPanel.add(passPanel);

            for (Investimento investment : investments) {
                JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel label = new JLabel(investment.toString());
                JButton btnInvestirItem = new JButton("Investir");

                btnInvestirItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String password = new String(passwordField.getPassword());
                        handleInvestment(investment, password);
                    }
                });
                itemPanel.add(label);
                itemPanel.add(btnInvestirItem);
                containerPanel.add(itemPanel);
            }
            if (investments.isEmpty()) {
                containerPanel.add(new JLabel("Não há investimentos disponíveis."));
            }
        }

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void handleInvestment(Investimento investment, String password) {
        if (!cliente.autenticar(password)) {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
            return;
        }

        double amount = investment.getValue();
        double totalBalance = cliente.getContas().stream()
                .mapToDouble(Conta::getSaldo)
                .sum();

        if (totalBalance < amount) {
            JOptionPane.showMessageDialog(this, "Saldo insuficiente!");
            return;
        }

        cliente.adicionaInvestimento(investment.getId());
        try {
            cliente.removeBalance(amount);
        } catch (SaldoInsuficienteException ex) {
            Logger.getLogger(ClienteInvestimentosRendaFixa.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(this, "Investimento realizado com sucesso!");
        actionsView.setVisible(true);
        setVisible(false);
    }

    private void addBackButton() {
        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(e -> {
            actionsView.setVisible(true);
            setVisible(false);
        });
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backButton);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }
}
