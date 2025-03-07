import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.model.Gerente;
import com.mycompany.bank.model.Conta;
import com.mycompany.bank.model.RendaFixa;
import com.mycompany.bank.model.RendaVariavel;
import com.mycompany.bank.model.SolicitacaoCred;
import com.mycompany.bank.service.SistemaBancario;
import com.mycompany.bank.exceptions.SaldoInsuficienteException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testes de integração do sistema bancário
 * Testa múltiplas funcionalidades em conjunto
 */
public class IntegracaoSistemaBancarioTest {
    
    private SistemaBancario sistema;
    private Cliente cliente;
    private Gerente gerente;
    private Conta contaCliente;
    private RendaFixa[] rendasFixas;
    private RendaVariavel[] rendasVariaveis;
    
    @Before
    public void setUp() {
        // Utiliza a fábrica de dados de teste para criar o ambiente
        sistema = TestDataFactory.criarSistemaBancarioComUsuarios();
        
        // Obtém o primeiro cliente e gerente do sistema para os testes
        cliente = (Cliente) sistema.getUsuarios().stream()
                .filter(u -> u instanceof Cliente)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum cliente encontrado"));
        
        gerente = (Gerente) sistema.getUsuarios().stream()
                .filter(u -> u instanceof Gerente)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum gerente encontrado"));
        
        // Obtém a primeira conta do cliente
        contaCliente = sistema.getContas().stream()
                .filter(c -> c.getUserId() == cliente.getId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhuma conta encontrada para o cliente"));
        
        // Cria investimentos para testes
        rendasFixas = TestDataFactory.criarRendasFixas();
        rendasVariaveis = TestDataFactory.criarRendasVariaveis();
    }
    
    @Test
    public void testFluxoOperacoesCompleto() throws SaldoInsuficienteException {
        // 1. Verifica saldo inicial
        double saldoInicial = contaCliente.getSaldo();
        Assert.assertTrue("Saldo inicial deve ser positivo", saldoInicial > 0);
        
        // 2. Realiza depósito
        double valorDeposito = 1000.0;
        contaCliente.depositar(valorDeposito);
        Assert.assertEquals(saldoInicial + valorDeposito, contaCliente.getSaldo(), 0.001);
        
        // 3. Realiza saque
        double valorSaque = 500.0;
        contaCliente.sacar(valorSaque);
        Assert.assertEquals(saldoInicial + valorDeposito - valorSaque, contaCliente.getSaldo(), 0.001);
        
        // 4. Aplica em renda fixa
        RendaFixa rendaFixaEscolhida = rendasFixas[0]; // CDB
        double valorAplicacao = 1000.0;
        rendaFixaEscolhida.aplicar(valorAplicacao);
        
        // 5. Faz uma transferência para outra conta
        Conta contaDestino = sistema.getContas().stream()
                .filter(c -> c.getUserId() != cliente.getId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhuma conta de outro cliente encontrada"));
        
        double saldoDestinoInicial = contaDestino.getSaldo();
        double valorTransferencia = 300.0;
        
        contaCliente.transferir(contaDestino, valorTransferencia);
        
        Assert.assertEquals(saldoInicial + valorDeposito - valorSaque - valorTransferencia, 
                           contaCliente.getSaldo(), 0.001);
        Assert.assertEquals(saldoDestinoInicial + valorTransferencia, 
                           contaDestino.getSaldo(), 0.001);
        
        // 6. Aplica em renda variável
        RendaVariavel rendaVariavelEscolhida = rendasVariaveis[0]; // Ações
        double valorAplicacaoRV = 500.0;
        rendaVariavelEscolhida.aplicar(valorAplicacaoRV);
        
        // 7. Solicita crédito
        SolicitacaoCred solicitacao = new SolicitacaoCred(cliente.getId(), 10000.0, "Reforma de casa");
        
        // 8. Gerente analisa e aprova solicitação
        solicitacao.setanAlyzed();
        solicitacao.setApproved();
        Assert.assertTrue(solicitacao.isAnalyzed());
        Assert.assertTrue(solicitacao.isApproved());
        
        // 9. Cliente aceita solicitação
        solicitacao.setAccepted();
        Assert.assertTrue(solicitacao.isAccepted());
        
        // 10. Realiza resgate de investimento
        rendaFixaEscolhida.resgatar(500.0);
        rendaVariavelEscolhida.resgatar(200.0);
    }
    
    @Test
    public void testFluxoMultiplasContasCliente() throws SaldoInsuficienteException {
        // Obtém as contas do cliente (deve ter pelo menos duas pela configuração da fábrica)
        Conta[] contasCliente = sistema.getContas().stream()
                .filter(c -> c.getUserId() == cliente.getId())
                .toArray(Conta[]::new);
        
        Assert.assertTrue("Cliente deve ter pelo menos 2 contas", contasCliente.length >= 2);
        
        Conta conta1 = contasCliente[0];
        Conta conta2 = contasCliente[1];
        
        // Verifica saldos iniciais
        double saldoInicial1 = conta1.getSaldo();
        double saldoInicial2 = conta2.getSaldo();
        
        // Transferência entre contas do mesmo cliente
        double valorTransferencia = 500.0;
        conta1.transferir(conta2, valorTransferencia);
        
        // Verifica saldos após transferência
        Assert.assertEquals(saldoInicial1 - valorTransferencia, conta1.getSaldo(), 0.001);
        Assert.assertEquals(saldoInicial2 + valorTransferencia, conta2.getSaldo(), 0.001);
    }
    
    @Test
    public void testFluxoInvestimentos() {
        // Testa aplicação em múltiplos investimentos
        for (RendaFixa rf : rendasFixas) {
            double valorAplicacao = rf.getValorMinimo() * 2; // Valor válido
            rf.aplicar(valorAplicacao);
        }
        
        for (RendaVariavel rv : rendasVariaveis) {
            double valorAplicacao = rv.getValorMinimo() * 2; // Valor válido
            rv.aplicar(valorAplicacao);
        }
        
        // Testa resgates
        rendasFixas[0].resgatar(rendasFixas[0].getValorMinimo());
        rendasVariaveis[0].resgatar(rendasVariaveis[0].getValorMinimo());
    }
} 