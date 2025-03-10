package com.mycompany.bank.gui;

import com.mycompany.bank.model.Gerente;
import com.mycompany.bank.model.SolicitacaoCred;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GerenteAvaliacaoCredito extends JFrame {
    public GerenteAvaliacaoCredito(JFrame parent, Gerente gerente) {
        super("Avaliação de Crédito");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        List<SolicitacaoCred> lista = gerente.getNotApproved();
        
        for (SolicitacaoCred solicitacao : lista) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new FlowLayout());
            
            String clientName = gerente.getClientNameById(solicitacao.getUserId());
            JLabel label = new JLabel(clientName + " - Valor: R$ " + solicitacao.getValue());
            
            JButton btnAprovar = new JButton("Aprovar");
            JButton btnRecusar = new JButton("Recusar");
            
            btnAprovar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gerente.aceitarSolicitacao(solicitacao.getId());
                    JOptionPane.showMessageDialog(GerenteAvaliacaoCredito.this, "Solicitação aprovada!");
                    dispose();
                    new GerenteAvaliacaoCredito(parent, gerente).setVisible(true);
                }
            });
            
            btnRecusar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gerente.recusarSolicitacao(solicitacao.getId());
                    JOptionPane.showMessageDialog(GerenteAvaliacaoCredito.this, "Solicitação recusada!");
                    dispose();
                    new GerenteAvaliacaoCredito(parent, gerente).setVisible(true);
                }
            });
            
            itemPanel.add(label);
            itemPanel.add(btnAprovar);
            itemPanel.add(btnRecusar);
            
            panel.add(itemPanel);
        }
        
        JButton btnSair = new JButton("Voltar");
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                parent.setVisible(true);
            }
        });
        panel.add(btnSair);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
    }
}