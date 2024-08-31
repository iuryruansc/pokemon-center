package br.com.pokemon_center.data.network

import br.com.pokemon_center.data.api.ApiService
import br.com.pokemon_center.data.api.ApiServiceClient
import br.com.pokemon_center.data.api.models.PokemonByNameResponse

class PokemonDetailsDataSource(private val apiService: ApiService = ApiServiceClient.instance) {

    suspend fun fetchPokemonDetails(pokemon: String): PokemonByNameResponse? {
        val pokemonDetailsResponse = apiService.getPokemonByName(pokemon)
        return pokemonDetailsResponse.body()
    }
}