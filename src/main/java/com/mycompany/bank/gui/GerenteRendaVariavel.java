package com.mycompany.bank.gui;

import com.mycompany.bank.model.Gerente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GerenteRendaVariavel extends JFrame {
    private JTextField nomeField;
    private JTextField descricaoField;
    private JTextField riscoField;
    private JTextField rentabilidadeField;
    private JButton salvarButton;

    public GerenteRendaVariavel(JFrame parent, Gerente gerente) {
        super("Cadastro Renda Variável");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 220);
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Nome:"));
        nomeField = new JTextField();
        add(nomeField);
        
        add(new JLabel("Descricao:"));
        descricaoField = new JTextField();
        add(descricaoField);

        add(new JLabel("Percentual de Risco:"));
        riscoField = new JTextField();
        
        riscoField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                String text = riscoField.getText();

                if (!Character.isDigit(c) && c != '.' || (c == '.' && text.contains("."))) {
                    System.out.print(c + text);
                    evt.consume();
                }
            }
        });
        
        add(riscoField);

        add(new JLabel("Rentabilidade Esperada (%):"));
        rentabilidadeField = new JTextField();
        
        rentabilidadeField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                String text = rentabilidadeField.getText();

                if (!Character.isDigit(c) && c != '.' || (c == '.' && text.contains("."))) {
                    System.out.print(c + text);
                    evt.consume();
                }
            }
        });
        
        add(rentabilidadeField);

        salvarButton = new JButton("Salvar");
        add(salvarButton);
        add(new JLabel(""));

        salvarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String descricao = descricaoField.getText();
                String risco = riscoField.getText();
                String rentabilidade = rentabilidadeField.getText();
                if(nome.isEmpty() || risco.isEmpty() || rentabilidade.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                } else {
                    JOptionPane.showMessageDialog(null, "Opção de renda variável cadastrada com sucesso!");

                    gerente.cadastrarRendaVariavel(nome, descricao, 0, Double.parseDouble(risco), Double.parseDouble(rentabilidade));
                    
                    parent.setVisible(true);
                    dispose();
                }
            }
        });
        
        JButton btnSair = new JButton("Voltar");
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                parent.setVisible(true);
            }
        });
        add(btnSair);
        
        setLocationRelativeTo(null);
    }
}

