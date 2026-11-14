package br.edu.exemplo;
import br.edu.exemplo.model.Produto;
import br.edu.exemplo.repository.ProdutoRepository;
import br.edu.exemplo.service.ProdutoService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest @Transactional
class ProdutoServiceIntegrationTest {
 @Autowired ProdutoService service;
 @Autowired ProdutoRepository repository;
 @Autowired EntityManager entityManager;

 /**
  * Envia as alteracoes pendentes ao H2 (flush) e esvazia o cache de 1o nivel
  * do JPA (clear). Assim, o proximo findById() executa um SELECT de verdade em
  * vez de devolver o mesmo objeto que ja estava em memoria.
  */
 private void sincronizarComBanco(){
  entityManager.flush();
  entityManager.clear();
 }

 // CT01 — Cadastro valido
 @Test void deveCadastrarERecuperarProduto(){
  Produto salvo = service.cadastrar("Teclado", 150.0);

  assertNotNull(salvo.getId(), "o produto deve receber um ID ao ser persistido");
  sincronizarComBanco();

  Produto doBanco = repository.findById(salvo.getId()).orElseThrow();
  assertEquals("Teclado", doBanco.getNome());
  assertEquals(150.0, doBanco.getPreco(), 0.001);
 }

 // CT02 — Nome invalido
 @Test void naoDevePersistirProdutoComNomeEmBranco(){
  long antes = repository.count();

  assertThrows(IllegalArgumentException.class, () -> service.cadastrar("   ", 80.0));

  assertEquals(antes, repository.count(), "nenhum registro deve ser criado");
 }

 // CT03 — Atualizacao (desafio)
 @Test void deveAtualizarPrecoPersistido(){
  Produto mouse = service.cadastrar("Mouse", 80.0);
  Long id = mouse.getId();

  service.atualizarPreco(id, 95.0);
  sincronizarComBanco();

  Produto doBanco = repository.findById(id).orElseThrow();
  assertEquals(95.0, doBanco.getPreco(), 0.001);
  assertEquals("Mouse", doBanco.getNome());
 }

 // ---------------------------------------------------------------
 // Casos adicionais (alem de CT01-CT03): regras de validacao com
 // persistencia real, sem mocks.
 // ---------------------------------------------------------------

 @Test void naoDevePersistirProdutoComNomeNulo(){
  long antes = repository.count();

  assertThrows(IllegalArgumentException.class, () -> service.cadastrar(null, 80.0));

  assertEquals(antes, repository.count());
 }

 @Test void naoDevePersistirProdutoComPrecoNaoPositivo(){
  long antes = repository.count();

  assertThrows(IllegalArgumentException.class, () -> service.cadastrar("Teclado", 0));
  assertThrows(IllegalArgumentException.class, () -> service.cadastrar("Teclado", -10.0));

  assertEquals(antes, repository.count());
 }

 @Test void naoDeveAtualizarParaPrecoInvalidoENemAlterarOPrecoPersistido(){
  Long id = service.cadastrar("Mouse", 80.0).getId();
  sincronizarComBanco();

  assertThrows(IllegalArgumentException.class, () -> service.atualizarPreco(id, -1.0));
  sincronizarComBanco();

  assertEquals(80.0, repository.findById(id).orElseThrow().getPreco(), 0.001);
 }

 @Test void deveFalharAoBuscarOuAtualizarProdutoInexistente(){
  assertThrows(IllegalArgumentException.class, () -> service.buscar(999_999L));
  assertThrows(IllegalArgumentException.class, () -> service.atualizarPreco(999_999L, 10.0));
 }
}
