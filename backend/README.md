# Backend do Dicionário Inclusivo

Backend do projeto, que recebe o texto original e corrige as palavras, usando o banco de dados como fonte, e envia o texto corrigido como resposta da requisição.

## Rotas

- `/translate` - Rota POST de tradução, recebendo uma string `content` no corpo da requisição, com o texto, dessa forma:

```
http POST /translate content="eu amo todos daqui"
```

E com isso será retornada a resposta:

```
eu amo todes aqui
```

## Variáveis de ambiente

- `DEVELOPMENT`: Varíavel de ambiente que define se o servidor está rodando em modo de desenvolvimento ou não. Por padrão, é `true`.
- `CLIENT_URL`: URL do cliente (frontend), caso essa variável não seja especificada, qualquer host será aceito.

## Iniciar aplicação

```
./gradlew run
```

## Gerar shadowjar do projeto

```
./gradlew shadowjar
```
