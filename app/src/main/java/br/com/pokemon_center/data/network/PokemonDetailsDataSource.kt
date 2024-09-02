package br.com.pokemon_center.data.network

import br.com.pokemon_center.data.api.ApiService
import br.com.pokemon_center.data.api.ApiServiceClient
import br.com.pokemon_center.data.api.models.PokemonByNameResponse
import br.com.pokemon_center.data.api.models.SpeciesByNameResponse

class PokemonDetailsDataSource(private val apiService: ApiService = ApiServiceClient.instance) {

    suspend fun fetchPokemonDetails(pokemon: String): PokemonByNameResponse? {
        val pokemonDetailsResponse = apiService.getPokemonByName(pokemon)
        return if(pokemonDetailsResponse.isSuccessful) {
            pokemonDetailsResponse.body()
        } else {
            when(pokemonDetailsResponse.code()){
                404 -> throw Exception("Pokemon not found.")
                else -> {
                    throw Exception("An error has ocurred.")
                }
            }
        }
    }

    suspend fun fetchSpeciesDetails(pokemon: String): SpeciesByNameResponse? {
        val speciesDetailsResponse = apiService.getSpeciesById(pokemon)
        return speciesDetailsResponse.body()
    }
}