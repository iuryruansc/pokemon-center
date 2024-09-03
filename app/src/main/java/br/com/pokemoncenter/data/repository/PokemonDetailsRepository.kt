package br.com.pokemoncenter.data.repository

import br.com.pokemoncenter.commom.util.testing.EspressoIdlingResourceHelper
import br.com.pokemoncenter.commom.util.testing.IdlingResourceHelper
import br.com.pokemoncenter.data.api.models.PokemonByNameResponse
import br.com.pokemoncenter.data.api.models.SpeciesByNameResponse
import br.com.pokemoncenter.data.network.PokemonDetailsDataSource

class PokemonDetailsRepository(
    private val mPokemonDetailsDataSource: PokemonDetailsDataSource = PokemonDetailsDataSource(),
    private val mIdlingResource: IdlingResourceHelper = EspressoIdlingResourceHelper()
) {

    suspend fun getPokemonDetails(pokemon: String): PokemonReturn<PokemonByNameResponse> {
        try {
            mIdlingResource.increment()
            val result = mPokemonDetailsDataSource.fetchPokemonDetails(pokemon)
            if (result != null) {
                return PokemonReturn(true, "", result)
            }
        } catch (ex: Exception) {
            return PokemonReturn(false, ex.message.orEmpty(), null)
        } finally {
            mIdlingResource.decrement()
        }
        return PokemonReturn(false, "Unknown error", null)
    }

    suspend fun getPokemonSpecies(pokemon: String): PokemonReturn<SpeciesByNameResponse> {
        try {
            mIdlingResource.increment()
            val result = mPokemonDetailsDataSource.fetchSpeciesDetails(pokemon)
            if (result != null) {
                return PokemonReturn(true, "", result)
            }
        } catch (ex: Exception) {
            return PokemonReturn(false, ex.message.orEmpty(), null)
        } finally {
            mIdlingResource.decrement()
        }
        return PokemonReturn(false, "Unknown error", null)
    }
}