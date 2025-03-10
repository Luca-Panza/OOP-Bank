package com.mycompany.bank.gui;

import com.mycompany.bank.model.Gerente;
import com.mycompany.bank.gui.ActionsView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GerenteMovFinanceiras extends JFrame {
    private JButton saqueButton;
    private JButton tranferenciaButton;

    public GerenteMovFinanceiras(ActionsView parent, Gerente gerente) {
        super("Apoio Movimentações Financeiras");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new FlowLayout());

        saqueButton = new JButton("Saque");
        
        saqueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SaqueCaixa saqueCaixa = new SaqueCaixa(parent, (Gerente) gerente);

                    saqueCaixa.setVisible(true);
                    parent.setVisible(false);
                }
            });
        
        add(saqueButton);

        tranferenciaButton = new JButton("Transferência");
        
        tranferenciaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CaixaTransf caixaTransf = new CaixaTransf(parent, (Gerente) gerente);

                    caixaTransf.setVisible(true);
                    parent.setVisible(false);
                }
            });
        
        add(tranferenciaButton);
        
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
