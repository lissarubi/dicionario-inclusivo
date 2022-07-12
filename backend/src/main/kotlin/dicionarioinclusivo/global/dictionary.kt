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
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Dictionary{
    private val client = HttpClient()

    var dictionary = generateDictionary()

    init {
        generateDictionaryPeriodically(3)
    }
    private fun generateDictionaryPeriodically(time: Long){
        val executorService = Executors.newSingleThreadScheduledExecutor()
        executorService.scheduleAtFixedRate({
            dictionary = generateDictionary()
        }, time, time, TimeUnit.MINUTES)
    }

    private fun generateDictionary(): MutableList<DictionaryEntry> = runBlocking {
        val dbBrute: String = client.get("https://raw.githubusercontent.com/lissatransborda/dicionario-inclusivo/main/database/db.json").bodyAsText()

        val db = Json.decodeFromString<List<Word>>(dbBrute)
        val dictionaryEntries = mutableListOf<DictionaryEntry>()

        db.forEach {
            val wrongWords = it.palavrasIncorretas.joinToString("-")
            dictionaryEntries.add(DictionaryEntry(mapOf(wrongWords to it.palavraCorreta), it.gêneroDaPalavra))
        }

        return@runBlocking dictionaryEntries
    }
}
