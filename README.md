# Dicionário Inclusivo

> Projeto ainda em construção

O Dicionário inclusivo é um site que altera textos com expressões ou palavras não inclusivas, e substitui por alternativas inclusivas.

# Estrutura

O projeto está organizado em um *monorepo*, com um único repositório guardando todo o projeto dentro de um único lugar.

- `database` - guarda o banco de dados do projeto, que é um JSON, onde novas palavras podem ser adicionadas.
- `backend` - essa pasta guarda todo o backend do projeto, feito em Ktor.
