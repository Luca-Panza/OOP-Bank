package com.mycompany.bank.wrappers;

import com.mycompany.bank.model.Usuario;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "usuarios")
public class UsuarioListWrapper {

    private List<Usuario> usuarios;
    
    public UsuarioListWrapper() {
    }

    @XmlElement(name = "usuario")
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
