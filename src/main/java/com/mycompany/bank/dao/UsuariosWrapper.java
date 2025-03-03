package com.mycompany.bank.dao;

import com.mycompany.bank.model.Usuario;
import java.util.List;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Classe wrapper para serializar/deserializar lista de usuários em XML via JAXB.
 */
@XmlRootElement(name = "usuariosList")
public class UsuariosWrapper {

    private List<Usuario> usuarios;

    @XmlElement(name = "usuario")
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
