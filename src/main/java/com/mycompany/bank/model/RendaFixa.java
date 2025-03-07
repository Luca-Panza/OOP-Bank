package com.mycompany.bank.model;
import com.google.gson.annotations.Expose;

/**
 * Investimento do tipo Renda Fixa.
 */
public class RendaFixa extends Investimento {

    @Expose
    private final String tipo = "Renda Fixa";

    @Expose
    private double taxaRendimento;
    @Expose
    private int prazoMinimo;
    @Expose
    private int prazoMaximo;

    public RendaFixa(String nome, String descricao, double valorMinimo,
                     double taxaRendimento, int prazoMinimo, int prazoMaximo) {
        super(nome, descricao, valorMinimo);
        this.taxaRendimento = taxaRendimento;
        this.prazoMinimo = prazoMinimo;
        this.prazoMaximo = prazoMaximo;
    }

    @Override
    public void aplicar(double valor) {
        if (valor < getValorMinimo()) {
            throw new IllegalArgumentException("Valor abaixo do mínimo para este investimento!");
        }
    }

    @Override
    public void resgatar(double valor) {
        System.out.println("Resgatando R$ " + valor + " de " + getNome() + " (RendaFixa).");
    }

    public double getTaxaRendimento() {
        return taxaRendimento;
    }

    public int getPrazoMinimo() {
        return prazoMinimo;
    }

    public int getPrazoMaximo() {
        return prazoMaximo;
    }
}
