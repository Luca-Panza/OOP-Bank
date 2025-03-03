package com.mycompany.bank.dao;

import com.mycompany.bank.model.Usuario;
import java.util.List;

/**
 * Exemplo de DAO para Usuário.
 * Aqui você poderia ter métodos para salvar e carregar (JSON/XML).
 */
public class UsuarioDAO {

    public void salvarEmJson(String caminho, List<Usuario> usuarios) {
        // Exemplo usando GSON:
        // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // try (FileWriter writer = new FileWriter(caminho)) {
        //     gson.toJson(usuarios, writer);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    public List<Usuario> carregarDeJson(String caminho) {
        // Exemplo usando GSON
        // ...
        return null;
    }

    public void salvarEmXml(String caminho, List<Usuario> usuarios) {
        // Exemplo usando JAXB
        // ...
    }

    public List<Usuario> carregarDeXml(String caminho) {
        // ...
        return null;
    }
}
