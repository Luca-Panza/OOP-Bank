package com.mycompany.bank.model;

public class RendaVariavel extends Investimento {

    private double percentualRisco;
    private double rentabilidadeEsperada;

    public RendaVariavel(String nome, String descricao, double valorMinimo,
                         double percentualRisco, double rentabilidadeEsperada) {
        super(nome, descricao, valorMinimo);
        this.percentualRisco = percentualRisco;
        this.rentabilidadeEsperada = rentabilidadeEsperada;
    }

    @Override
    public void aplicar(double valor) {
        if (valor < getValorMinimo()) {
            throw new IllegalArgumentException("Valor abaixo do mínimo para este investimento!");
        }
        System.out.println("Aplicando R$" + valor + " em " + getNome() + " (RendaVariável).");
        // Lógica fictícia adicional...
    }

    @Override
    public void resgatar(double valor) {
        System.out.println("Resgatando R$" + valor + " de " + getNome() + " (RendaVariável).");
        // Lógica fictícia adicional...
    }

    // Getters / Setters
    public double getPercentualRisco() {
        return percentualRisco;
    }

    public double getRentabilidadeEsperada() {
        return rentabilidadeEsperada;
    }
}
