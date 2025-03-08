import com.mycompany.bank.model.Conta;
import com.mycompany.bank.exceptions.SaldoInsuficienteException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Testes mínimos para a classe Conta (necessita JUnit no classpath).
 */
public class ContaTest {

    @Test
    public void testDeposito() {
        Conta conta = new Conta("0001", 1000.0, 1);
        conta.depositar(500.0);
        Assertions.assertEquals(1500.0, conta.getSaldo(), 0.001);
    }

    @Test
    public void testSaque() throws SaldoInsuficienteException {
        Conta conta = new Conta("0001", 1000.0, 1);
        conta.sacar(200.0);
        Assertions.assertEquals(800.0, conta.getSaldo(), 0.001);
    }

    @Test
    public void testSaqueSaldoInsuficiente() {
        Conta conta = new Conta("0001", 100.0, 1);
        Assertions.assertThrows(SaldoInsuficienteException.class, () -> {
            conta.sacar(200.0);  // deve lançar SaldoInsuficienteException
        });
    }
    
    @Test
    public void testTransferencia() throws SaldoInsuficienteException {
        Conta contaOrigem = new Conta("0001", 1000.0, 1);
        Conta contaDestino = new Conta("0002", 500.0, 2);
        
        contaOrigem.transferir(contaDestino, 300.0);
        
        Assertions.assertEquals(700.0, contaOrigem.getSaldo(), 0.001);
        Assertions.assertEquals(800.0, contaDestino.getSaldo(), 0.001);
    }
    
    @Test
    public void testTransferenciaSaldoInsuficiente() {
        Conta contaOrigem = new Conta("0001", 100.0, 1);
        Conta contaDestino = new Conta("0002", 500.0, 2);
        
        Assertions.assertThrows(SaldoInsuficienteException.class, () -> {
            contaOrigem.transferir(contaDestino, 200.0);
        });
    }
}
