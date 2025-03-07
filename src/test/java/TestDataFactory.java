import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.model.Gerente;
import com.mycompany.bank.model.Caixa;
import com.mycompany.bank.model.Conta;
import com.mycompany.bank.model.RendaFixa;
import com.mycompany.bank.model.RendaVariavel;
import com.mycompany.bank.model.SolicitacaoCred;
import com.mycompany.bank.service.SistemaBancario;

/**
 * Classe utilitária para criação de dados de teste
 */
public class TestDataFactory {
    
    /**
     * Cria e retorna um sistema bancário com usuários padrão para testes
     */
    public static SistemaBancario criarSistemaBancarioComUsuarios() {
        SistemaBancario sistema = new SistemaBancario();
        
        // Clientes
        Cliente cliente1 = new Cliente("Paulo Souza", "111.222.333-44", "senha123", sistema);
        Cliente cliente2 = new Cliente("Juliana Lima", "222.333.444-55", "senha456", sistema);
        Cliente cliente3 = new Cliente("Fernando Costa", "333.444.555-66", "senha789", sistema);
        
        // Gerentes
        Gerente gerente1 = new Gerente("Roberta Santos", "444.555.666-77", "gerente123", sistema);
        Gerente gerente2 = new Gerente("Marcelo Silva", "555.666.777-88", "gerente456", sistema);
        
        // Caixas
        Caixa caixa1 = new Caixa("João Pereira", "666.777.888-99", "caixa123", sistema);
        
        // Cadastra usuários no sistema
        sistema.cadastrarUsuario(cliente1);
        sistema.cadastrarUsuario(cliente2);
        sistema.cadastrarUsuario(cliente3);
        sistema.cadastrarUsuario(gerente1);
        sistema.cadastrarUsuario(gerente2);
        sistema.cadastrarUsuario(caixa1);
        
        // Cria e adiciona contas para os clientes
        Conta conta1 = new Conta("C001", 2000.0, cliente1.getId());
        Conta conta2 = new Conta("C002", 5000.0, cliente2.getId());
        Conta conta3 = new Conta("C003", 1500.0, cliente3.getId());
        Conta conta4 = new Conta("C004", 3000.0, cliente1.getId()); // Segunda conta do cliente1
        
        sistema.adicionarConta(conta1);
        sistema.adicionarConta(conta2);
        sistema.adicionarConta(conta3);
        sistema.adicionarConta(conta4);
        
        return sistema;
    }
    
    /**
     * Cria uma variedade de investimentos de renda fixa para testes
     */
    public static RendaFixa[] criarRendasFixas() {
        return new RendaFixa[] {
            new RendaFixa("CDB Banco ABC", "CDB com rentabilidade prefixada", 1000.0, 0.06, 90, 720),
            new RendaFixa("LCI Premium", "Letra de Crédito Imobiliário", 2000.0, 0.055, 180, 1080),
            new RendaFixa("Tesouro Direto", "Títulos públicos", 50.0, 0.045, 30, 1800)
        };
    }
    
    /**
     * Cria uma variedade de investimentos de renda variável para testes
     */
    public static RendaVariavel[] criarRendasVariaveis() {
        return new RendaVariavel[] {
            new RendaVariavel("Ações Vale", "Ações da empresa Vale", 100.0, 0.18, 0.15),
            new RendaVariavel("Fundo Imobiliário XP", "Fundo de investimento imobiliário", 500.0, 0.12, 0.09),
            new RendaVariavel("ETF Índice", "ETF que acompanha o índice Bovespa", 200.0, 0.14, 0.11)
        };
    }
    
    /**
     * Cria solicitações de crédito para um cliente específico
     */
    public static SolicitacaoCred[] criarSolicitacoesCredito(int clienteId) {
        return new SolicitacaoCred[] {
            new SolicitacaoCred(clienteId, 10000.0, "Reforma de casa"),
            new SolicitacaoCred(clienteId, 5000.0, "Viagem internacional"),
            new SolicitacaoCred(clienteId, 20000.0, "Compra de automóvel"),
            new SolicitacaoCred(clienteId, 2000.0, "Curso de especialização")
        };
    }
} 