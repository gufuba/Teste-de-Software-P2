# Teste de Usuário Final — Mercado Livre (busca, comparação e simulação de compra)

**Disciplina:** Teste de Software — Semana 14
**Site avaliado:** https://www.mercadolivre.com.br (versão desktop, navegador web)
**Data do registro:** 14/11/2026
**Participante:** 1 usuário final (eu), com a conta do Mercado Livre já autenticada

## 1. Objetivo

Avaliar facilidade de uso, eficiência e clareza da interface do Mercado Livre nas etapas de
**busca, comparação e simulação de compra** de um produto, aplicando conceitos de IHC e de
testes de usabilidade.

## 2. Cenário

Procurar um **fone de ouvido sem fio** para uso diário, encontrar um modelo bem avaliado,
comparar opções e simular a compra **até o carrinho/início do checkout, sem finalizar o
pagamento**.

## 3. Tarefas executadas

1. Acessar o site.
2. Pesquisar "fone de ouvido Bluetooth".
3. Usar os filtros: apenas entrega **Full** e ordenar pelos **melhores avaliados**.
4. Escolher um produto e acessar os detalhes.
5. Adicionar o item ao carrinho.
6. Simular o início da compra (sem concluir o pagamento).
7. Retornar à tela inicial.

**Produto escolhido:** Fone Bluetooth Samsung Galaxy Buds Core Preto — nota 4,9 (46.534
opiniões), "+100 mil vendidos", R$ 214,63 no Pix, enviado pelo Full.

## 4. Planilha de observação

| Tarefa | Tempo (s) | Sucesso (S/N) | Erros | Observações |
|---|---:|:---:|:---:|---|
| 1. Acessar site | 7 | S | 0 | Página inicial carregou sem dificuldades. |
| 2. Buscar produto | 5 | S | 0 | Campo de busca em destaque no topo; resultados com 2.371 itens. |
| 3. Aplicar filtros | 37 | **Parcial** | 1 | Filtro **Full** fácil de achar (chave no topo da lateral). **Não existe** opção "melhores avaliados": o menu "Ordenar por" só oferece *Mais relevantes*, *Menor preço* e *Maior preço*, e não há filtro de avaliação na lateral. Procurei a opção antes de concluir que não existia (perda de caminho). Contornei comparando as notas exibidas em cada card. |
| 4. Abrir detalhes | 8 | S | 0 | Card com nota e quantidade vendida ajudou a escolher. |
| 5. Adicionar ao carrinho | 36 | S | 1 | O primeiro clique funcionou, mas a tela anterior continuou visível por alguns segundos. Cliquei de novo e acertei uma recomendação ("smartwatch") da tela de confirmação. Conferi o carrinho: 1 item, correto. |
| 6. Simular compra | 38 | S | 0 | Carrinho → tela de **seguro** (não prevista) → "Finalize sua compra". Parei antes de "Pagar e finalizar". |
| 7. Voltar à tela inicial | 13 | S | 0 | Logotipo leva à home, que já exibe "Compre seu carrinho" com o item. |
| **Total** | **144 (2 min 24 s)** | | **2** | |

## 5. Métricas e critérios de sucesso

| Métrica | Resultado |
|---|---|
| Tempo de execução | **2 min 24 s** |
| Taxa de sucesso (fluxo concluído sem ajuda) | Concluí o fluxo sem ajuda, com uma adaptação na tarefa 3 (sem ordenação por avaliação). |
| Erros cometidos | 2 (1 perda de caminho/confusão nos filtros; 1 clique incorreto após adicionar ao carrinho). |

| Critério de sucesso | Meta | Resultado | Atendido? |
|---|---|---|:---:|
| Usuários que adicionam o produto ao carrinho | ≥ 90% | 1 de 1 (100%) | Sim* |
| Tempo médio para concluir o teste | ≤ 4 min | 2 min 24 s | Sim* |

\* Com **um único participante**, os percentuais e a média não têm valor estatístico; servem
apenas como registro desta execução.

## 6. Problemas de usabilidade encontrados

Classificados por gravidade (alta / média / baixa) e relacionados às heurísticas de Nielsen.

| # | Gravidade | Problema | Heurística |
|---|:---:|---|---|
| 1 | **Alta** | **A tarefa "ordenar pelos melhores avaliados" não pôde ser feita como descrita**: o menu de ordenação não tem esse critério e não há filtro de avaliação. A nota só aparece dentro de cada card, forçando comparação manual. | Flexibilidade e eficiência de uso; compatibilidade com o mundo real |
| 2 | Média | **Tela de seguro surge antes do checkout**, com a opção de 2 anos **já pré-selecionada** e marcada como "Recomendado"; o botão "Agora não" é secundário e "Adicionar" tem o destaque azul. Adiciona uma etapa e induz a aceitar. | Controle e liberdade do usuário; prevenção de erros |
| 3 | Média | **Feedback lento/ambíguo ao adicionar ao carrinho**: a página anterior permanece visível até a tela de confirmação carregar, o que levou a um segundo clique em um elemento errado. | Visibilidade do status do sistema |
| 4 | Média | **Mensagens e valores confusos no carrinho**: aparece "Você atingiu a quantidade máxima disponível" com 1 unidade e "+50 disponíveis"; "Aproveite o frete grátis adicionando mais produtos" ao lado de frete já grátis; e o resumo mostra "Total R$ 367,80" junto de "R$ 214,63 no Pix". | Consistência e padrões; reconhecimento em vez de memorização |
| 5 | Média | **Preço muda entre telas**: R$ 214,63 (Pix) na página do produto e **R$ 221,27** no total do checkout até escolher o meio de pagamento (o Pix não vem selecionado). A relação com o desconto do Pix não é óbvia. | Visibilidade do status do sistema; prevenção de erros |
| 6 | Baixa | **Anúncios patrocinados ("Ad") misturados** aos primeiros resultados e banner de marca grande empurrando a lista para baixo. | Design estético e minimalista |
| 7 | Baixa | **Rótulo "FULL com frete grátis"** mistura dois conceitos (tipo de envio e frete grátis) e fica logo acima de outro filtro "Frete grátis", o que pode confundir. | Consistência e padrões |
| 8 | Baixa | **Banner de cookies fixo** cobre a parte inferior de todas as páginas até o usuário responder. | Estética e design minimalista |

## 7. Pontos positivos

- Campo de busca em destaque e resultado imediato.
- Filtro Full simples (chave liga/desliga), com feedback claro: contador de resultados (2.371),
  indicador de carregamento e URL refletindo o filtro.
- Cards com **nota, quantidade vendida, preço, desconto e frete** facilitam a comparação.
- Página de detalhes completa: avaliação, opiniões, preço, parcelamento, prazo de entrega,
  estoque, vendedor e política de devolução; dois botões ("Comprar agora" e "Adicionar ao
  carrinho") com hierarquia visual clara.
- Checkout em **tela única** (entrega, pagamento e resumo juntos), com endereço já preenchido
  e botão "Pagar e finalizar" evidente.
- Estado do carrinho **persistente**: a home passa a mostrar "Compre seu carrinho".

## 8. Recomendações

1. Incluir **"Melhor avaliados"** no "Ordenar por" e/ou um filtro por nota (ex.: 4 estrelas ou mais).
2. Não pré-selecionar o seguro e dar o mesmo peso visual a "Agora não" e "Adicionar".
3. Dar **confirmação imediata** ao adicionar ao carrinho (estado de carregamento no botão ou
   aviso "Adicionado!") antes de trocar de tela.
4. Revisar as mensagens do carrinho (quantidade máxima, frete grátis) e o resumo de valores,
   mostrando **um único total a pagar**.
5. Explicar no checkout que o desconto do Pix só é aplicado ao escolher esse meio de pagamento.

## 9. Limitações

- Apenas **um participante** e **uma execução**; os tempos e erros não são generalizáveis.
- O site tinha campanhas sazonais (Black Friday 2026) e conteúdo personalizado por conta, então a
  interface e os resultados podem variar entre usuários e datas.
- Nenhum pagamento foi realizado: parei na tela "Finalize sua compra".

---

## Anexo A — Formulário SUS 

Escala de 1 (discordo totalmente) a 5 (concordo totalmente).

| # | Afirmação | 1 | 2 | 3 | 4 | 5 |
|---|---|:-:|:-:|:-:|:-:|:-:|
| 1 | Acho que gostaria de usar este sistema com frequência. | | | |x| |
| 2 | Achei o sistema desnecessariamente complexo. | |x| | | |
| 3 | Achei o sistema fácil de usar. | | | | |x|
| 4 | Acho que precisaria de ajuda de uma pessoa técnica para usar o sistema. | |x| | | |
| 5 | Achei que as várias funções do sistema estavam bem integradas. | | | | |x|
| 6 | Achei que havia muita inconsistência no sistema. |x| | | | |
| 7 | Imagino que a maioria das pessoas aprenderia a usar o sistema rapidamente. | | | |x| |
| 8 | Achei o sistema muito complicado de usar. | |x| | | |
| 9 | Senti-me muito confiante ao usar o sistema. | | | |x| |
| 10 | Precisei aprender muitas coisas antes de conseguir usar o sistema. | |x| | | |
