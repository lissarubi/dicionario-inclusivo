package schemas

import dicionarioinclusivo.models.NewWord
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object NewWords: Table(){
    val id: Column<String> = varchar("id", 36)

    val wrongWords: Column<String> = text("wrongWords")
    val correctWord: Column<String> = text("correctWord")
    val wordGender: Column<String> = text("wordGender")

    override val primaryKey = PrimaryKey(id, name = "PK_NewWords_Id")

    fun toNewWord(row: ResultRow): NewWord = NewWord(
        id = row[id],
        wrongWords = row[wrongWords].split("-"),
        correctWord = row[correctWord],
        wordGender = row[wordGender]
    )
}
