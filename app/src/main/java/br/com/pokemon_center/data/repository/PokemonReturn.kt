package br.com.pokemon_center.data.repository

data class PokemonReturn<T> (
    val success: Boolean,
    val message: String,
    val data: T?
)