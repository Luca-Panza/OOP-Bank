import com.mycompany.bank.model.Conta;
import com.mycompany.bank.exceptions.SaldoInsuficienteException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testes mínimos para a classe Conta (necessita JUnit no classpath).
 */
public class ContaTest {

    @Test
    public void testDeposito() {
        Conta conta = new Conta("0001", 1000.0);
        conta.depositar(500.0);
        Assert.assertEquals(1500.0, conta.getSaldo(), 0.001);
    }

    @Test
    public void testSaque() throws SaldoInsuficienteException {
        Conta conta = new Conta("0001", 1000.0);
        conta.sacar(200.0);
        Assert.assertEquals(800.0, conta.getSaldo(), 0.001);
    }

    @Test(expected = SaldoInsuficienteException.class)
    public void testSaqueSaldoInsuficiente() throws SaldoInsuficienteException {
        Conta conta = new Conta("0001", 100.0);
        conta.sacar(200.0);  // deve lançar SaldoInsuficienteException
    }
}
