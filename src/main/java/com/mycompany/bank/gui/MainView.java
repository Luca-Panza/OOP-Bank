/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bank.gui;

import com.mycompany.bank.service.SistemaBancario;
import com.mycompany.bank.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Exemplo mínimo de uma tela de login em Swing.
 * Em um projeto real, você pode ter várias telas diferentes para cada perfil.
 */
public class MainView extends JFrame {

    private JTextField cpfField;
    private JPasswordField senhaField;
    private JButton loginButton;

    private SistemaBancario sistema;

    public MainView(SistemaBancario sistema) {
        super("Sistema Bancário - Login");
        this.sistema = sistema;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new FlowLayout());

        add(new JLabel("CPF:"));
        cpfField = new JTextField(15);
        add(cpfField);

        add(new JLabel("Senha:"));
        senhaField = new JPasswordField(15);
        add(senhaField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();
                String senha = new String(senhaField.getPassword());

                Usuario user = sistema.login(cpf, senha);
                if (user != null) {
                    JOptionPane.showMessageDialog(null, "Bem-vindo, " + user.getNome());
                    // Aqui você pode abrir outra tela (ex. TelaCliente, TelaCaixa, TelaGerente, etc.)
                } else {
                    JOptionPane.showMessageDialog(null, "CPF ou senha inválidos.");
                }
            }
        });
        add(loginButton);

        setLocationRelativeTo(null);
    }
}
