# Dicionário Inclusivo

> Projeto ainda em construção

O Dicionário inclusivo é um site que altera textos com expressões ou palavras não inclusivas, e substitui por alternativas inclusivas.

# Motivação

O projeto foi sugerido pela [Letícia Silva (dii_lua)](https://twitter.com/dii_lua/), durante o projeto dela e do [Willian Frantz](frantz_willian) que fui contratada. Gostei da sugestão e criei o projeto, com o objetivo de ser o mais open source e livre possível, facilitando novas contribuições ao dicionário.

# Estrutura

O projeto está organizado em um *monorepo*, com um único repositório guardando todo o projeto dentro de um único lugar.

- `database` - guarda o banco de dados do projeto, que é um JSON, onde novas palavras podem ser adicionadas.
- `frontend` - frontend do projeot, feito em VueJS.
- `backend` - backend do projeto, feito em Ktor.
