package br.com.pokemoncenter.data.network

import br.com.pokemoncenter.commom.Constants
import br.com.pokemoncenter.commom.DataNotFoundException
import br.com.pokemoncenter.commom.DataParseException
import br.com.pokemoncenter.data.api.ApiService
import br.com.pokemoncenter.data.api.ApiServiceClient
import br.com.pokemoncenter.data.api.models.MoveDetailsResponse
import br.com.pokemoncenter.data.api.models.PokemonByNameResponse
import br.com.pokemoncenter.data.api.models.SpeciesByNameResponse
import java.io.IOException

class PokemonDetailsDataSource(private val apiService: ApiService = ApiServiceClient.instance) {

    suspend fun fetchPokemonDetails(pokemon: String): PokemonByNameResponse? {
        val pokemonDetailsResponse = apiService.getPokemonByName(pokemon)
        if (pokemonDetailsResponse.isSuccessful) {
            return pokemonDetailsResponse.body()
        }
        when (pokemonDetailsResponse.code()) {
            Constants.ERROR_400 -> throw DataParseException("Bad Request")
            Constants.ERROR_404 -> throw DataNotFoundException("Pokemon not found.")
            else -> throw IOException("HTTP Error: ${pokemonDetailsResponse.code()}")
        }
    }

    suspend fun fetchSpeciesDetails(pokemon: String): SpeciesByNameResponse? {
        val speciesDetailsResponse = apiService.getSpeciesById(pokemon)
        if (speciesDetailsResponse.isSuccessful) {
            return speciesDetailsResponse.body()
        }
        when (speciesDetailsResponse.code()) {
            Constants.ERROR_400 -> throw DataParseException("Bad Request")
            Constants.ERROR_404 -> throw DataNotFoundException("Species not found.")
            else -> throw IOException("HTTP Error: ${speciesDetailsResponse.code()}")
        }
    }

    suspend fun fetchMoveDetails(name: String): MoveDetailsResponse? {
        val moveDetailsResponse = apiService.getMoveByName(name)
        if (moveDetailsResponse.isSuccessful) {
            return moveDetailsResponse.body()
        }
        when (moveDetailsResponse.code()) {
            Constants.ERROR_400 -> throw DataParseException("Bad Request")
            Constants.ERROR_404 -> throw DataNotFoundException("Move not found.")
            else -> throw IOException("HTTP Error: ${moveDetailsResponse.code()}")
        }
    }
}
