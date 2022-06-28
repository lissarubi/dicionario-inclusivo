<template>
  <div class="translate">
    <TranslateBox class="translateBox" />
  </div>
</template>

<script>
import TranslateBox from '../components/TranslateBox.vue'

export default {
  name: 'translatePage',
  components: {
    TranslateBox
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.translateBox {
  text-align: center;
  margin-top: 5vh;
}
</style>
