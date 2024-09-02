package br.com.pokemon_center.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.pokemon_center.data.repository.PokemonDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonDetailsViewModel: ViewModel() {

    private val mPokemonDetailsRepository = PokemonDetailsRepository()

    private var _pokemonId = MutableLiveData<Int>()
    val pokemonId: LiveData<Int> get() = _pokemonId

    private var _pokemonName = MutableLiveData<String>()
    val pokemonName: LiveData<String> get() = _pokemonName

    private var _pokemonImage = MutableLiveData<String>()
    val pokemonImage: LiveData<String> get() = _pokemonImage

    private var _pokemonType1 = MutableLiveData<String>()
    val pokemonType1: LiveData<String> get() = _pokemonType1

    private var _pokemonType2 = MutableLiveData<String>()
    val pokemonType2: LiveData<String> get() = _pokemonType2

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun pokemonDetails(pokemon: String) {
        viewModelScope.launch {
            val response = mPokemonDetailsRepository.getPokemonDetails(pokemon)
            if (response.success) {
                val details = response.data!!
                _pokemonId.postValue(details.id)
                _pokemonName.postValue(details.name)
                _pokemonImage.postValue(details.sprites.frontDefault)
                _pokemonType1.postValue(details.types[0].type.name)
                if (details.types.size > 1) {
                    _pokemonType2.postValue(details.types[1].type.name)
                }
                _isLoading.value = false
            } else {
                _isLoading.value = true
                _errorMessage.postValue(response.message)
            }
        }
    }
}