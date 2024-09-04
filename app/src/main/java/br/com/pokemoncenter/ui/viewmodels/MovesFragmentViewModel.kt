package br.com.pokemoncenter.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Moves
import br.com.pokemoncenter.data.repository.PokemonDetailsRepository
import br.com.pokemoncenter.local.db.AppDatabase
import br.com.pokemoncenter.local.entity.MoveEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MovesFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val mPokemonDetailsRepository = PokemonDetailsRepository()
    private val database = AppDatabase.getInstance(application)
    private val pokemonDao = database.pokemonDao()

    private var _finalMovesList = MutableLiveData<List<Moves>>()
    val finalMovesList: LiveData<List<Moves>>
        get() = _finalMovesList


    fun pokemonMoves(name: String) {
        viewModelScope.launch {
            val pokemon = pokemonDao.getPokemonByName(name)
            val allMovesLocal = pokemonDao.getAllMoves()
            val pokemonMoves = pokemon!!.moves

            for (moves in pokemonMoves) {
                val moveName = moves.move.name
                val existingMove = allMovesLocal.find { it.name == moveName }
                if (existingMove == null) {
                    val response = mPokemonDetailsRepository.getMoveDetails(moveName)
                    if (response.success) {
                        val moveDetails = response.data
                        if (moveDetails != null) {
                            val moveEntity = MoveEntity(
                                name = moveDetails.name,
                                accuracy = moveDetails.accuracy,
                                power = moveDetails.power,
                                pp = moveDetails.pp,
                                type = moveDetails.type,
                                damageClass = moveDetails.damageClass,
                                effectEntries = moveDetails.effectEntries,
                                flavorTextEntries = moveDetails.flavorTextEntries,
                                target = moveDetails.target
                            )
                            pokemonDao.insertMove(moveEntity)
                        } else {
                            // TODO handle null
                        }
                    } else {
                        // TODO handle error
                    }
                }
            }
            _finalMovesList.postValue(pokemonMoves)
        }
    }
}
