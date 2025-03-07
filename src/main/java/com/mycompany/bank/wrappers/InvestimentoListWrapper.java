package com.mycompany.bank.wrappers;

import com.mycompany.bank.model.Investimento;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "investimentos")
public class InvestimentoListWrapper {

    private List<Investimento> investimentos;
    
    public InvestimentoListWrapper() {
    }

    @XmlElement(name = "investimento")
    public List<Investimento> getInvestimentos() {
        return investimentos;
    }

    public void setInvestimentos(List<Investimento> investimentos) {
        this.investimentos = investimentos;
    }
}
