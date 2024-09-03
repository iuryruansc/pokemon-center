package br.com.pokemoncenter.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Stats
import br.com.pokemoncenter.local.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    private val pokemonDao = database.pokemonDao()

    private var _stats = MutableLiveData<List<Stats>>()
    val stats: LiveData<List<Stats>> get() = _stats

    fun pokemonStats(pokemon: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val pokemonStats = pokemonDao.getPokemonByName(pokemon)
            _stats.postValue(pokemonStats!!.stats)
        }
    }
}
