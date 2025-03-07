import com.mycompany.bank.model.Conta;
import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.service.SistemaBancario;
import com.mycompany.bank.exceptions.SaldoInsuficienteException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testes para operações financeiras básicas (saque, depósito e transferência)
 */
public class OperacoesFinanceirasTest {
    
    private SistemaBancario sistema;
    private Cliente cliente1;
    private Cliente cliente2;
    private Conta conta1;
    private Conta conta2;
    
    @Before
    public void setUp() {
        sistema = new SistemaBancario();
        
        // Cria dois clientes para testes
        cliente1 = new Cliente("Ana Silva", "123.456.789-00", "senha123", sistema);
        cliente2 = new Cliente("Carlos Santos", "987.654.321-00", "senha456", sistema);
        
        // Adiciona clientes ao sistema
        sistema.cadastrarUsuario(cliente1);
        sistema.cadastrarUsuario(cliente2);
        
        // Cria contas para os clientes
        conta1 = new Conta("1001", 1000.0, cliente1.getId());
        conta2 = new Conta("2001", 500.0, cliente2.getId());
        
        // Vincula as contas aos clientes no sistema
        sistema.adicionarConta(conta1);
        sistema.adicionarConta(conta2);
    }
    
    @Test
    public void testDepositoValido() {
        // Testa um depósito válido
        conta1.depositar(500.0);
        Assert.assertEquals(1500.0, conta1.getSaldo(), 0.001);
        
        // Verifica se o histórico foi atualizado
        Assert.assertEquals(1, conta1.getHistorico().size());
        Assert.assertEquals(500.0, conta1.getHistorico().get(0), 0.001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDepositoInvalido() {
        // Tenta fazer um depósito com valor negativo
        conta1.depositar(-100.0);
    }
    
    @Test
    public void testSaqueValido() throws SaldoInsuficienteException {
        // Realiza um saque válido
        conta1.sacar(300.0);
        Assert.assertEquals(700.0, conta1.getSaldo(), 0.001);
        
        // Verifica se o histórico foi atualizado
        Assert.assertEquals(1, conta1.getHistorico().size());
        Assert.assertEquals(-300.0, conta1.getHistorico().get(0), 0.001);
    }
    
    @Test(expected = SaldoInsuficienteException.class)
    public void testSaqueComSaldoInsuficiente() throws SaldoInsuficienteException {
        // Tenta sacar mais do que o saldo disponível
        conta1.sacar(1500.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSaqueComValorNegativo() throws SaldoInsuficienteException {
        // Tenta fazer um saque com valor negativo
        conta1.sacar(-200.0);
    }
    
    @Test
    public void testTransferenciaValida() throws SaldoInsuficienteException {
        // Realiza uma transferência válida
        conta1.transferir(conta2, 300.0);
        
        // Verifica os saldos após a transferência
        Assert.assertEquals(700.0, conta1.getSaldo(), 0.001);
        Assert.assertEquals(800.0, conta2.getSaldo(), 0.001);
        
        // Verifica se o histórico da conta de origem foi atualizado
        Assert.assertEquals(1, conta1.getHistorico().size());
        Assert.assertEquals(-300.0, conta1.getHistorico().get(0), 0.001);
        
        // Verifica se o histórico da conta de destino foi atualizado
        Assert.assertEquals(1, conta2.getHistorico().size());
        Assert.assertEquals(300.0, conta2.getHistorico().get(0), 0.001);
    }
    
    @Test(expected = SaldoInsuficienteException.class)
    public void testTransferenciaComSaldoInsuficiente() throws SaldoInsuficienteException {
        // Tenta transferir mais do que o saldo disponível
        conta1.transferir(conta2, 1500.0);
    }
    
    @Test
    public void testConsultaSaldo() {
        // Verifica se o saldo inicial está correto
        double saldo = cliente1.consultarSaldo("1001");
        Assert.assertEquals(1000.0, saldo, 0.001);
    }
    
    @Test
    public void testPertenceConta() {
        // Verifica se a conta pertence ao cliente
        Assert.assertTrue(cliente1.pertenceConta("1001"));
        Assert.assertFalse(cliente1.pertenceConta("2001"));
    }
} 