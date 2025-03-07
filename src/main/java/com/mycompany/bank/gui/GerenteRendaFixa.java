package com.mycompany.bank.gui;

import com.mycompany.bank.model.Gerente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GerenteRendaFixa extends JFrame {
    private JTextField nomeField;
    private JTextField descricaoField;
    private JTextField taxaField;
    private JTextField prazoMinField;
    private JTextField prazoMaxField;
    private JButton salvarButton;

    public GerenteRendaFixa(JFrame parent, Gerente gerente) {
        super("Cadastro Renda Fixa");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Nome:"));
        nomeField = new JTextField();
        add(nomeField);
        
        add(new JLabel("Descricao:"));
        descricaoField = new JTextField();
        add(descricaoField);
        
        add(new JLabel("Taxa de Rendimento:"));
        taxaField = new JTextField();
        
        taxaField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                String text = taxaField.getText();

                if (!Character.isDigit(c) && c != '.' || (c == '.' && text.contains("."))) {
                    System.out.print(c + text);
                    evt.consume();
                }
            }
        });
        
        add(taxaField);

        add(new JLabel("Prazo Mínimo (dias):"));
        prazoMinField = new JTextField();
        
        prazoMinField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                String text = prazoMinField.getText();

                if (!Character.isDigit(c)) {
                    evt.consume();
                }
            }
        });
        
        add(prazoMinField);

        add(new JLabel("Prazo Máximo (dias):"));
        prazoMaxField = new JTextField();
        
        prazoMaxField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                String text = prazoMinField.getText();

                if (!Character.isDigit(c)) {
                    evt.consume();
                }
            }
        });
        
        add(prazoMaxField);

        salvarButton = new JButton("Salvar");
        add(salvarButton);
        
        add(new JLabel(""));

        salvarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String descricao = descricaoField.getText();
                String taxa = taxaField.getText();
                String prazoMin = prazoMinField.getText();
                String prazoMax = prazoMaxField.getText();

                if (taxa.isEmpty() || prazoMin.isEmpty() || prazoMax.isEmpty() || nome.isEmpty() || descricao.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                } else {
                    JOptionPane.showMessageDialog(null, "Opção de renda fixa cadastrada com sucesso!");

                    gerente.cadastrarRendaFixa(nome, descricao, 0.0, Double.parseDouble(taxa), Integer.parseInt(prazoMin), Integer.parseInt(prazoMax));
                    
                    parent.setVisible(true);
                    dispose();
                }
            }
        });
        
        setLocationRelativeTo(null);
    }
}

