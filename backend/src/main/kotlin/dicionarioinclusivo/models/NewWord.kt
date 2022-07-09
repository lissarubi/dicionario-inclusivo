package dicionarioinclusivo.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class NewWord(
    val id: String = UUID.randomUUID().toString(),
    val wrongWords: List<String>,
    val correctWord: String,
    val wordGender: String
)
