import com.mycompany.bank.model.RendaFixa;
import com.mycompany.bank.model.RendaVariavel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testes para as classes de investimento (Renda Fixa e Variável)
 */
public class InvestimentoTest {
    
    private RendaFixa rendaFixa;
    private RendaVariavel rendaVariavel;
    
    @Before
    public void setUp() {
        // Configurando uma renda fixa com taxa de 5%, prazo mínimo de 30 dias e máximo de 365 dias
        rendaFixa = new RendaFixa("CDB Banco XYZ", "CDB com rentabilidade prefixada", 
                500.0, 0.05, 30, 365);
        
        // Configurando uma renda variável com 15% de risco e rentabilidade esperada de 12%
        rendaVariavel = new RendaVariavel("Ações XPTO", "Ações da empresa XPTO",
                100.0, 0.15, 0.12);
    }
    
    @Test
    public void testAplicacaoRendaFixaComValorValido() {
        // Teste de aplicação com valor válido
        rendaFixa.aplicar(1000.0);
        // Se não lançou exceção, o teste passou
        Assert.assertTrue(true);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAplicacaoRendaFixaComValorAbaixoDoMinimo() {
        // Deve lançar exceção ao tentar aplicar menos que o valor mínimo
        rendaFixa.aplicar(100.0);
    }
    
    @Test
    public void testPropriedadesRendaFixa() {
        Assert.assertEquals("CDB Banco XYZ", rendaFixa.getNome());
        Assert.assertEquals("CDB com rentabilidade prefixada", rendaFixa.getDescricao());
        Assert.assertEquals(500.0, rendaFixa.getValorMinimo(), 0.001);
        Assert.assertEquals(0.05, rendaFixa.getTaxaRendimento(), 0.001);
        Assert.assertEquals(30, rendaFixa.getPrazoMinimo());
        Assert.assertEquals(365, rendaFixa.getPrazoMaximo());
    }
    
    @Test
    public void testAplicacaoRendaVariavelComValorValido() {
        // Teste de aplicação com valor válido
        rendaVariavel.aplicar(500.0);
        // Se não lançou exceção, o teste passou
        Assert.assertTrue(true);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAplicacaoRendaVariavelComValorAbaixoDoMinimo() {
        // Deve lançar exceção ao tentar aplicar menos que o valor mínimo
        rendaVariavel.aplicar(50.0);
    }
    
    @Test
    public void testPropriedadesRendaVariavel() {
        Assert.assertEquals("Ações XPTO", rendaVariavel.getNome());
        Assert.assertEquals("Ações da empresa XPTO", rendaVariavel.getDescricao());
        Assert.assertEquals(100.0, rendaVariavel.getValorMinimo(), 0.001);
        Assert.assertEquals(0.15, rendaVariavel.getPercentualRisco(), 0.001);
        Assert.assertEquals(0.12, rendaVariavel.getRentabilidadeEsperada(), 0.001);
    }
    
    @Test
    public void testResgateRendaFixa() {
        rendaFixa.resgatar(300.0);
        // Verifica que o método executa sem problemas
        Assert.assertTrue(true);
    }
    
    @Test
    public void testResgateRendaVariavel() {
        rendaVariavel.resgatar(50.0);
        // Verifica que o método executa sem problemas
        Assert.assertTrue(true);
    }
} 