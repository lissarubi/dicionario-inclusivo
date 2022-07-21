<template>
  <div class="translateBox">
    <textarea id="textToTranslate" @input="getText($event.target.value)" name="textToTranslate" rows="10" cols="50" />

    <button @click="translate" class="translateButton">Corrigir</button>

    <div class="resultBox" v-if="textTranslated != ''">
      <div class="translatedText">
        {{ textTranslated }}
      </div>

      <button class="copyButton" @click="copyText">
        <font-awesome-icon icon="fa-solid fa-copy" />
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import VueSimpleAlert from "vue3-simple-alert";

export default {
  name: "TranslateBoxComponent",
  data: () => ({
    textToTranslate: String,
    textTranslated: "",
    BACKEND_URL: process.env.VUE_APP_BACKEND_URL,
  }),
  methods: {
    getText(textToTranslate) {
      this.textToTranslate = textToTranslate;
    },
    async translate() {
      console.log(this.BACKEND_URL);
      let response = await axios.post(`${this.BACKEND_URL}/translate`, {
        content: this.textToTranslate,
      });
      this.textTranslated = response.data;
    },
    copyText() {
      navigator.clipboard.writeText(this.textTranslated)
      VueSimpleAlert.fire({ title: "Texto copiado!", type: "success" })
    }
  },
};
</script>

<style src="../global.css">
</style>

<style scoped>
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
  width: 60%;
  height: 30vh;
  font-size: 2ch;
}

.translateButton {
  margin-top: 3vh;
  width: 20%;
  font-size: 2ch;
  border-style: solid;
  border-radius: 5px;
  border-color: var(--lightGreen);
  background-color: var(--green);
  transition-duration: 400ms;
}

.translateButton:hover {
  background-color: var(--darkGreen);
  border-color: var(--darkGreen);
  color: white;
}

.resultBox {
  width: 60%;
}

.translatedText {
  text-align: justify;
  margin-top: 3vh;
  background-color: #f7f7f7;
  width: 100%;
  min-height: 10vh;
  padding: 0.5em;
}

.copyButton {
  font-size: 2ch;
  background-color: #efefef;
  border-color: #ddd;
  width: 100%;
  border-style: solid;
  border-radius: 5px;
  transition-duration: 400ms;
}

.copyButton:hover {
  background-color: var(--lightBlue);
  border-color: var(--lightBlue);
}

@media screen and (max-width: 1000px) {
  textarea {
    width: 80%;
  }
  .translateButton {
    font-size: 2.5ch;
    width: 30%;
  }
}
</style>
