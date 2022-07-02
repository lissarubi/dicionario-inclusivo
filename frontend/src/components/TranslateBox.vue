<template>
  <div class="translateBox">
    <textarea id="textToTranslate" @input="getText($event.target.value)" name="textToTranslate" rows="10"
      cols="50"></textarea>

    <button @click="translate" class="translateButton">Corrigir</button>

    <div class="translatedText" v-if="textTranslated != ''">{{ textTranslated }}</div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: "TranslateBoxComponent",
  data: () => ({
    textToTranslate: String,
    textTranslated: '',
    BACKEND_URL: process.env.VUE_APP_BACKEND_URL,
  }),
  methods: {
    getText(textToTranslate) {
      this.textToTranslate = textToTranslate
    },
    async translate() {
      console.log(this.BACKEND_URL)
      let response = await axios.post(`${this.BACKEND_URL}/translate`, {
        content: this.textToTranslate
      })
      this.textTranslated = response.data
    }
  },
}
</script>

<style src='../global.css'>
</style>

<style>
.translateBox {
  margin: 0 auto;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: space-between;
}

textarea {
  border-radius: 6px;
  border-style: solid;
  border-color: #ddd;
  width: 40%;
  height: 30vh;
  font-size: 2ch;
}

button {
  margin-top: 3vh;
  width: 20%;
  font-size: 2ch;
  border-style: solid;
  border-radius: 5px;
  border-color: var(--lightGreen);
  background-color: var(--green);
  transition-duration: 400ms;
}

button:hover {
  background-color: var(--darkGreen);
  border-color: var(--darkGreen);
  color: white;
}

.translatedText {
  text-align: justify;
  margin-top: 3vh;
  background-color: #f7f7f7;
  width: 40%;
  min-height: 10vh;
  padding: 0.5em;
}
</style>
