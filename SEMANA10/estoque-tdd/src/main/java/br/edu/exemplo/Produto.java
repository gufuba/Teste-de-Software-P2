package br.edu.exemplo;

public class Produto {

    private String nome;
    private int quantidade;

    public Produto(String nome) {
        this.nome = nome;
        this.quantidade = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    /** Adiciona unidades ao estoque. Quantidades menores ou iguais a zero sao ignoradas. */
    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            return;
        }
        this.quantidade += quantidade;
    }

    /**
     * Retira unidades do estoque. A operacao e ignorada (estoque inalterado)
     * se a quantidade for menor ou igual a zero ou maior que o estoque disponivel.
     */
    public void retirarEstoque(int quantidade) {
        if (quantidade <= 0 || quantidade > this.quantidade) {
            return;
        }
        this.quantidade -= quantidade;
    }
}
