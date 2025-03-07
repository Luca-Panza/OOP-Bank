package com.mycompany.bank.wrappers;

import com.mycompany.bank.model.Conta;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "contas")
public class ContaListWrapper {

    private List<Conta> contas;
    
    public ContaListWrapper() {
    }

    @XmlElement(name = "conta")
    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }
}
