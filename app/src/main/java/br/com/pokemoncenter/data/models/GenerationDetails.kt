package br.com.pokemoncenter.data.models

data class Generation(
    val general: GeneralInfo,
    val features: List<String>,
    val starters: List<Character>,
    val legendary: List<Character>,
    val importantCharacters: List<Character>,
    val gymLeaders: List<Character>,
    val eliteFour: List<Character>
)

data class GeneralInfo(
    val generationName: String,
    val region: String,
    val introducedPokemon: String,
    val startDexNumber: String,
    val endDexNumber: String,
    val games: String,
    val releaseYear: String
)

data class Character(
    val name: String,
    val role: String,
    val image: String
)
