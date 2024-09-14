package br.com.pokemoncenter.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.pokemoncenter.commom.util.hofs.textformat.capitalizedName
import br.com.pokemoncenter.commom.util.hofs.types.typeStyleBackground
import br.com.pokemoncenter.data.api.models.PokemonByNameResponse
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Types
import br.com.pokemoncenter.data.repository.PokemonDetailsRepository
import br.com.pokemoncenter.local.db.AppDatabase
import br.com.pokemoncenter.local.entity.FavoritesEntity
import br.com.pokemoncenter.local.entity.PokemonByNameEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val mPokemonDetailsRepository = PokemonDetailsRepository()
    private val database = AppDatabase.getInstance(application)
    private val pokemonDao = database.pokemonDao()

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private var _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private var _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite

    private var _pokemon = MutableStateFlow<PokemonByNameResponse?>(null)
    val pokemon: StateFlow<PokemonByNameResponse?> get() = _pokemon

    fun pokemonDetails(pokemon: String) {
        viewModelScope.launch {
            val pokemonByName = pokemonDao.getPokemonByName(pokemon)
            if (pokemonByName != null) {
                _isLoading.value = false
                _pokemon.value = pokemonByName.toPokemonByNameResponse()
            } else {
                val response = mPokemonDetailsRepository.getPokemonDetails(pokemon)
                if (response.success) {
                    val details = response.data
                    if (details != null) {
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
                        _pokemon.value = details
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

    fun pokemonToFavorite(pokemon: String) {
        viewModelScope.launch {
            val pokemonDetails = pokemonDao.getPokemonByName(pokemon)!!
            val favorite = pokemonDao.getFavorite(capitalizedName(pokemon))
            val types = pokemonDetails.types

            if (favorite == null) {
                val backgroundColor = getBackground(types)

                val newFavorite = FavoritesEntity(
                    name = capitalizedName(pokemonDetails.name),
                    sprite = pokemonDetails.sprites.frontDefault,
                    backgroundOne = backgroundColor[0],
                    backgroundTwo = backgroundColor[1]
                )
                pokemonDao.insertFavorite(newFavorite)
                _isFavorite.value = true
            } else {
                pokemonDao.deleteFavorite(favorite)
                _isFavorite.value = false
            }
        }
    }

    suspend fun alreadyFavorite(pokemonName: String): Boolean {
        val exists = pokemonDao.getFavorite(capitalizedName(pokemonName))
        return exists != null
    }

    private fun getBackground(types: List<Types>): List<Int?> {
        val background = mutableListOf<Int?>()
        if (types.size > 1) {
            background.add(typeStyleBackground(types[0].type.name))
            background.add(typeStyleBackground(types[1].type.name))
        } else {
            background.add(typeStyleBackground(types[0].type.name))
            background.add(null)
        }
        return background
    }

    private fun PokemonByNameEntity.toPokemonByNameResponse(): PokemonByNameResponse {
        return PokemonByNameResponse(
            id = id,
            name = name,
            sprites = sprites,
            height = height,
            weight = weight,
            baseExperience = baseExperience,
            abilities = abilities,
            moves = moves,
            stats = stats,
            types = types,
            cries = cries
        )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
