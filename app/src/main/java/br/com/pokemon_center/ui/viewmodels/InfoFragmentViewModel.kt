package br.com.pokemon_center.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.pokemon_center.commom.util.formatHeight
import br.com.pokemon_center.commom.util.formatWeight
import br.com.pokemon_center.data.repository.PokemonDetailsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoFragmentViewModel : ViewModel() {

    private val mPokemonDetailsRepository = PokemonDetailsRepository()

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

    private var _cries = MutableLiveData<String>()
    val cries: LiveData<String> get() = _cries

    fun pokemonSpecies(pokemon: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mPokemonDetailsRepository.getPokemonSpecies(pokemon)
            if (response.success) {
                val species = response.data!!
                val englishDescription = species.flavorTextEntries.find { it.language.name == "en" }
                val formattedDescription = englishDescription!!.flavorText.replace("\n", " ")
                _pokemonDescription.postValue(formattedDescription)
                _captureRate.postValue(species.captureRate.toString())
                _genera.postValue(species.genera.find { it.language.name == "en" }!!.genus)
                _generationDebut.postValue(species.generation.name)
            }
        }
    }

    fun pokemonDetails(pokemon: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mPokemonDetailsRepository.getPokemonDetails(pokemon)
            if (response.success) {
                val details = response.data!!
                _pokemonWeight.postValue(formatWeight(details.weight))
                _pokemonHeight.postValue(formatHeight(details.height))
                _baseExperience.postValue(details.baseExperience.toString())
                _cries.postValue(details.cries.latest)
            }
        }
    }
}
