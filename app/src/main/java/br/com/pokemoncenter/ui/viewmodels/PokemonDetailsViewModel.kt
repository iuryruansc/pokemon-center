package br.com.pokemoncenter.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.pokemoncenter.data.repository.PokemonDetailsRepository
import br.com.pokemoncenter.local.db.AppDatabase
import br.com.pokemoncenter.local.entity.PokemonByNameEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val mPokemonDetailsRepository = PokemonDetailsRepository()
    private val database = AppDatabase.getInstance(application)
    private val pokemonDao = database.pokemonDao()

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
            val pokemonByName = pokemonDao.getPokemonByName(pokemon)
            if (pokemonByName != null) {
                _pokemonId.postValue(pokemonByName.id)
                _pokemonName.postValue(pokemonByName.name)
                _pokemonImage.postValue(pokemonByName.sprites.frontDefault)
                _pokemonType1.postValue(pokemonByName.types[0].type.name)
                if (pokemonByName.types.size > 1) {
                    _pokemonType2.postValue(pokemonByName.types[1].type.name)
                }
                _isLoading.value = false
            } else {
                val response = mPokemonDetailsRepository.getPokemonDetails(pokemon)
                if (response.success) {
                    val details = response.data
                    if (details != null) {
                        _pokemonId.postValue(details.id)
                        _pokemonName.postValue(details.name)
                        _pokemonImage.postValue(details.sprites.frontDefault)
                        _pokemonType1.postValue(details.types[0].type.name)
                        if (details.types.size > 1) {
                            _pokemonType2.postValue(details.types[1].type.name)
                        }
                        _isLoading.value = false

                        val pokemonEntity = PokemonByNameEntity(
                            id = details.id,
                            name = details.name,
                            sprites = details.sprites,
                            height = details.height,
                            weight = details.weight,
                            baseExperience = details.baseExperience,
                            abilities = details.abilities,
                            moves = details.moves,
                            stats = details.stats,
                            types = details.types,
                            cries = details.cries
                        )

                        pokemonDao.insertPokemon(pokemonEntity)
                    } else {
                        _isLoading.value = true
                        _errorMessage.postValue("Couldn't retrieve data, try again later")
                    }
                } else {
                    _isLoading.value = true
                    _errorMessage.postValue(response.message)
                }
            }
        }
    }
}
