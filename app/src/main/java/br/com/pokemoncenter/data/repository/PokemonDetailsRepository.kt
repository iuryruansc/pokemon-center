package br.com.pokemoncenter.data.repository

import br.com.pokemoncenter.commom.DataNotFoundException
import br.com.pokemoncenter.commom.DataParseException
import br.com.pokemoncenter.commom.util.testing.EspressoIdlingResourceHelper
import br.com.pokemoncenter.commom.util.testing.IdlingResourceHelper
import br.com.pokemoncenter.data.api.models.MoveDetailsResponse
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
            } else {
                // Handle the case where the result is null
            }
        } catch (e: DataNotFoundException) {
            return PokemonReturn(false, e.message.orEmpty(), null)
        } catch (e: DataParseException) {
            return PokemonReturn(false, e.message.orEmpty(), null)
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
            } else {
                // Handle the case where the result is null
            }
        } catch (e: DataNotFoundException) {
            return PokemonReturn(false, e.message.orEmpty(), null)
        } catch (e: DataParseException) {
            return PokemonReturn(false, e.message.orEmpty(), null)
        } catch (ex: Exception) {
            return PokemonReturn(false, ex.message.orEmpty(), null)
        } finally {
            mIdlingResource.decrement()
        }
        return PokemonReturn(false, "Unknown error", null)
    }

    suspend fun getMoveDetails(name: String): PokemonReturn<MoveDetailsResponse> {
        try {
            mIdlingResource.increment()
            val result = mPokemonDetailsDataSource.fetchMoveDetails(name)
            if (result != null) {
                return PokemonReturn(true, "", result)
            } else {
                // Handle the case where the result is null
            }
        } catch (e: DataNotFoundException) {
            return PokemonReturn(false, e.message.orEmpty(), null)
        } catch (e: DataParseException) {
            return PokemonReturn(false, e.message.orEmpty(), null)
        } catch (ex: Exception) {
            return PokemonReturn(false, ex.message.orEmpty(), null)
        } finally {
            mIdlingResource.decrement()
        }
        return PokemonReturn(false, "Unknown error", null)
    }
}
