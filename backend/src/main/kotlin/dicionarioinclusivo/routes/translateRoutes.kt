package dicionarioinclusivo.routes

import dicionarioinclusivo.global.Dictionary
import dicionarioinclusivo.models.Text
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val dictionary = Dictionary().dictionary

fun Route.translateRoutes() {
  route("/translate") { postTranslate() }
}

fun Route.postTranslate() {
  post {
    val text = call.receive<Text>()
    val textTranslated = translate(text.content)
    call.respond(textTranslated)
  }
}

fun translate(textToTranslate: String): String {
  var textBeingTranslated = textToTranslate.split(" ").toMutableList()

  for ((index, wordBrute) in textBeingTranslated.withIndex()) {
    val word = wordBrute.replace(",", "").replace(".", "").replace(":", "").replace(";", "").replace("\n", "")
    dictionary.forEach { dictionaryEntry ->
      val keys = dictionaryEntry.word.keys.first().split("-")
      keys.forEach { wrongWord ->
        val plural = wrongWord.last() == 's'

        // check only one wrong word
        if (word == wrongWord) {
          textBeingTranslated[index] =
              fixWrongWord(wrongWord, correctWord = dictionaryEntry.word.values.first(), textBeingTranslated[index])

          if (index != 0){
            textBeingTranslated[index - 1] =
              fixWordGender(textBeingTranslated[index - 1], dictionaryEntry.wordGender, plural)
          }
          if (index >= 1){
            textBeingTranslated[index - 2] =
              fixWordGender(textBeingTranslated[index - 2], dictionaryEntry.wordGender, plural)
          }
        }

        // check multiple wrong words
        else if (wrongWord.contains(" ") && textBeingTranslated.joinToString(" ").contains(wrongWord)){
          val wrongWordSplit = wrongWord.split(" ")
          val textToCheck = textBeingTranslated.joinToString(" ").replace(",", "").replace(".", "").replace(":", "").replace(";", "").replace("\n", "").split(" ")
          val wrongWordIndex = textToCheck.indexOf(wrongWordSplit[0])

          if (wrongWordIndex != 0){
            textBeingTranslated[wrongWordIndex - 1] = fixWordGender(textBeingTranslated[wrongWordIndex - 1], dictionaryEntry.wordGender, plural)
          }
          if (wrongWordIndex >= 1){
            textBeingTranslated[wrongWordIndex - 2] = fixWordGender(textBeingTranslated[wrongWordIndex - 2], dictionaryEntry.wordGender, plural)
          }

          textBeingTranslated = fixMultipleWrongWords(wrongWord, correctWord = dictionaryEntry.word.values.first(), textBeingTranslated.joinToString(" "))
        }
      }
    }
  }
  return textBeingTranslated.joinToString(" ")
}

fun fixWrongWord(wrongWord: String, correctWord: String, textBeingTranslated: String): String {
  var correctText = textBeingTranslated
  correctText = correctText.replace(wrongWord, correctWord)

  return correctText
}

fun fixMultipleWrongWords(wrongWord: String, correctWord: String, textBeingTranslated: String): MutableList<String> {
  var correctText = textBeingTranslated
  correctText = correctText.replace(wrongWord, correctWord)

  return correctText.split(" ") as MutableList<String>
}

fun fixWordGender(textBeingTranslated: String, wordGender: String, plural: Boolean): String {
  var correctWord = textBeingTranslated

  when (wordGender) {
    "masc" -> {
      correctWord = fixToMasc(textBeingTranslated, plural)
    }
    "fem" -> {
      correctWord = fixToFem(textBeingTranslated, plural)
    }
    "nb" -> {
      correctWord = fixToNB(textBeingTranslated, plural)
    }
  }
  return correctWord
}

fun fixToMasc(textToTranslate: String, plural: Boolean): String {
  var correctText = textToTranslate

  if (plural) {
    when (correctText) {
      "umas", "umes" -> correctText = "uns"
      "as", "es" -> correctText = "os"
      "todas", "todes" -> correctText = "todos"
      "delas", "delus" -> correctText = "deles"
      "suas", "sues" -> correctText = "seus"
    }
  } else {
    when (correctText) {
      "uma", "ume" -> correctText = "um"
      "a", "ê" -> correctText = "o"
      "toda", "tode" -> correctText = "todo"
      "dela", "delu" -> correctText = "dele"
      "sua", "sue" -> correctText = "seu"
    }
  }

  return correctText
}

fun fixToFem(textToTranslate: String, plural: Boolean): String {
  var correctText = textToTranslate

  if (plural) {
    when (correctText) {
      "uns", "umes" -> correctText = "umas"
      "os", "es" -> correctText = "as"
      "todos", "todes" -> correctText = "todas"
      "deles", "delus" -> correctText = "delas"
      "seus", "sues" -> correctText = "suas"
    }
  } else {
    when (correctText) {
      "um", "umes" -> correctText = "uma"
      "o", "ê" -> correctText = "a"
      "todo", "tode" -> correctText = "toda"
      "dele", "delu" -> correctText = "dela"
      "seu", "sue" -> correctText = "sua"
    }
  }

  return correctText
}

fun fixToNB(textToTranslate: String, plural: Boolean): String {
  var correctText = textToTranslate

  if (plural) {
    when (correctText) {
      "umas", "uns" -> correctText = "umes"
      "as", "os" -> correctText = "es"
      "todas", "todos" -> correctText = "todes"
      "delas", "deles" -> correctText = "delus"
      "suas", "seus" -> correctText = "sues"
    }
  } else {
    when (correctText) {
      "uma" -> correctText = "ume"
      "um" -> correctText = "ume"
      "a", "o" -> correctText = "ê"
      "toda", "todo" -> correctText = "tode"
      "dela", "dele" -> correctText = "delu"
      "sua", "seu" -> correctText = "sue"
    }
  }

  return correctText
}
