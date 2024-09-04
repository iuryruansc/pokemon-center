package br.com.pokemoncenter.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.pokemoncenter.commom.util.hofs.textformat.findDescription
import br.com.pokemoncenter.commom.util.hofs.textformat.formatHeight
import br.com.pokemoncenter.commom.util.hofs.textformat.formatWeight
import br.com.pokemoncenter.data.repository.PokemonDetailsRepository
import br.com.pokemoncenter.local.db.AppDatabase
import br.com.pokemoncenter.local.entity.SpeciesByNameEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class InfoFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val mPokemonDetailsRepository = PokemonDetailsRepository()
    private val database = AppDatabase.getInstance(application)
    private val pokemonDao = database.pokemonDao()

    private var _pokemonDescription = MutableLiveData<String>()
    val pokemonDescription: LiveData<String> get() = _pokemonDescription

    private var _pokemonWeight = MutableLiveData<String>()
    val pokemonWeight: LiveData<String> get() = _pokemonWeight

    private var _pokemonHeight = MutableLiveData<String>()
    val pokemonHeight: LiveData<String> get() = _pokemonHeight

    private var _captureRate = MutableLiveData<String>()
    val captureRate: LiveData<String> get() = _captureRate

    private var _baseExperience = MutableLiveData<String>()
    val baseExperience: LiveData<String> get() = _baseExperience

    private var _genera = MutableLiveData<String>()
    val genera: LiveData<String> get() = _genera

    private var _generationDebut = MutableLiveData<String>()
    val generationDebut: LiveData<String> get() = _generationDebut

    fun pokemonSpecies(pokemon: String) {
        viewModelScope.launch {
            val pokemonByName = pokemonDao.getSpeciesByName(pokemon)
            if (pokemonByName != null) {
                val formattedDescription = findDescription(pokemonByName.flavorTextEntries)
                _pokemonDescription.postValue(formattedDescription)
                _captureRate.postValue(pokemonByName.captureRate.toString())
                _genera.postValue(pokemonByName.genera.find { it.language.name == "en" }!!.genus)
                _generationDebut.postValue(pokemonByName.generation.name)
            } else {
                val response = mPokemonDetailsRepository.getPokemonSpecies(pokemon)
                if (response.success) {
                    val species = response.data
                    if (species != null) {
                        val formattedDescription = findDescription(species.flavorTextEntries)
                        _pokemonDescription.postValue(formattedDescription)
                        _captureRate.postValue(species.captureRate.toString())
                        _genera.postValue(species.genera.find { it.language.name == "en" }!!.genus)
                        _generationDebut.postValue(species.generation.name)

                        val speciesEntity = SpeciesByNameEntity(
                            pokemonId = species.id,
                            pokemonName = species.name,
                            evolvesFromSpecies = species.evolvesFromSpecies,
                            generation = species.generation,
                            captureRate = species.captureRate,
                            flavorTextEntries = species.flavorTextEntries,
                            genera = species.genera
                        )

                        pokemonDao.insertSpecies(speciesEntity)
                    } else {
                        // TODO handle null
                    }
                } else {
                    // TODO handle error
                }
            }
        }
    }

    fun pokemonDetails(pokemon: String) {
        viewModelScope.launch {
            val pokemonByName = pokemonDao.getPokemonByName(pokemon)
            if (pokemonByName != null) {
                _pokemonWeight.postValue(formatWeight(pokemonByName.weight))
                _pokemonHeight.postValue(formatHeight(pokemonByName.height))
                _baseExperience.postValue(pokemonByName.baseExperience.toString())
            } else {
                val response = mPokemonDetailsRepository.getPokemonDetails(pokemon)
                if (response.success) {
                    val pokemonDetails = response.data
                    if (pokemonDetails != null) {
                        _pokemonWeight.postValue(formatWeight(pokemonDetails.weight))
                        _pokemonHeight.postValue(formatHeight(pokemonDetails.height))
                        _baseExperience.postValue(pokemonDetails.baseExperience.toString())
                    } else {
                        // TODO handle null
                    }
                } else {
                    // TODO handle error
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
