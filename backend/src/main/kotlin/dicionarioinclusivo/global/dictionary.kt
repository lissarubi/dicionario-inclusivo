package dicionarioinclusivo.global

import dicionarioinclusivo.models.DictionaryEntry
import dicionarioinclusivo.models.Word
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

val client = HttpClient()

var dictionary = generateDictionary()

fun generateDictionary() = runBlocking {
    val dbBrute: String = client.get("https://pastebin.com/raw/q9UMhLxF").bodyAsText()

    val db = Json.decodeFromString<List<Word>>(dbBrute)
    val dictionary = mutableListOf<DictionaryEntry>()

    db.forEach {
        val wrongWords = it.palavrasIncorretas.joinToString("-")
        dictionary.add(DictionaryEntry(mapOf(wrongWords to it.palavraCorreta), it.gêneroDaPalavra))
    }

    return@runBlocking dictionary
}