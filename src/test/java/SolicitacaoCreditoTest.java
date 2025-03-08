import com.mycompany.bank.model.SolicitacaoCred;
import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.model.Gerente;
import com.mycompany.bank.service.SistemaBancario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testes para solicitações de crédito
 */
public class SolicitacaoCreditoTest {
    
    private SistemaBancario sistema;
    private Cliente cliente;
    private Gerente gerente;
    private SolicitacaoCred solicitacao;
    
    @BeforeEach
    public void setUp() {
        sistema = new SistemaBancario();
        
        // Cria um cliente para testes
        cliente = new Cliente("Roberto Lima", "456.789.123-00", "senha789", sistema);
        sistema.adicionarUsuario(cliente);
        
        // Cria um gerente para analisar solicitações
        gerente = new Gerente("Mariana Costa", "321.654.987-00", "senhager", sistema);
        sistema.adicionarUsuario(gerente);
        
        // Cria uma solicitação de crédito
        solicitacao = new SolicitacaoCred(cliente.getId(), 5000.0, "Reforma residencial");
    }
    
    @Test
    public void testCriacaoSolicitacaoCredito() {
        // Verifica se a solicitação foi criada corretamente
        Assertions.assertEquals(cliente.getId(), solicitacao.getUserId());
        Assertions.assertEquals(5000.0, solicitacao.getValue(), 0.001);
        Assertions.assertEquals("Reforma residencial", solicitacao.getReason());
        
        // Inicialmente, não deve estar analisada nem aprovada
        Assertions.assertFalse(solicitacao.isAnalyzed());
        Assertions.assertFalse(solicitacao.isApproved());
        Assertions.assertFalse(solicitacao.isAccepted());
    }
    
    @Test
    public void testAnaliseAprovacaoCredito() {
        // Simula a análise da solicitação pelo gerente
        solicitacao.setanAlyzed();
        Assertions.assertTrue(solicitacao.isAnalyzed());
        
        // Simula a aprovação da solicitação
        solicitacao.setApproved();
        Assertions.assertTrue(solicitacao.isApproved());
        
        // Simula a aceitação da solicitação pelo cliente
        solicitacao.setAccepted();
        Assertions.assertTrue(solicitacao.isAccepted());
    }
    
    @Test
    public void testIdUnico() {
        // Cria várias solicitações e verifica se têm IDs diferentes
        SolicitacaoCred solicitacao2 = new SolicitacaoCred(cliente.getId(), 10000.0, "Compra de veículo");
        SolicitacaoCred solicitacao3 = new SolicitacaoCred(cliente.getId(), 3000.0, "Viagem");
        
        Assertions.assertNotEquals(solicitacao.getId(), solicitacao2.getId());
        Assertions.assertNotEquals(solicitacao.getId(), solicitacao3.getId());
        Assertions.assertNotEquals(solicitacao2.getId(), solicitacao3.getId());
    }
    
    @Test
    public void testFluxoCompleto() {
        // Simula um fluxo completo de solicitação, aprovação e aceitação
        
        // 1. Cliente solicita o crédito (já feito no setUp)
        Assertions.assertFalse(solicitacao.isAnalyzed());
        
        // 2. Gerente analisa e aprova
        solicitacao.setanAlyzed();
        solicitacao.setApproved();
        
        Assertions.assertTrue(solicitacao.isAnalyzed());
        Assertions.assertTrue(solicitacao.isApproved());
        
        // 3. Cliente aceita o crédito
        solicitacao.setAccepted();
        Assertions.assertTrue(solicitacao.isAccepted());
    }
    
    @Test
    public void testMultiplasSolicitacoesCliente() {
        // Teste com múltiplas solicitações para o mesmo cliente
        SolicitacaoCred solicitacao2 = new SolicitacaoCred(cliente.getId(), 8000.0, "Compra de equipamentos");
        SolicitacaoCred solicitacao3 = new SolicitacaoCred(cliente.getId(), 15000.0, "Investimento em negócio");
        
        // Todas as solicitações devem ter o mesmo ID de cliente
        Assertions.assertEquals(cliente.getId(), solicitacao.getUserId());
        Assertions.assertEquals(cliente.getId(), solicitacao2.getUserId());
        Assertions.assertEquals(cliente.getId(), solicitacao3.getUserId());
        
        // Mas valores e motivos diferentes
        Assertions.assertEquals(5000.0, solicitacao.getValue(), 0.001);
        Assertions.assertEquals(8000.0, solicitacao2.getValue(), 0.001);
        Assertions.assertEquals(15000.0, solicitacao3.getValue(), 0.001);
        
        Assertions.assertEquals("Reforma residencial", solicitacao.getReason());
        Assertions.assertEquals("Compra de equipamentos", solicitacao2.getReason());
        Assertions.assertEquals("Investimento em negócio", solicitacao3.getReason());
    }
} 