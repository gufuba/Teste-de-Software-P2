package br.edu.exemplo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarrinhoTest {

    // Requisito 1 — Carrinho vazio
    @Test
    void carrinhoRecemCriadoDeveTerValorTotalZero() {
        Carrinho carrinho = new Carrinho();

        assertEquals(0, carrinho.getValorTotal(), 0.001);
    }

    // Requisito 2 — Adicionar um produto
    @Test
    void deveAdicionarUmProdutoEAtualizarOTotal() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Teclado", 100.00));

        assertEquals(100.00, carrinho.getValorTotal(), 0.001);
    }

    // Requisito 3 — Adicionar vários produtos
    @Test
    void deveSomarOsPrecosDeVariosProdutos() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Teclado", 100.00));
        carrinho.adicionar(new Produto("Mouse", 50.00));

        assertEquals(150.00, carrinho.getValorTotal(), 0.001);
    }

    // Requisito 4 — Remover produto
    @Test
    void deveRemoverProdutoEAtualizarOTotal() {
        Carrinho carrinho = new Carrinho();
        Produto teclado = new Produto("Teclado", 100.00);
        Produto mouse = new Produto("Mouse", 50.00);
        carrinho.adicionar(teclado);
        carrinho.adicionar(mouse);

        carrinho.remover(mouse);

        assertEquals(100.00, carrinho.getValorTotal(), 0.001);
    }

    // Requisito 5 — Produto com preço inválido
    // Decisão: o carrinho REJEITA o produto lançando IllegalArgumentException
    // (falha explícita, em vez de ignorar a operação silenciosamente).
    @Test
    void naoDevePermitirAdicionarProdutoComPrecoNegativo() {
        Carrinho carrinho = new Carrinho();

        assertThrows(IllegalArgumentException.class,
                () -> carrinho.adicionar(new Produto("Teclado", -100.00)));
    }

    @Test
    void produtoRejeitadoPorPrecoNegativoNaoDeveAlterarOTotal() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Mouse", 50.00));

        assertThrows(IllegalArgumentException.class,
                () -> carrinho.adicionar(new Produto("Teclado", -100.00)));

        assertEquals(50.00, carrinho.getValorTotal(), 0.001);
    }

    // Requisito 6 — Desconto de 10% para compras >= R$ 500,00
    @Test
    void deveAplicarDescontoDeDezPorCentoParaCompraAcimaDoLimite() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Monitor", 600.00));

        assertEquals(540.00, carrinho.getValorTotal(), 0.001);
    }

    @Test
    void naoDeveAplicarDescontoParaSubtotalAbaixoDoLimite() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Monitor", 499.99));

        assertEquals(499.99, carrinho.getValorTotal(), 0.001);
    }

    @Test
    void deveAplicarDescontoParaSubtotalExatamenteNoLimite() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Monitor", 500.00));

        assertEquals(450.00, carrinho.getValorTotal(), 0.001);
    }

    @Test
    void deveAplicarDescontoParaSubtotalLogoAcimaDoLimite() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Monitor", 500.01));

        assertEquals(450.009, carrinho.getValorTotal(), 0.001);
    }

    // ------------------------------------------------------------------
    // Casos adicionais (bordas) escritos pelo aluno
    // ------------------------------------------------------------------

    @Test
    void devePermitirProdutoComPrecoZero() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Brinde", 0.00));

        assertEquals(0.00, carrinho.getValorTotal(), 0.001);
    }

    @Test
    void removerProdutoQueNaoEstaNoCarrinhoNaoDeveAlterarOTotal() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 100.00));

        carrinho.remover(new Produto("Mouse", 50.00));

        assertEquals(100.00, carrinho.getValorTotal(), 0.001);
    }

    @Test
    void removerTodosOsProdutosDeveZerarOTotal() {
        Carrinho carrinho = new Carrinho();
        Produto teclado = new Produto("Teclado", 100.00);
        carrinho.adicionar(teclado);

        carrinho.remover(teclado);

        assertEquals(0.00, carrinho.getValorTotal(), 0.001);
    }

    @Test
    void deveContarProdutoAdicionadoMaisDeUmaVez() {
        Carrinho carrinho = new Carrinho();
        Produto mouse = new Produto("Mouse", 50.00);

        carrinho.adicionar(mouse);
        carrinho.adicionar(mouse);

        assertEquals(100.00, carrinho.getValorTotal(), 0.001);
    }

    @Test
    void deveAplicarDescontoQuandoASomaDeVariosProdutosAtingeOLimite() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Teclado", 300.00));
        carrinho.adicionar(new Produto("Mouse", 200.00));

        assertEquals(450.00, carrinho.getValorTotal(), 0.001);
    }

    @Test
    void deveDeixarDeAplicarDescontoAoRemoverProdutoEFicarAbaixoDoLimite() {
        Carrinho carrinho = new Carrinho();
        Produto monitor = new Produto("Monitor", 460.00);
        Produto mouse = new Produto("Mouse", 40.00);
        carrinho.adicionar(monitor);
        carrinho.adicionar(mouse);
        assertEquals(450.00, carrinho.getValorTotal(), 0.001); // 500,00 - 10%

        carrinho.remover(mouse);

        assertEquals(460.00, carrinho.getValorTotal(), 0.001); // 460,00 sem desconto
    }

    @Test
    void subtotalNaoDeveConsiderarODesconto() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Monitor", 600.00));

        assertEquals(600.00, carrinho.getSubtotal(), 0.001);
    }
}
