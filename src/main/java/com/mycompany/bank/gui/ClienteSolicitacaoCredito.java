package com.mycompany.bank.gui;

import com.mycompany.bank.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteSolicitacaoCredito extends JFrame {

    private Cliente cliente;
    private ActionsView actionsView;

    public ClienteSolicitacaoCredito(ActionsView actionsView, Cliente cliente) {
        super("Solicitações de Crédito");
        this.actionsView = actionsView;
        this.cliente = cliente;
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new FlowLayout());

        JButton btnMinhasSolicitacoes = new JButton("Minhas Solicitações");
        JButton btnNovaSolicitacao = new JButton("Nova Solicitação");

        btnMinhasSolicitacoes.addActionListener(e -> showSolicitationsList());
        btnNovaSolicitacao.addActionListener(e -> showNewSolicitationForm());

        add(btnMinhasSolicitacoes);
        add(btnNovaSolicitacao);

        addBackButton();
        setLocationRelativeTo(null);
    }

    private void showSolicitationsList() {
        getContentPane().removeAll();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (SolicitacaoCred solicitacao : cliente.getSolicitacoesCredito()) {
            JPanel solicitacaoPanel = new JPanel(new BorderLayout());
            String status = "Analisado: " + toSimNao(solicitacao.isAnalyzed())
                    + " | Aprovado: " + toSimNao(solicitacao.isApproved())
                    + " | Aceito: " + toSimNao(solicitacao.isAccepted());
            JLabel label = new JLabel(solicitacao.toString() + " - " + status);
            solicitacaoPanel.add(label, BorderLayout.CENTER);

            if (solicitacao.isAnalyzed() && solicitacao.isApproved() && !solicitacao.isAccepted()) {
                JButton acceptButton = new JButton("Aceitar");
                JButton rejectButton = new JButton("Rejeitar");

                acceptButton.addActionListener(e -> {
                    handleCreditAcceptance(solicitacao, true);
                    refreshSolicitationsList();
                });

                rejectButton.addActionListener(e -> {
                    handleCreditAcceptance(solicitacao, false);
                    refreshSolicitationsList();
                });

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(acceptButton);
                buttonPanel.add(rejectButton);
                solicitacaoPanel.add(buttonPanel, BorderLayout.EAST);
            }

            mainPanel.add(solicitacaoPanel);
            mainPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> restartComponent());
        add(btnVoltar);

        revalidate();
        repaint();
    }

    private void showNewSolicitationForm() {
        getContentPane().removeAll();

        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        JTextField amountField = new JTextField();

        amountField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                String text = amountField.getText();

                if (!Character.isDigit(c) && c != '.' || (c == '.' && text.contains("."))) {
                    System.out.print(c + text);
                    evt.consume();
                }
            }
        });

        JTextArea purposeArea = new JTextArea(3, 20);
        JPasswordField passwordField;

        formPanel.add(new JLabel("Valor:"));
        formPanel.add(amountField);
        formPanel.add(new JLabel("Finalidade:"));
        formPanel.add(new JScrollPane(purposeArea));

        JButton submitButton = new JButton("Enviar");

        add(formPanel);

        add(new JLabel("Senha:"));
        passwordField = new JPasswordField(20);
        add(passwordField);

        submitButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            boolean verificaSenha = cliente.autenticar(password);

            if (amountField.getText().isBlank() || purposeArea.getText().isBlank() || password.isBlank()) {
                JOptionPane.showMessageDialog(null, "Por favor preencha todos os campos.");
                return;
            }

            if (!verificaSenha) {
                JOptionPane.showMessageDialog(null, "Senha inválida");
                return;
            }
            cliente.adicionarSolicitacaoCred(Double.parseDouble(amountField.getText()), purposeArea.getText());
            JOptionPane.showMessageDialog(this, "Solicitação enviada!");
        });

        add(submitButton);

        JButton btnSair = new JButton("Voltar");
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartComponent();
            }
        });
        add(btnSair);

        revalidate();
        repaint();
    }

    private class CreditSolicitationRenderer extends JLabel implements ListCellRenderer<SolicitacaoCred> {

        public Component getListCellRendererComponent(
                JList<? extends SolicitacaoCred> list, SolicitacaoCred value,
                int index, boolean isSelected, boolean cellHasFocus) {

            String status = "Analisado: " + toSimNao(value.isAnalyzed())
                    + " | Aprovado: " + toSimNao(value.isApproved())
                    + " | Aceito: " + toSimNao(value.isAccepted());

            setText(value.toString() + " - " + status);

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            if (value.isAnalyzed() && value.isApproved() && !value.isAccepted()) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(this, BorderLayout.CENTER);

                JButton acceptButton = new JButton("Aceitar");
                JButton rejectButton = new JButton("Rejeitar");

                acceptButton.addActionListener(e -> handleCreditAcceptance(value, true));
                rejectButton.addActionListener(e -> handleCreditAcceptance(value, false));

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(acceptButton);
                buttonPanel.add(rejectButton);
                panel.add(buttonPanel, BorderLayout.EAST);

                return panel;
            }

            return this;
        }
    }

    private void handleCreditAcceptance(SolicitacaoCred solicitacao, boolean accepted) {
        solicitacao.setanAlyzed();

        if (accepted) {
            solicitacao.setAccepted();
            cliente.addBalance(solicitacao.getValue());
        }

        JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!");
    }

    private void addBackButton() {
        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                actionsView.setVisible(true);
            }
        });
        add(btnSair);
    }

    private void restartComponent() {
        getContentPane().removeAll();
        initializeUI();
        revalidate();
        repaint();
    }

    private String toSimNao(boolean value) {
        return value ? "Sim" : "Não";
    }

    private void refreshSolicitationsList() {
        showSolicitationsList();
    }
}
