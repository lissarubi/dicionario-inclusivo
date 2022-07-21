<template>
  <header>
    <strong class="header-title">Dicionário Inclusivo</strong>

    <div class="logo"><img class="logoImage" src="logo.png"
        alt="Logo do dicionário inclusivo, sendo um balão de fala, com uma pessoa no meio, dividida em várias cores diferentes">
    </div>

    <nav class="header-nav">
      <router-link to="/" class="header-anchor">Traduzir</router-link>
      <strong>|</strong>
      <router-link to="/contribuir" class="header-anchor">Contribuir</router-link>
      <strong>|</strong>
      <router-link to="/sobre" class="header-anchor">Sobre</router-link>
      <strong>|</strong>
    </nav>
  </header>
</template>

<script>
export default {
  name: "HeaderComponent",
};
</script>

<style scoped>
header {
  background-color: var(--yellow);
  padding: 2vh 8vw;
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
}

.logoImage {
  width: 5em;
}

@media screen and (min-width: 1001px) {
  header {
    padding: 2vh 8vw;
    justify-content: space-between;
  }
}

@media screen and (max-width: 1000px) {
  header {
    padding: 3vh 8vw;
    flex-direction: column;
    justify-content: space-around;
  }
}

.header-nav {
  height: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}

.header-nav>* {
  margin: 0.3em;
}

.header-title {
  font-size: 2.5ch;
}

.header-anchor,
.header-title {
  color: black;
}

a {
  text-decoration: none;
}
</style>
