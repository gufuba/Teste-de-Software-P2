package br.edu.exemplo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
@SpringBootTest @AutoConfigureMockMvc @Transactional
class ProdutoControllerIntegrationTest {
 @Autowired MockMvc mockMvc;
 @Autowired ObjectMapper objectMapper;

 private static final String JSON_MOUSE = "{\"nome\":\"Mouse\",\"preco\":80.0}";

 // CT04 — Cadastro via API
 @Test void deveCadastrarProdutoPelaApi() throws Exception {
  mockMvc.perform(post("/produtos")
     .contentType(MediaType.APPLICATION_JSON)
     .content(JSON_MOUSE))
   .andExpect(status().isOk())
   .andExpect(jsonPath("$.id").isNumber())
   .andExpect(jsonPath("$.nome").value("Mouse"))
   .andExpect(jsonPath("$.preco").value(80.0));
 }

 // CT05 — Consulta via API
 @Test void deveBuscarProdutoPelaApi() throws Exception {
  MvcResult cadastro = mockMvc.perform(post("/produtos")
     .contentType(MediaType.APPLICATION_JSON)
     .content(JSON_MOUSE))
   .andExpect(status().isOk())
   .andReturn();
  JsonNode criado = objectMapper.readTree(cadastro.getResponse().getContentAsString());
  long id = criado.get("id").asLong();

  mockMvc.perform(get("/produtos/{id}", id))
   .andExpect(status().isOk())
   .andExpect(jsonPath("$.id").value(id))
   .andExpect(jsonPath("$.nome").value("Mouse"))
   .andExpect(jsonPath("$.preco").value(80.0));
 }

 // ---------------------------------------------------------------
 // Casos adicionais (alem de CT04 e CT05)
 // ---------------------------------------------------------------

 @Test void deveRejeitarCadastroComNomeEmBrancoPelaApi() {
  // A aplicacao nao possui @ControllerAdvice: a IllegalArgumentException do
  // Service atravessa o Controller e o MockMvc a devolve embrulhada em ServletException.
  ServletException erro = assertThrows(ServletException.class, () ->
    mockMvc.perform(post("/produtos")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"nome\":\"\",\"preco\":80.0}")));
  assertInstanceOf(IllegalArgumentException.class, erro.getCause());
  assertEquals("Nome obrigatório", erro.getCause().getMessage());
 }

 @Test void deveRejeitarCadastroComPrecoNegativoPelaApi() {
  ServletException erro = assertThrows(ServletException.class, () ->
    mockMvc.perform(post("/produtos")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"nome\":\"Mouse\",\"preco\":-5.0}")));
  assertInstanceOf(IllegalArgumentException.class, erro.getCause());
 }

 @Test void deveFalharAoBuscarProdutoInexistentePelaApi() {
  ServletException erro = assertThrows(ServletException.class, () ->
    mockMvc.perform(get("/produtos/{id}", 999_999L)));
  assertInstanceOf(IllegalArgumentException.class, erro.getCause());
  assertEquals("Produto não encontrado", erro.getCause().getMessage());
 }
}
