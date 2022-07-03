<template>
  <section class="contribute">
    <h1>Contribua com o dicionário</h1>

    <p>
      Para contribuir com o projeto, preencha o formulário abaixo com a nova
      palavra, e gere o novo dicionário. Com isso, um arquivo chamado
      <code>db.json</code> será baixado. Esse arquivo representa o dicionário
      completo.
    </p>

    <p>
      Após isso, veja esse vídeo para inserir o seu novo dicionário na
      plataforma.
    </p>

    <h2>Palavra(s) incorreta(s)</h2>
    <div class="wrongWords" v-for="word in wrongWordCount" :key="word">
      <input type="text" class="wrongWord" />
    </div>
    <button @click="addWrongWord" class="addWrongWord">+</button>

    <h2>Palavra correta</h2>
    <div class="correctWordDiv">
      <input type="text" class="correctWord" v-model="correctWord" />
    </div>

    <h2>Gênero da palavra</h2>

    <div class="wordGenderDiv">
      <select name="wordGender" class="wordGender" v-model="wordGender">
        <option value="nb">Não Binárie</option>
        <option value="fem">Feminina</option>
        <option value="masc">Masculino</option>
      </select>
    </div>

    <div class="generateDictionaryDiv">
      <button class="generateDictionary" @click="generateDictionary">
        Gerar Dicionário
      </button>
    </div>
  </section>
</template>

<script>
import axios from "axios";
import { saveAs } from "file-saver";

export default {
  name: "ContributePage",

  data: () => ({
    wrongWordCount: 1,
    dictionary: [],

    correctWord: "",
    wordGender: "nb",
  }),

  methods: {
    addWrongWord() {
      this.wrongWordCount++;
    },
    generateDictionary() {
      let wrongWords = [];
      let wrongWordsElements = document.getElementsByClassName("wrongWord");
      console.log(wrongWordsElements);

      for (let i = 1; i <= this.wrongWordCount; i++) {
        wrongWords.push(wrongWordsElements[i - 1].value);
      }

      this.dictionary.push({
        palavrasIncorretas: wrongWords,
        palavrasCorretas: this.correctWord,
        gêneroDaPalavra: this.wordGender,
      });

      let dictionaryString = JSON.stringify(this.dictionary);

      let dictionaryFile = new Blob([dictionaryString], { type: "text/plain" });
      saveAs(dictionaryFile, "db.json");
    },
  },
  async mounted() {
    let response = await axios.get(
      "https://raw.githubusercontent.com/lissatransborda/dicionario-inclusivo/main/database/db.json"
    );
    this.dictionary = response.data;
  },
};
</script>

<style scoped>
.contribute {
  margin: 0 auto 20vh;
  width: 50%;
  text-align: center;
}

p {
  text-align: left;
}

code {
  font-family: "Courier New", Courier, monospace;
  background-color: black;
  color: white;
  padding: 3px;
  border-radius: 3px;
}

input {
  border-style: solid;
  border-color: #aaa;
  border-radius: 5px;
}

.wrongWords {
  margin: 1em;
  display: flex;
  flex-direction: column;
  width: 40%;
  margin: 0 auto;
}

.addWrongWord {
  width: 10%;
  font-size: 2ch;
  border-style: solid;
  border-radius: 5px;
  border-color: #fafafa;
  background-color: var(--lightBlue);
}

.wrongWord {
  margin-bottom: 1.5em;
}

.wordGender {
  margin-bottom: 2em;
  border-style: solid;
  border-radius: 5px;
  border-color: var(--lightBlue);
  background-color: #fafafa;
  font-size: 1em;
}

.generateDictionary {
  font-size: 2.5ch;
  padding: 0.2em;
  border-style: solid;
  border-radius: 6px;
  border-color: #fafafa;
  background-color: var(--lightBlue);
}
</style>
