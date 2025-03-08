import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.model.Gerente;
import com.mycompany.bank.model.SolicitacaoCred;
import com.mycompany.bank.service.SistemaBancario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Testes de integração para o fluxo completo de solicitações de crédito
 */
public class SolicitacaoCreditoIntegracaoTest {
    
    private SistemaBancario sistema;
    private Cliente cliente1;
    private Cliente cliente2;
    private Gerente gerente;
    private List<SolicitacaoCred> solicitacoesCliente1;
    private List<SolicitacaoCred> solicitacoesCliente2;
    
    @BeforeEach
    public void setUp() {
        // Inicializa sistema
        sistema = TestDataFactory.criarSistemaBancarioComUsuarios();
        
        // Obtém dois clientes diferentes para testes
        List<Cliente> clientes = sistema.getUsuarios().stream()
                .filter(u -> u instanceof Cliente)
                .map(u -> (Cliente) u)
                .limit(2)
                .collect(Collectors.toList());
        
        cliente1 = clientes.get(0);
        cliente2 = clientes.get(1);
        
        // Obtém um gerente para análise
        gerente = (Gerente) sistema.getUsuarios().stream()
                .filter(u -> u instanceof Gerente)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum gerente encontrado"));
        
        // Cria solicitações de crédito para os clientes
        solicitacoesCliente1 = new ArrayList<>();
        solicitacoesCliente2 = new ArrayList<>();
        
        for (SolicitacaoCred solicitacao : TestDataFactory.criarSolicitacoesCredito(cliente1.getId())) {
            solicitacoesCliente1.add(solicitacao);
        }
        
        for (SolicitacaoCred solicitacao : TestDataFactory.criarSolicitacoesCredito(cliente2.getId())) {
            solicitacoesCliente2.add(solicitacao);
        }
    }
    
    @Test
    public void testProcessoCompletoDeSolicitacaoCredito() {
        // Pega a primeira solicitação do cliente 1
        SolicitacaoCred solicitacao = solicitacoesCliente1.get(0);
        
        // Verifica estado inicial
        Assertions.assertFalse(solicitacao.isAnalyzed());
        Assertions.assertFalse(solicitacao.isApproved());
        Assertions.assertFalse(solicitacao.isAccepted());
        
        // Gerente analisa e aprova
        solicitacao.setanAlyzed();
        Assertions.assertTrue(solicitacao.isAnalyzed());
        Assertions.assertFalse(solicitacao.isApproved()); // Ainda não aprovada
        
        solicitacao.setApproved();
        Assertions.assertTrue(solicitacao.isApproved());
        
        // Cliente aceita
        solicitacao.setAccepted();
        Assertions.assertTrue(solicitacao.isAccepted());
    }
    
    @Test
    public void testAnaliseMultiplasSolicitacoes() {
        // O gerente analisa todas as solicitações do cliente 1
        for (SolicitacaoCred solicitacao : solicitacoesCliente1) {
            solicitacao.setanAlyzed();
            Assertions.assertTrue(solicitacao.isAnalyzed());
            
            // Aprova apenas solicitações abaixo de 10000
            if (solicitacao.getValue() <= 10000.0) {
                solicitacao.setApproved();
                Assertions.assertTrue(solicitacao.isApproved());
            } else {
                Assertions.assertFalse(solicitacao.isApproved());
            }
        }
        
        // Verifica quantas foram aprovadas
        long aprovadas = solicitacoesCliente1.stream()
                .filter(SolicitacaoCred::isApproved)
                .count();
        
        // Deve ter pelo menos uma aprovada e uma não aprovada (pela configuração dos dados de teste)
        Assertions.assertTrue(aprovadas > 0);
        Assertions.assertTrue(aprovadas < solicitacoesCliente1.size());
    }
    
    @Test
    public void testClienteAceitaApenasAlgumasSolicitacoes() {
        // Gerente analisa e aprova todas as solicitações do cliente 2
        for (SolicitacaoCred solicitacao : solicitacoesCliente2) {
            solicitacao.setanAlyzed();
            solicitacao.setApproved();
            Assertions.assertTrue(solicitacao.isAnalyzed());
            Assertions.assertTrue(solicitacao.isApproved());
        }
        
        // Cliente aceita apenas as solicitações abaixo de 6000
        for (SolicitacaoCred solicitacao : solicitacoesCliente2) {
            if (solicitacao.getValue() < 6000.0) {
                solicitacao.setAccepted();
                Assertions.assertTrue(solicitacao.isAccepted());
            } else {
                Assertions.assertFalse(solicitacao.isAccepted());
            }
        }
        
        // Verifica quantas foram aceitas
        long aceitas = solicitacoesCliente2.stream()
                .filter(SolicitacaoCred::isAccepted)
                .count();
        
        // Deve ter pelo menos uma aceita e uma não aceita (pela configuração dos dados de teste)
        Assertions.assertTrue(aceitas > 0);
        Assertions.assertTrue(aceitas < solicitacoesCliente2.size());
    }
    
    @Test
    public void testVerificacaoPropriedadesSolicitacao() {
        // Testa as propriedades de cada solicitação
        for (SolicitacaoCred solicitacao : solicitacoesCliente1) {
            Assertions.assertEquals(cliente1.getId(), solicitacao.getUserId());
            Assertions.assertTrue(solicitacao.getValue() > 0);
            Assertions.assertNotNull(solicitacao.getReason());
            Assertions.assertFalse(solicitacao.getReason().isEmpty());
        }
        
        // Verifica se todas as solicitações têm IDs diferentes
        List<Integer> ids = new ArrayList<>();
        for (SolicitacaoCred solicitacao : solicitacoesCliente1) {
            Assertions.assertFalse(ids.contains(solicitacao.getId()));
            ids.add(solicitacao.getId());
        }
        
        for (SolicitacaoCred solicitacao : solicitacoesCliente2) {
            Assertions.assertFalse(ids.contains(solicitacao.getId()));
            ids.add(solicitacao.getId());
        }
    }
} 