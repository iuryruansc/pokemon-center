package br.com.pokemon_center.data.api

import br.com.pokemon_center.data.api.models.PokemonByName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonByName(@Path("name") name:String): Response<PokemonByName>

}