<template>
  <section class="contribute">
    <h1>Contribua com o dicionário</h1>

    <p>
      Para contribuir com o projeto, preencha o formulário abaixo com a nova
      palavra, que a sua sugestão será analisada, e depois inserida no banco de dados.
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
        <option value="nb">Não Binárie/Neutro</option>
        <option value="fem">Feminina</option>
        <option value="masc">Masculino</option>
      </select>
    </div>

    <p>
      Caso você tenha dúvida sobre o gênero da palavra, é basicamente com qual artigo você usa a palavra, então por
      exemplo, <strong>cadeira</strong> é uma palavra de gênero feminino, porque é <strong>A</strong> cadeira, não
      <strong>O</strong> cadeira.
    </p>

    <p>
      Palavras não bináries/neutras são aquelas que não inferem gênero nenhum. Como <strong>todes</strong>,
      <strong>amigues</strong> e <strong>alunes</strong>. Você também pode selecionar essa opção para palavras que podem
      ser usadas para qualquer gênero, como <strong>indígena</strong>, que pode ser <strong>O</strong> indígena, ou
      <strong>A</strong> indígena.
    </p>

    <div class="generateDictionaryDiv">
      <button class="saveWord" @click="saveWord">
        Adicionar palavra
      </button>
    </div>
  </section>
</template>

<script>
import axios from "axios";
import VueSimpleAlert from "vue3-simple-alert";

export default {
  name: "ContributePage",

  data: () => ({
    BACKEND_URL: process.env.VUE_APP_BACKEND_URL,

    wrongWordCount: 1,

    correctWord: "",
    wordGender: "nb",
  }),

  methods: {
    addWrongWord() {
      this.wrongWordCount++;
    },
    saveWord() {
      let wrongWords = [];
      let wrongWordsElements = document.getElementsByClassName("wrongWord");

      for (let i = 1; i <= this.wrongWordCount; i++) {
        wrongWords.push(wrongWordsElements[i - 1].value);
      }

      axios.post(`${this.BACKEND_URL}/new`, {
        wrongWords,
        correctWord: this.correctWord,
        wordGender: this.wordGender
      })

      VueSimpleAlert.fire({ title: "Palavra adicionada!", type: "success" })
    },
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

.correctWord {
  width: 40%;
}

.wordGender {
  margin-bottom: 2em;
  border-style: solid;
  border-radius: 5px;
  border-color: var(--lightBlue);
  background-color: #fafafa;
  font-size: 1em;
}

.saveWord {
  font-size: 2.5ch;
  padding: 0.2em;
  border-style: solid;
  border-radius: 6px;
  border-color: #fafafa;
  background-color: var(--lightBlue);
}

@media screen and (max-width: 1000px) {
  .contribute {
    text-align: left;
    width: 80%;
  }

  p {
    text-align: justify;
  }

  .wrongWords {
    width: 100%;
  }

  .correctWord {
    width: 100%;
  }
}
</style>
