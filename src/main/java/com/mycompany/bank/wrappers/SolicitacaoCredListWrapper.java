package com.mycompany.bank.wrappers;

import com.mycompany.bank.model.SolicitacaoCred;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "solicitacaoCreds")
public class SolicitacaoCredListWrapper {

    private List<SolicitacaoCred> solicitacaoCreds;
    
    public SolicitacaoCredListWrapper() {
    }

    @XmlElement(name = "solicitacaoCred")
    public List<SolicitacaoCred> getSolicitacaoCreds() {
        return solicitacaoCreds;
    }

    public void setSolicitacaoCreds(List<SolicitacaoCred> solicitacaoCreds) {
        this.solicitacaoCreds = solicitacaoCreds;
    }
}
