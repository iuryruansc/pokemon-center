package br.com.pokemoncenter.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Stats
import br.com.pokemoncenter.local.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class StatsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val database = AppDatabase.getInstance(application)
    private val pokemonDao = database.pokemonDao()

    private var _stats = MutableLiveData<List<Stats>>()
    val stats: LiveData<List<Stats>> get() = _stats

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun pokemonStats(pokemon: String) {
        viewModelScope.launch {
            val pokemonStats = pokemonDao.getPokemonByName(pokemon)
            if (pokemonStats != null) {
                _stats.postValue(pokemonStats.stats)
            } else {
                _errorMessage.postValue("Couldn't retrieve data, try again later")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
