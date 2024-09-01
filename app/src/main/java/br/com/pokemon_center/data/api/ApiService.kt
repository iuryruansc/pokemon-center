package br.com.pokemon_center.data.api

import br.com.pokemon_center.data.api.models.PokemonByNameResponse
import br.com.pokemon_center.data.api.models.SpeciesByNameResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name:String): Response<PokemonByNameResponse>

    @GET("pokemon-species/{name}")
    suspend fun getSpeciesById(@Path("name") name: String): Response<SpeciesByNameResponse>
}