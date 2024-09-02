package br.com.pokemon_center.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.pokemon_center.data.api.models.pokemonbynamedata.Stats
import br.com.pokemon_center.data.repository.PokemonDetailsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatsFragmentViewModel : ViewModel() {

    private var _stats = MutableLiveData<List<Stats>>()
    val stats: LiveData<List<Stats>> get() = _stats

    private val mPokemonDetailsRepository = PokemonDetailsRepository()

    fun pokemonStats(pokemon: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mPokemonDetailsRepository.getPokemonDetails(pokemon)
            if (response.success) {
                val result = response.data!!
                _stats.postValue(result.stats)
            }
        }
    }
}