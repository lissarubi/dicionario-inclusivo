package dicionarioinclusivo.routes

import dicionarioinclusivo.models.NewWord
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import schemas.NewWords

val PASSWORD = System.getenv("PASSWORD_TO_NEW_WORDS") ?: "default"

fun Route.newWordRoutes() {
    route("/new") {
        postNewWord()
        getNewWords()
        deleteNewWord()
    }
}

fun Route.getNewWords(){
    get {
        try {
            val newWords = transaction {
                NewWords.selectAll().map { NewWords.toNewWord(it) }
            }

            return@get call.respond(newWords)
        } catch (error: Exception) {
            return@get call.respondText("Error searching new words", status = HttpStatusCode.InternalServerError)
        }
    }
}

fun Route.postNewWord() {
    post {
        try {
            val newWord = call.receive<NewWord>()

            val insert = transaction {
                NewWords.insert {
                    it[id] = newWord.id
                    it[wrongWords] = newWord.wrongWords.joinToString("-")
                    it[correctWord] = newWord.correctWord
                    it[wordGender] = newWord.wordGender
                }
            }

            if (insert.equals(0)) {
                return@post call.respondText("Error posting new word", status = HttpStatusCode.InternalServerError)
            }

            return@post call.respond(newWord)
        }
        catch (error: Exception) {
            return@post call.respondText("Error posting new word", status = HttpStatusCode.InternalServerError)
        }
    }
}
fun Route.deleteNewWord() {
    delete("{id}") {
        try {
            val id = call.parameters["id"]
            val password = call.request.queryParameters["password"] ?: ""

            if (id == null){
                return@delete call.respondText("missing ID", status = HttpStatusCode.BadRequest)
            }

            if (password == PASSWORD) {
                val remove = transaction {
                    NewWords.deleteWhere { NewWords.id eq id }
                }

                if (remove.equals(0)) {
                    return@delete call.respondText("word not found", status = HttpStatusCode.InternalServerError)
                }

                return@delete call.respondText("Word deleted")
            }else{
                return@delete call.respondText("wrong passoword", status = HttpStatusCode.BadRequest)
            }
        } catch (error: Exception) {
            return@delete call.respondText("error deleting word", status = HttpStatusCode.InternalServerError)
        }
    }
}
