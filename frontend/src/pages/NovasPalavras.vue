<template>
  <section class="newWordsSection" v-if="words.length >= 1">
    <h1>Novas palavras</h1>
    <button class="generateDictionary" @click="generateDictionary">Gerar dicionário</button>

    <div class="newWordList">
      <div v-for="word in words" :key="word" class="newWord">
        <div class="newWordInfo">
          <div class="correctWord">
            <h1>{{ word.correctWord }}</h1>
          </div>

          <h3 class="wrongWordsTitle">Palavras incorretas:</h3>
          <div class="wrongWords">
            <span class="wrongWord" v-for="wrongWord in word.wrongWords" :key="wrongWord">{{ wrongWord }}</span>
          </div>

          <h3 class="wordGenderTitle">Gênero da palavra:</h3>
          <span class="wordGender" v-if="word.wordGender == 'nb'">Não Binárie</span>
          <span class="wordGender" v-if="word.wordGender == 'fem'">Feminina</span>
          <span class="wordGender" v-if="word.wordGender == 'fem'">Masculino</span>
        </div>
        <button class="deleteButton" @click="deleteWord(word.id)">X</button>
      </div>
    </div>
  </section>

  <section v-else>
    <h2>Nenhuma palavra nova :(</h2>
  </section>

</template>

<script>
import axios from "axios";
import VueSimpleAlert from "vue3-simple-alert";

export default {
  name: "NewWordsPage",

  data: () => ({
    BACKEND_URL: process.env.VUE_APP_BACKEND_URL,

    words: [],
    dictionary: [],
  }),
  methods: {
    deleteWord(id) {
      if (localStorage.getItem("password")) {
        this.deleteWordRequest(id, localStorage.getItem("password"))
      }
      else {
        VueSimpleAlert.prompt("Senha para deletar palavra").then(async (password) => {
          this.deleteWordRequest(id, password).then((status) => {
            if (status == 200) {
              localStorage.setItem("password", password)
            }
          })
        })
      }
    },
    generateDictionary() {
      this.words.forEach((word) => {
        this.dictionary.push({
          palavrasIncorretas: word.wrongWords,
          palavraCorreta: word.correctWord,
          gêneroDaPalavra: word.wordGender
        })
      })

      console.log(JSON.stringify(this.dictionary))
    },
    async deleteWordRequest(id, password) {
      try {
        await axios.delete(`${this.BACKEND_URL}/new/${id}?password=${password}`);
        VueSimpleAlert.fire({ title: "Palavra deletada", type: "success" })
        let newWordsRequest = await axios.get(`${this.BACKEND_URL}/new`);
        this.words = newWordsRequest.data
        return newWordsRequest.status
      } catch (err) {
        if (err.response.status == 400) {
          VueSimpleAlert.fire({ title: "Senha incorreta", type: "error" })
          return err.response.status
        }
      }
    }
  },
  async mounted() {
    let newWordsRequest = await axios.get(`${this.BACKEND_URL}/new`);
    this.words = newWordsRequest.data

    let dictionaryRequest = await axios.get(
      "https://raw.githubusercontent.com/lissatransborda/dicionario-inclusivo/main/database/db.json"
    );
    this.dictionary = dictionaryRequest.data;
  },
};
</script>

<style scoped>
section {
  margin: 0 auto 20vh;
  width: 80%;
  text-align: center;
}

.generateDictionary {
  border-style: solid;
  border-radius: 5px;
  font-size: 2.5ch;
  background-color: var(--lightBlue);
}

.newWordList {
  margin-top: 2em;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-around;
}

.newWord {
  border-style: solid;
  border-radius: 10px;
  margin: 0.1em 0.1em 1.5em;
  padding: 1em;
  width: 25%;
}

.newWordInfo {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.deleteButton {
  margin-left: 85%;
  background-color: var(--purple);
  color: white;
  border-radius: 5px;
  font-size: 2ch;
  width: 15%;
  border-style: none;
  transition-duration: 300ms;
}

.deleteButton:hover {
  background-color: var(--red);
}
</style>
