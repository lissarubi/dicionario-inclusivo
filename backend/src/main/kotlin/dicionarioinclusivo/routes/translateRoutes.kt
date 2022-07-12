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
        }

        // check multiple wrong words
        else if (wrongWord.contains(" ") && textBeingTranslated.joinToString(" ").contains(wrongWord)){
          val wrongWordSplit = wrongWord.split(" ")
          val textToCheck = textBeingTranslated.joinToString(" ").replace(",", "").replace(".", "").replace(":", "").replace(";", "").replace("\n", "").split(" ")
          val wrongWordIndex = textToCheck.indexOf(wrongWordSplit[0])

          if (wrongWordIndex != 0){
            textBeingTranslated[wrongWordIndex - 1] = fixWordGender(textBeingTranslated[wrongWordIndex - 1], dictionaryEntry.wordGender, plural)
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
    if (correctText == "umas") correctText = "uns"
    if (correctText == "as") correctText = "os"
  } else {
    if (correctText == "uma") correctText = "um"
    if (correctText == "a") correctText = "o"
  }

  return correctText
}

fun fixToFem(textToTranslate: String, plural: Boolean): String {
  var correctText = textToTranslate

  if (plural) {
    if (correctText == "uns") correctText = "umas"
    if (correctText == "os") correctText = "as"
  } else {
    if (correctText == "um") correctText = "uma"
    if (correctText == "o") correctText = "a"
  }

  return correctText
}

fun fixToNB(textToTranslate: String, plural: Boolean): String {
  var correctText = textToTranslate

  if (plural) {
    if (correctText == "umas") correctText = "umes"
    if (correctText == "uns") correctText = "umes"
    if (correctText == "as" || correctText == "os") correctText = "es"
  } else {
    if (correctText == "uma") correctText = "ume"
    if (correctText == "um") correctText = "ume"
    if (correctText == "a" || correctText == "o") correctText = "ê"
  }

  return correctText
}
