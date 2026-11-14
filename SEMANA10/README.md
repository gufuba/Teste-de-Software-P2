# Semana 10 — TDD (Test-Driven Development)

Quatro projetos Maven (Java 17 + JUnit 5) desenvolvidos com o ciclo
**RED → GREEN → REFACTOR**, com cobertura de testes medida pelo **JaCoCo**.

| Projeto | O que faz | Testes | Cobertura (linhas / branches) |
|---|---|---|---|
| [`bubblesort-tdd`](bubblesort-tdd) | Ordenação de vetor de inteiros (Bubble Sort) | 8 | 100 % / 100 % |
| [`carrinho-compras-tdd`](carrinho-compras-tdd) | Carrinho: total, adicionar, remover, preço inválido, desconto de 10 % ≥ R$ 500 | 18 | 100 % / 100 % |
| [`conta-bancaria-tdd-maven`](conta-bancaria-tdd-maven) | Depósito e saque com validações | 11 | 100 % / 100 % |
| [`estoque-tdd`](estoque-tdd) | Entrada e retirada de estoque com validações | 13 | 100 % / 100 % |

## Como executar

Dentro de cada projeto:

```bash
mvn test      # roda os testes
mvn verify    # roda os testes + falha o build se a cobertura < 100 % (linhas ou branches)
```

O relatório de cobertura fica em `target/site/jacoco/index.html`.

## Decisões de projeto

- **Carrinho, preço negativo** (requisito 5): `adicionar` lança `IllegalArgumentException`
  e o carrinho permanece inalterado (falha explícita em vez de ignorar em silêncio).
- **Conta e Estoque**: operações inválidas (valor/quantidade `<= 0`, saque/retirada maior
  que o saldo/estoque disponível) são **ignoradas** e não alteram o estado, conforme os
  testes fornecidos pelo professor.
- Os testes fornecidos pelo professor (bubblesort, conta, estoque) **não foram alterados**;
  os casos de borda adicionais ficam em uma seção separada ao final de cada classe de teste.
- No carrinho, os testes foram escritos requisito a requisito, vendo cada teste falhar (RED)
  antes da implementação.
