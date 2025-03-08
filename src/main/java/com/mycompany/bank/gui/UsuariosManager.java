package com.mycompany.bank.gui;

import com.mycompany.bank.model.Caixa;
import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.model.Gerente;
import com.mycompany.bank.model.Usuario;
import com.mycompany.bank.service.SistemaBancario;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Interface para gerenciamento de usuários (Cliente, Caixa, Gerente)
 */
public class UsuariosManager extends JFrame {
    
    private final SistemaBancario sistema;
    private JPanel mainPanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JList<Usuario> usuariosList;
    private DefaultListModel<Usuario> usuariosModel;
    
    private JTextField nomeField;
    private JTextField cpfField;
    private JPasswordField senhaField;
    private JComboBox<String> tipoUsuarioCombo;
    
    // Para edição
    private JTextField nomeEditField;
    private JTextField cpfEditField;
    private JPasswordField senhaEditField;
    private JComboBox<String> tipoUsuarioEditCombo;
    
    private static final String LISTAGEM_PANEL = "Listagem";
    private static final String CADASTRO_PANEL = "Cadastro";
    private static final String EDICAO_PANEL = "Edicao";
    
    public UsuariosManager(SistemaBancario sistema) {
        this.sistema = sistema;
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Gerenciamento de Usuários");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout());
        
        // Painel de cards para alternar entre listagem, cadastro e edição
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        
        // Adicionar os painéis específicos aos cards
        cardPanel.add(createListagemPanel(), LISTAGEM_PANEL);
        cardPanel.add(createCadastroPanel(), CADASTRO_PANEL);
        cardPanel.add(createEdicaoPanel(), EDICAO_PANEL);
        
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        
        getContentPane().add(mainPanel);
        
        // Iniciar mostrando a listagem
        cardLayout.show(cardPanel, LISTAGEM_PANEL);
    }
    
    private JPanel createListagemPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Modelo e lista de usuários
        usuariosModel = new DefaultListModel<>();
        carregarUsuarios();
        
        usuariosList = new JList<>(usuariosModel);
        usuariosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usuariosList.setCellRenderer(new UsuarioListCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(usuariosList);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Painel para os botões
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton adicionarButton = new JButton("Adicionar Usuário");
        JButton editarButton = new JButton("Editar Usuário");
        JButton removerButton = new JButton("Remover Usuário");
        JButton voltarButton = new JButton("Voltar");
        
        adicionarButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, CADASTRO_PANEL);
        });
        
        editarButton.addActionListener((ActionEvent e) -> {
            Usuario selecionado = usuariosList.getSelectedValue();
            if (selecionado != null) {
                preencherCamposEdicao(selecionado);
                cardLayout.show(cardPanel, EDICAO_PANEL);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.");
            }
        });
        
        removerButton.addActionListener((ActionEvent e) -> {
            Usuario selecionado = usuariosList.getSelectedValue();
            if (selecionado != null) {
                int confirma = JOptionPane.showConfirmDialog(this, 
                        "Tem certeza que deseja remover o usuário " + selecionado.getNome() + "?",
                        "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
                
                if (confirma == JOptionPane.YES_OPTION) {
                    sistema.removerUsuario(selecionado.getCpf());
                    carregarUsuarios();
                    JOptionPane.showMessageDialog(this, "Usuário removido com sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para remover.");
            }
        });
        
        voltarButton.addActionListener((ActionEvent e) -> {
            dispose();
        });
        
        botoesPanel.add(adicionarButton);
        botoesPanel.add(editarButton);
        botoesPanel.add(removerButton);
        botoesPanel.add(voltarButton);
        
        panel.add(botoesPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createCadastroPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Nome
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nomeField = new JTextField(20);
        formPanel.add(nomeField, gbc);
        
        // CPF
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cpfField = new JTextField(20);
        formPanel.add(cpfField, gbc);
        
        // Senha
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        senhaField = new JPasswordField(20);
        formPanel.add(senhaField, gbc);
        
        // Tipo de Usuário
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Tipo de Usuário:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tipoUsuarioCombo = new JComboBox<>(new String[]{"Cliente", "Caixa", "Gerente"});
        formPanel.add(tipoUsuarioCombo, gbc);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton salvarButton = new JButton("Salvar");
        JButton cancelarButton = new JButton("Cancelar");
        
        salvarButton.addActionListener((ActionEvent e) -> {
            if (salvarNovoUsuario()) {
                // Limpar campos
                nomeField.setText("");
                cpfField.setText("");
                senhaField.setText("");
                tipoUsuarioCombo.setSelectedIndex(0);
                
                // Voltar para listagem
                cardLayout.show(cardPanel, LISTAGEM_PANEL);
            }
        });
        
        cancelarButton.addActionListener((ActionEvent e) -> {
            // Limpar campos
            nomeField.setText("");
            cpfField.setText("");
            senhaField.setText("");
            tipoUsuarioCombo.setSelectedIndex(0);
            
            // Voltar para listagem
            cardLayout.show(cardPanel, LISTAGEM_PANEL);
        });
        
        botoesPanel.add(salvarButton);
        botoesPanel.add(cancelarButton);
        
        panel.add(botoesPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createEdicaoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Nome
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        nomeEditField = new JTextField(20);
        formPanel.add(nomeEditField, gbc);
        
        // CPF
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cpfEditField = new JTextField(20);
        formPanel.add(cpfEditField, gbc);
        
        // Senha
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        senhaEditField = new JPasswordField(20);
        formPanel.add(senhaEditField, gbc);
        
        // Tipo de Usuário
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Tipo de Usuário:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tipoUsuarioEditCombo = new JComboBox<>(new String[]{"Cliente", "Caixa", "Gerente"});
        formPanel.add(tipoUsuarioEditCombo, gbc);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton salvarButton = new JButton("Salvar Alterações");
        JButton cancelarButton = new JButton("Cancelar");
        
        salvarButton.addActionListener((ActionEvent e) -> {
            if (salvarEdicaoUsuario()) {
                // Voltar para listagem
                cardLayout.show(cardPanel, LISTAGEM_PANEL);
            }
        });
        
        cancelarButton.addActionListener((ActionEvent e) -> {
            // Voltar para listagem
            cardLayout.show(cardPanel, LISTAGEM_PANEL);
        });
        
        botoesPanel.add(salvarButton);
        botoesPanel.add(cancelarButton);
        
        panel.add(botoesPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void carregarUsuarios() {
        usuariosModel.clear();
        for (Usuario usuario : sistema.getUsuarios()) {
            usuariosModel.addElement(usuario);
        }
    }
    
    private boolean salvarNovoUsuario() {
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().trim();
        String senha = new String(senhaField.getPassword());
        String tipoUsuario = (String) tipoUsuarioCombo.getSelectedItem();
        
        // Validação básica
        if (nome.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.");
            return false;
        }
        
        // Verificar se o CPF já está em uso
        if (sistema.buscarUsuarioPorCpf(cpf) != null) {
            JOptionPane.showMessageDialog(this, "CPF já cadastrado no sistema.");
            return false;
        }
        
        // Criar o usuário de acordo com o tipo selecionado
        Usuario novoUsuario = null;
        
        switch (tipoUsuario) {
            case "Cliente":
                novoUsuario = new Cliente(nome, cpf, senha, sistema);
                break;
            case "Caixa":
                novoUsuario = new Caixa(nome, cpf, senha, sistema);
                break;
            case "Gerente":
                novoUsuario = new Gerente(nome, cpf, senha, sistema);
                break;
        }
        
        // Adicionar o usuário e salvar
        if (novoUsuario != null) {
            sistema.adicionarUsuario(novoUsuario);
            carregarUsuarios();
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            return true;
        }
        
        return false;
    }
    
    private void preencherCamposEdicao(Usuario usuario) {
        nomeEditField.setText(usuario.getNome());
        cpfEditField.setText(usuario.getCpf());
        senhaEditField.setText(usuario.getSenha());
        
        // Determinar o tipo de usuário
        String tipoUsuario;
        if (usuario instanceof Cliente) {
            tipoUsuario = "Cliente";
        } else if (usuario instanceof Caixa) {
            tipoUsuario = "Caixa";
        } else if (usuario instanceof Gerente) {
            tipoUsuario = "Gerente";
        } else {
            tipoUsuario = "Cliente"; // Default
        }
        
        tipoUsuarioEditCombo.setSelectedItem(tipoUsuario);
    }
    
    private boolean salvarEdicaoUsuario() {
        Usuario usuarioSelecionado = usuariosList.getSelectedValue();
        if (usuarioSelecionado == null) {
            return false;
        }
        
        String nome = nomeEditField.getText().trim();
        String cpf = cpfEditField.getText().trim();
        String senha = new String(senhaEditField.getPassword());
        String tipoUsuario = (String) tipoUsuarioEditCombo.getSelectedItem();
        
        // Validação básica
        if (nome.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.");
            return false;
        }
        
        // Verificar se o CPF já está em uso por outro usuário
        if (!cpf.equals(usuarioSelecionado.getCpf())) {
            Usuario existente = sistema.buscarUsuarioPorCpf(cpf);
            if (existente != null) {
                JOptionPane.showMessageDialog(this, "CPF já cadastrado para outro usuário.");
                return false;
            }
        }
        
        // Se o tipo de usuário mudou, precisamos remover o antigo e criar um novo
        boolean tipoMudou = false;
        
        if ((usuarioSelecionado instanceof Cliente && !tipoUsuario.equals("Cliente")) ||
            (usuarioSelecionado instanceof Caixa && !tipoUsuario.equals("Caixa")) ||
            (usuarioSelecionado instanceof Gerente && !tipoUsuario.equals("Gerente"))) {
            tipoMudou = true;
        }
        
        if (tipoMudou) {
            // Remover o usuário antigo
            sistema.removerUsuario(usuarioSelecionado.getCpf());
            
            // Criar um novo usuário do tipo escolhido
            Usuario novoUsuario = null;
            switch (tipoUsuario) {
                case "Cliente":
                    novoUsuario = new Cliente(nome, cpf, senha, sistema);
                    break;
                case "Caixa":
                    novoUsuario = new Caixa(nome, cpf, senha, sistema);
                    break;
                case "Gerente":
                    novoUsuario = new Gerente(nome, cpf, senha, sistema);
                    break;
            }
            
            // Adicionar o novo usuário
            if (novoUsuario != null) {
                sistema.adicionarUsuario(novoUsuario);
            }
        } else {
            // Apenas atualizar os dados do usuário existente
            usuarioSelecionado.setNome(nome);
            usuarioSelecionado.setCpf(cpf);
            usuarioSelecionado.setSenha(senha);
            sistema.atualizarUsuario(usuarioSelecionado);
        }
        
        carregarUsuarios();
        JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
        return true;
    }
    
    // Renderer personalizado para exibir informações do usuário na lista
    private class UsuarioListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Usuario) {
                Usuario usuario = (Usuario) value;
                String tipo = "";
                
                if (usuario instanceof Cliente) {
                    tipo = "Cliente";
                } else if (usuario instanceof Caixa) {
                    tipo = "Caixa";
                } else if (usuario instanceof Gerente) {
                    tipo = "Gerente";
                }
                
                setText(String.format("%s - %s (%s)", usuario.getNome(), usuario.getCpf(), tipo));
            }
            
            return c;
        }
    }
} 