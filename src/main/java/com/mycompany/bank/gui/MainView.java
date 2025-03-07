package com.mycompany.bank.gui;

import com.mycompany.bank.service.SistemaBancario;
import com.mycompany.bank.model.Usuario;
import com.mycompany.bank.gui.ActionsView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private JTextField cpfField;
    private JPasswordField senhaField;
    private JButton loginButton;

    public MainView(SistemaBancario sistema) {
        super("Sistema Bancário - Login");
        
        MainView instancia = this;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new FlowLayout());

        add(new JLabel("CPF:"));
        cpfField = new JTextField(20);
        add(cpfField);

        add(new JLabel("Senha:"));
        senhaField = new JPasswordField(19);
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
                    setVisible(false);
                    
                    ActionsView actionsView = new ActionsView(sistema, user, instancia);
                    actionsView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "CPF ou senha inválidos.");
                }
            }
        });
        
        add(loginButton);

        setLocationRelativeTo(null);
    }
}
