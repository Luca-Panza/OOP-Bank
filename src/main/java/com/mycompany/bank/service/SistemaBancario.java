package com.mycompany.bank.service;

import com.mycompany.bank.model.*;
import com.mycompany.bank.wrappers.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.lang.reflect.Type;

/**
 * Classe de serviço que gerencia as listas de usuários, contas, etc.
 */
public class SistemaBancario {

    private static final String USUARIOS_FILE = "./files/usuarios.json";
    private static final String CONTAS_FILE = "./files/contas.json";
    private static final String INVESTIMENTOS_FILE = "./files/investimentos.json";
    private static final String SOLICITACOES_CRED_FILE = "./files/solicitacoesCred.json";

    private List<Usuario> usuarios;
    private List<Conta> contas;
    private List<Investimento> investimentos;
    private List<SolicitacaoCred> solicitacoesCred;

    RuntimeTypeAdapterFactory<Usuario> usuarioAdapterFactory
            = RuntimeTypeAdapterFactory.of(Usuario.class, "tipo")
                    .registerSubtype(Gerente.class, "Gerente")
                    .registerSubtype(Caixa.class, "Caixa")
                    .registerSubtype(Cliente.class, "Cliente");

    RuntimeTypeAdapterFactory<Investimento> investimentoAdapterFactory
            = RuntimeTypeAdapterFactory.of(Investimento.class, "tipo")
                    .registerSubtype(RendaFixa.class, "Renda Fixa")
                    .registerSubtype(RendaVariavel.class, "Renda Variavel");

    Gson gson = new GsonBuilder().registerTypeAdapterFactory(usuarioAdapterFactory)
            .registerTypeAdapterFactory(investimentoAdapterFactory).excludeFieldsWithoutExposeAnnotation().create();

    public SistemaBancario() {
        this.usuarios = loadFromFile(USUARIOS_FILE, new TypeToken<List<Usuario>>() {
        }.getType());
        this.contas = loadFromFile(CONTAS_FILE, new TypeToken<List<Conta>>() {
        }.getType());
        this.investimentos = loadFromFile(INVESTIMENTOS_FILE, new TypeToken<List<Investimento>>() {
        }.getType());
        this.solicitacoesCred = loadFromFile(SOLICITACOES_CRED_FILE, new TypeToken<List<SolicitacaoCred>>() {
        }.getType());
        
        for (Usuario u : usuarios) {
            u.setSistema(this);
        }
    }

    public void adicionarUsuario(Usuario u) {
        this.usuarios.add(u);
    }

    public Usuario buscarUsuarioPorCpf(String cpf) {
        for (Usuario u : usuarios) {
            if (u.getCpf().equalsIgnoreCase(cpf)) {
                return u;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }

        return null;
    }

    public void removerUsuario(String cpf) {
        usuarios.removeIf(u -> u.getCpf().equalsIgnoreCase(cpf));
    }

    public Usuario login(String cpf, String senha) {
        Usuario u = buscarUsuarioPorCpf(cpf);
        if (u != null && u.autenticar(senha)) {
            return u;
        }
        return null;
    }

    // Métodos para gerenciar contas
    public void adicionarConta(Conta c) {
        this.contas.add(c);
    }

    public Conta buscarContaPorNumero(String numero) {
        for (Conta c : contas) {
            if (c.getNumero().equals(numero)) {
                return c;
            }
        }
        return null;
    }

    public List<Conta> buscarContaPorUsuario(int userId) {
        List<Conta> contasUsuario = new ArrayList<>();

        for (Conta c : contas) {
            if (c.getUserId() == userId) {
                contasUsuario.add(c);
            }
        }
        return contasUsuario;
    }

    // Métodos para gerenciar investimentos
    public void adicionarInvestimento(Investimento i) {
        this.investimentos.add(i);
    }

    public List<Investimento> getInvestimentos() {
        return investimentos;
    }

    public Cliente getClienteByAccount(String conta) {
        Conta contaEncontrada = buscarContaPorNumero(conta);

        if (contaEncontrada != null) {
            return (Cliente) buscarUsuarioPorId(contaEncontrada.getUserId());
        }

        return null;
    }

    public List<SolicitacaoCred> getSolitacoesCred() {
        return this.solicitacoesCred;
    }

    public void adicionarSolicitacaoCred(int userId, double value, String reason) {
        this.solicitacoesCred.add(new SolicitacaoCred(userId, value, reason));
    }

    public List<SolicitacaoCred> getNotApprovedCred() {
        List<SolicitacaoCred> notApproved = new ArrayList<>();

        for (SolicitacaoCred solicitacao : solicitacoesCred) {
            if (!solicitacao.isApproved()) {
                notApproved.add(solicitacao);
            }
        }

        return notApproved;
    }

    public void aceitaSolicitacao(int id) {
        for (SolicitacaoCred solicitacao : solicitacoesCred) {
            if (solicitacao.getId() == id) {
                solicitacao.setanAlyzed();
                solicitacao.setApproved();
                break;
            }
        }
    }

    public void recusarSolicitacao(int id) {
        for (SolicitacaoCred solicitacao : solicitacoesCred) {
            if (solicitacao.getId() == id) {
                solicitacao.setanAlyzed();
                break;
            }
        }
    }

    public <T> void saveToFile(String fileName, List<T> data) {
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> loadFromFile(String fileName, Type type) {
        File file = new File(fileName);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveAll() {
        saveToFile(USUARIOS_FILE, usuarios);
        saveToFile(CONTAS_FILE, contas);
        saveToFile(INVESTIMENTOS_FILE, investimentos);
        saveToFile(SOLICITACOES_CRED_FILE, solicitacoesCred);
        saveAllXml();
    }

    public <T> void saveToFileXML(String fileName, T wrapper) {
        try {
            JAXBContext context = JAXBContext.newInstance(wrapper.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wrapper, new File(fileName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void saveAllXml() {
        UsuarioListWrapper usuarioListWrapper = new UsuarioListWrapper();
        ContaListWrapper contaListWrapper = new ContaListWrapper();
        InvestimentoListWrapper investimentoListWrapper = new InvestimentoListWrapper();
        SolicitacaoCredListWrapper solicitacaoCredListWrapper = new SolicitacaoCredListWrapper();

        usuarioListWrapper.setUsuarios(usuarios);
        contaListWrapper.setContas(contas);
        investimentoListWrapper.setInvestimentos(investimentos);
        solicitacaoCredListWrapper.setSolicitacaoCreds(solicitacoesCred);

        saveToFileXML(USUARIOS_FILE.replace(".json", ".xml"), usuarioListWrapper);
        saveToFileXML(CONTAS_FILE.replace(".json", ".xml"), contaListWrapper);
        saveToFileXML(INVESTIMENTOS_FILE.replace(".json", ".xml"), investimentoListWrapper);
        saveToFileXML(SOLICITACOES_CRED_FILE.replace(".json", ".xml"), solicitacaoCredListWrapper);
    }
}
