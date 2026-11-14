# Semana 11 — Testes de integração com Spring Boot e JUnit 5

Projeto-base fornecido (`Controller → Service → Repository → H2`) com os testes de
integração completados.

**Tecnologias:** Java 17, Maven, Spring Boot 3.3.13, Spring Data JPA, H2 (em memória),
JUnit 5 e MockMvc. **Nenhum mock** foi usado: `ProdutoRepository`, `ProdutoService`
e o banco H2 são os componentes reais.

## Como executar

```bash
mvn test
```

## Resultado da execução

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0 -- ProdutoControllerIntegrationTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0 -- ProdutoServiceIntegrationTest
Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Antes de completar os `TODO`, os 5 métodos de teste "passavam" (5 testes, 0 falhas) **sem
verificar nada**, porque estavam vazios. Esse resultado verde não era evidência de nada.

## Testes executados

### Exercício 1 — Service + Repository + H2 (`ProdutoServiceIntegrationTest`)

| Caso | Teste | O que verifica |
|---|---|---|
| CT01 | `deveCadastrarERecuperarProduto` | Cadastra `Teclado` (150.0); o produto recebe ID e, ao ser relido do banco com `findById`, tem nome e preço corretos. |
| CT02 | `naoDevePersistirProdutoComNomeEmBranco` | Nome em branco lança `IllegalArgumentException` e `repository.count()` não muda. |
| CT03 (desafio) | `deveAtualizarPrecoPersistido` | Cadastra `Mouse` (80.0), atualiza para 95.0 e a releitura do banco retorna 95.0. |
| extra | `naoDevePersistirProdutoComNomeNulo` | Nome `null` é rejeitado e nada é gravado. |
| extra | `naoDevePersistirProdutoComPrecoNaoPositivo` | Preço `0` e `-10` são rejeitados e nada é gravado. |
| extra | `naoDeveAtualizarParaPrecoInvalidoENemAlterarOPrecoPersistido` | Atualização inválida lança exceção e o preço persistido continua 80.0. |
| extra | `deveFalharAoBuscarOuAtualizarProdutoInexistente` | ID inexistente lança `IllegalArgumentException`. |

### Exercício 2 — HTTP com MockMvc (`ProdutoControllerIntegrationTest`)

| Caso | Teste | O que verifica |
|---|---|---|
| CT04 | `deveCadastrarProdutoPelaApi` | `POST /produtos` com `{"nome":"Mouse","preco":80.0}` → HTTP 200, `id` numérico, `nome` = `Mouse`, `preco` = `80.0`. |
| CT05 | `deveBuscarProdutoPelaApi` | `POST` para cadastrar, extrai o `id` da resposta com `ObjectMapper`/`JsonNode`, `GET /produtos/{id}` → HTTP 200 com os mesmos dados. |
| extra | `deveRejeitarCadastroComNomeEmBrancoPelaApi` | Nome vazio: a `IllegalArgumentException("Nome obrigatório")` do Service chega ao MockMvc. |
| extra | `deveRejeitarCadastroComPrecoNegativoPelaApi` | Preço negativo é rejeitado pela API. |
| extra | `deveFalharAoBuscarProdutoInexistentePelaApi` | `GET` de ID inexistente falha com "Produto não encontrado". |

> **Observação:** a aplicação não tem `@ControllerAdvice`. Por isso, uma
> `IllegalArgumentException` lançada pelo Service não vira HTTP 400/404: com MockMvc ela
> sai de `perform(...)` embrulhada em `ServletException`, e é isso que os testes extras
> verificam. Em um servidor real, o resultado seria HTTP 500. Tratar isso é uma melhoria
> possível, mas fora do escopo desta atividade (o código de produção não foi alterado).

## Evidência de que os testes detectam defeitos

Para garantir que as asserções não passam "no vazio", foram injetados dois defeitos
temporários no `ProdutoService` e a suíte foi executada (o código original foi restaurado):

| Defeito injetado | Resultado |
|---|---|
| `cadastrar` grava `preco + 1` | **4 testes falharam** (CT01, CT04, CT05 e o extra de atualização inválida), por exemplo `expected: <150.0> but was: <151.0>`. |
| `atualizarPreco` deixa de alterar o preço | **1 teste falhou**: CT03 `expected: <95.0> but was: <80.0>`. |

## Discussão

**Por que `repository.findById()` é uma evidência diferente de olhar só o objeto retornado pelo serviço?**
O objeto devolvido por `service.cadastrar(...)` é o objeto Java que acabamos de criar, então
ele só mostra o que o *código* acha que fez. Consultar o `repository` mostra o que ficou
*registrado na camada de persistência*. Há um detalhe: dentro de um teste `@Transactional`,
o JPA mantém um cache de 1º nível (o `EntityManager`) e `findById` pode devolver o **mesmo
objeto** já em memória, sem ir ao banco. Por isso CT01 e CT03 chamam `entityManager.flush()`
(envia os `INSERT`/`UPDATE` ao H2) e `entityManager.clear()` (esvazia o cache) antes do
`findById`: assim o `SELECT` é executado de verdade e o dado lido é o que está no H2.

**O que `@Transactional` faz ao término de cada teste?**
O Spring Test abre uma transação antes de cada método de teste e, ao final, faz **rollback**
(não `commit`). Todo `INSERT`/`UPDATE` feito pelo teste é desfeito, e o banco volta ao estado
anterior. É isso que **isola** os testes entre si: o H2 em memória e o contexto Spring são
reaproveitados por toda a suíte, mas um teste não deixa registros para o seguinte, e a ordem
de execução não importa. (A sequência de IDs `IDENTITY` não volta ao início no rollback;
por isso os testes nunca assumem um ID fixo, e usam `999999` como ID inexistente.)

**Quais componentes são efetivamente exercitados no Exercício 2?**
Todo o fluxo: roteamento do Spring MVC (`DispatcherServlet`), desserialização do JSON para
`ProdutoRequest`, `ProdutoController`, `ProdutoService` (regras de validação),
`ProdutoRepository` (Spring Data JPA), Hibernate e o banco H2, e a serialização do
`Produto` de volta para JSON.

**Por que o MockMvc não precisa de um servidor HTTP real?**
O MockMvc chama o `DispatcherServlet` diretamente, dentro da mesma JVM, usando objetos
simulados de requisição e resposta (`MockHttpServletRequest/Response`). Não há socket,
porta nem Tomcat; o que se simula é só a camada de transporte HTTP, enquanto toda a
aplicação Spring (contexto, beans, JPA, banco) é real.

**Qual a diferença para um teste unitário do controller com dependências simuladas?**
Um teste unitário do controller substitui o `ProdutoService` por um mock e verifica apenas
que o controller delega e converte dados corretamente; ele pode passar mesmo que o Service
ou o banco estejam quebrados. O teste de integração aqui usa Service, Repository e H2
reais, então verifica que as camadas **funcionam juntas** (mapeamento JSON, regras de
negócio e persistência). Em contrapartida, ele é mais lento (sobe o contexto Spring, ~5 s)
e, quando falha, aponta o fluxo e não diretamente a classe culpada.

## Arquivos relevantes

- `src/test/java/br/edu/exemplo/ProdutoServiceIntegrationTest.java` — Exercício 1
- `src/test/java/br/edu/exemplo/ProdutoControllerIntegrationTest.java` — Exercício 2
- Código de produção (`src/main`): **inalterado**, conforme o projeto-base.
