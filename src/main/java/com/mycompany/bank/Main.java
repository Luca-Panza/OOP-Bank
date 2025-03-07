package com.mycompany.bank;

import com.mycompany.bank.gui.MainView;
import com.mycompany.bank.service.SistemaBancario;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Cria o sistema
        SistemaBancario sistema = new SistemaBancario();

        // Inicia a GUI (Swing)
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView(sistema);
            mainView.setVisible(true);
        });
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            sistema.saveAll();
        }));
    }
}
