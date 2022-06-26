package dicionarioinclusivo.models

import kotlinx.serialization.Serializable

@Serializable
data class Word (val palavrasIncorretas: List<String>, val palavraCorreta: String, val gêneroDaPalavra: String)