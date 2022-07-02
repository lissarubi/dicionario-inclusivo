package dicionarioinclusivo.routes

import dicionarioinclusivo.global.dictionary
import dicionarioinclusivo.models.Text
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
  val textBeingTranslated = textToTranslate.split(" ", ",", ".").toMutableList()

  for ((index, word) in textBeingTranslated.withIndex()) {
    dictionary.forEach { dictionaryEntry ->
      val keys = dictionaryEntry.word.keys.first().split("-")
      keys.forEach { wrongWord ->
        if (textBeingTranslated[index] == wrongWord) {
          val plural = wrongWord.last() == 's'
          textBeingTranslated[index] =
              fixWrongWord(wrongWord, correctWord = dictionaryEntry.word.values.first(), word)

          if (index != 0) {
            textBeingTranslated[index - 1] =
                fixWordGender(textBeingTranslated[index - 1], dictionaryEntry.wordGender, plural)
          }
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
