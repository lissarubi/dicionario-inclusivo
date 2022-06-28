<template>
  <Header />
  <router-view />
</template>

<script>
import Header from './components/Header.vue'

export default {
  name: 'App',
  components: {
    Header
  }
}
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,300;0,400;0,700;1,300;1,400;1,700&display=swap");
:root {
  --yellow: #f5e51c;
  --purple: #7109b6;
  --lightBlue: #4bc9f1;
  --darkBlue: #3a0ca3;
}
body {
  margin: 0;
}
* {
  font-family: "Poppins";
}
</style>
