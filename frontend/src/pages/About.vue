<template>
  <section class="textContent">
    <h2>O que é o Dicionario Inclusivo?</h2>
    <p>
      O Dicionário inclusivo é um site que facilita a o uso de termos
      inclusivos, com uma área onde podemos escrever um texto, e todas as
      palavras não-inclusivas desse texto serão removidas, e trocadas por uma
      alternativa inclusiva.
    </p>
  </section>

  <section class="textContent">
    <h2>Quem criou?</h2>
    <p>
      O dicionário inclusivo foi criado pela
      <a href="https://twitter.com/lissatransborda" target="_blank">Lissa Ferreira</a>. Produtora de conteúdo, Community
      Manager da comunidade <a href="https://feministech.github.io" target="_blank">Feministech</a>, Cofundadora da
      comunidade <a href="https://kotlinautas.dev/">Kotlinautas</a>,
      moderadora da mentoria de DevRel <a href="https://abacatinhos.dev/">Abacatinhos.dev</a> e cozinheira amadora.
    </p>
  </section>

  <section class="textContent">
    <h2>Como funciona?</h2>
    <p>
      Para saber mais sobre o funcionamento do dicionário inclusivo, o projeto
      está disponível no
      <a href="https://github.com/lissatransborda/dicionario-inclusivo" target="_blank">Github</a>, com todo o código
      fonte aberto.
    </p>
  </section>
</template>

<script>
export default {
  name: "AboutPage",
};
</script>

<style scoped>
.textContent {
  text-align: justify;
  width: 70%;
  margin: 0 auto;
}

p {
  font-size: 1.6ch;
}
</style>
