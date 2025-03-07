package com.mycompany.bank.model;

/**
 * Investimento do tipo Renda Fixa.
 */
public class RendaFixa extends Investimento {

    private double taxaRendimento;
    private int prazoMinimo;
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
