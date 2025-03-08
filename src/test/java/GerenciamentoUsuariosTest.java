import com.mycompany.bank.model.Cliente;
import com.mycompany.bank.model.Caixa;
import com.mycompany.bank.model.Gerente;
import com.mycompany.bank.model.Usuario;
import com.mycompany.bank.service.SistemaBancario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * Testes para o gerenciamento de usuários (Cliente, Caixa e Gerente)
 */
public class GerenciamentoUsuariosTest {
    
    private SistemaBancario sistema;
    
    @BeforeEach
    public void setUp() {
        // Remover arquivos de dados existentes para garantir um ambiente limpo
        File usuariosFile = new File("./files/usuarios.json");
        if (usuariosFile.exists()) {
            usuariosFile.delete();
        }
        
        File usuariosXmlFile = new File("./files/usuarios.xml");
        if (usuariosXmlFile.exists()) {
            usuariosXmlFile.delete();
        }
        
        sistema = new SistemaBancario();
    }
    
    @Test
    public void testCriacaoCliente() {
        // Cria um cliente
        Cliente cliente = new Cliente("João Silva", "123.456.789-00", "senha123", sistema);
        sistema.adicionarUsuario(cliente);
        
        // Verifica se o cliente foi adicionado corretamente
        Usuario usuarioEncontrado = sistema.buscarUsuarioPorCpf("123.456.789-00");
        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertTrue(usuarioEncontrado instanceof Cliente);
        Assertions.assertEquals("João Silva", usuarioEncontrado.getNome());
    }
    
    @Test
    public void testCriacaoCaixa() {
        // Cria um caixa
        Caixa caixa = new Caixa("Maria Oliveira", "987.654.321-00", "senha456", sistema);
        sistema.adicionarUsuario(caixa);
        
        // Verifica se o caixa foi adicionado corretamente
        Usuario usuarioEncontrado = sistema.buscarUsuarioPorCpf("987.654.321-00");
        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertTrue(usuarioEncontrado instanceof Caixa);
        Assertions.assertEquals("Maria Oliveira", usuarioEncontrado.getNome());
    }
    
    @Test
    public void testCriacaoGerente() {
        // Cria um gerente
        Gerente gerente = new Gerente("Carlos Santos", "456.789.123-00", "senha789", sistema);
        sistema.adicionarUsuario(gerente);
        
        // Verifica se o gerente foi adicionado corretamente
        Usuario usuarioEncontrado = sistema.buscarUsuarioPorCpf("456.789.123-00");
        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertTrue(usuarioEncontrado instanceof Gerente);
        Assertions.assertEquals("Carlos Santos", usuarioEncontrado.getNome());
    }
    
    @Test
    public void testRemocaoUsuario() {
        // Cria um cliente
        Cliente cliente = new Cliente("Ana Pereira", "111.222.333-44", "senha123", sistema);
        sistema.adicionarUsuario(cliente);
        
        // Verifica se o cliente foi adicionado
        Assertions.assertNotNull(sistema.buscarUsuarioPorCpf("111.222.333-44"));
        
        // Remove o cliente
        sistema.removerUsuario("111.222.333-44");
        
        // Verifica se o cliente foi removido
        Assertions.assertNull(sistema.buscarUsuarioPorCpf("111.222.333-44"));
    }
    
    @Test
    public void testAtualizacaoUsuario() {
        // Cria um cliente
        Cliente cliente = new Cliente("Pedro Costa", "555.666.777-88", "senha123", sistema);
        sistema.adicionarUsuario(cliente);
        
        // Atualiza os dados do cliente
        cliente.setNome("Pedro Costa Silva");
        cliente.setSenha("novaSenha456");
        sistema.atualizarUsuario(cliente);
        
        // Verifica se os dados foram atualizados
        Usuario usuarioAtualizado = sistema.buscarUsuarioPorCpf("555.666.777-88");
        Assertions.assertEquals("Pedro Costa Silva", usuarioAtualizado.getNome());
        Assertions.assertEquals("novaSenha456", usuarioAtualizado.getSenha());
    }
    
    @Test
    public void testListagemUsuariosPorTipo() {
        // Limpa a lista de usuários para garantir que só temos os que adicionamos
        sistema = new SistemaBancario();
        
        // Cria vários usuários de diferentes tipos
        sistema.adicionarUsuario(new Cliente("Cliente 1", "111.111.111-11", "senha1", sistema));
        sistema.adicionarUsuario(new Cliente("Cliente 2", "222.222.222-22", "senha2", sistema));
        sistema.adicionarUsuario(new Caixa("Caixa 1", "333.333.333-33", "senha3", sistema));
        sistema.adicionarUsuario(new Gerente("Gerente 1", "444.444.444-44", "senha4", sistema));
        sistema.adicionarUsuario(new Gerente("Gerente 2", "555.555.555-55", "senha5", sistema));
        
        // Verifica a listagem de clientes
        Assertions.assertEquals(2, sistema.listarClientes().size());
        
        // Verifica a listagem de caixas
        Assertions.assertEquals(1, sistema.listarCaixas().size());
        
        // Verifica a listagem de gerentes
        Assertions.assertEquals(2, sistema.listarGerentes().size());
    }
    
    @Test
    public void testBuscaUsuarioPorId() {
        // Cria um cliente
        Cliente cliente = new Cliente("Juliana Lima", "999.888.777-66", "senha123", sistema);
        sistema.adicionarUsuario(cliente);
        
        // Busca o cliente pelo ID
        Usuario usuarioEncontrado = sistema.buscarUsuarioPorId(cliente.getId());
        
        // Verifica se o cliente foi encontrado corretamente
        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertEquals(cliente.getId(), usuarioEncontrado.getId());
        Assertions.assertEquals("Juliana Lima", usuarioEncontrado.getNome());
    }
    
    @Test
    public void testAutenticacaoUsuario() {
        // Cria um cliente
        Cliente cliente = new Cliente("Roberto Alves", "777.888.999-00", "senha123", sistema);
        sistema.adicionarUsuario(cliente);
        
        // Tenta fazer login com credenciais corretas
        Usuario usuarioLogado = sistema.login("777.888.999-00", "senha123");
        Assertions.assertNotNull(usuarioLogado);
        Assertions.assertEquals(cliente.getId(), usuarioLogado.getId());
        
        // Tenta fazer login com senha incorreta
        Usuario loginInvalido = sistema.login("777.888.999-00", "senhaErrada");
        Assertions.assertNull(loginInvalido);
        
        // Tenta fazer login com CPF inexistente
        Usuario loginCpfInexistente = sistema.login("000.000.000-00", "senha123");
        Assertions.assertNull(loginCpfInexistente);
    }
} 