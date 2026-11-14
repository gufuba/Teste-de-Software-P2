package br.edu.exemplo;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    /** Subtotal a partir do qual a compra recebe desconto. */
    static final double LIMITE_PARA_DESCONTO = 500.00;

    /** Percentual de desconto aplicado (10%). */
    static final double PERCENTUAL_DESCONTO = 0.10;

    private final List<Produto> produtos = new ArrayList<>();

    /**
     * Adiciona um produto ao carrinho.
     *
     * @throws IllegalArgumentException se o preco do produto for negativo
     */
    public void adicionar(Produto produto) {
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo");
        }
        produtos.add(produto);
    }

    /** Remove uma ocorrencia do produto. Se ele nao estiver no carrinho, nada acontece. */
    public void remover(Produto produto) {
        produtos.remove(produto);
    }

    /** Soma dos precos dos produtos, sem desconto. */
    public double getSubtotal() {
        double subtotal = 0;
        for (Produto produto : produtos) {
            subtotal += produto.getPreco();
        }
        return subtotal;
    }

    /** Valor a pagar: subtotal, com 10% de desconto se o subtotal for >= R$ 500,00. */
    public double getValorTotal() {
        double subtotal = getSubtotal();
        if (subtotal >= LIMITE_PARA_DESCONTO) {
            return subtotal * (1 - PERCENTUAL_DESCONTO);
        }
        return subtotal;
    }
}
