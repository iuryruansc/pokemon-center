package br.com.pokemoncenter.data.repository

data class PokemonReturn<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)
