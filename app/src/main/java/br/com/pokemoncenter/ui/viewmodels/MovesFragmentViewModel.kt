package br.com.pokemoncenter.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.pokemon_center.R
import br.com.pokemoncenter.commom.util.hofs.types.typeStyle
import br.com.pokemoncenter.commom.util.hofs.types.typeStyleBackground
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Moves
import br.com.pokemoncenter.data.repository.PokemonDetailsRepository
import br.com.pokemoncenter.local.db.AppDatabase
import br.com.pokemoncenter.local.entity.MoveEntity
import br.com.pokemoncenter.ui.adapters.MoveObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovesFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val mPokemonDetailsRepository = PokemonDetailsRepository()
    private val database = AppDatabase.getInstance(application)
    private val pokemonDao = database.pokemonDao()

    private val _moveObjects = MutableStateFlow<List<MoveObject>>(emptyList())
    val moveObjects: StateFlow<List<MoveObject>>
        get() = _moveObjects

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
            fetchMoveObjects(pokemonMoves)
        }
    }

    private fun fetchMoveObjects(moveList: List<Moves>) {
        viewModelScope.launch {
            val allMoves = pokemonDao.getAllMoves()
            val moveDetailsMap = allMoves.associateBy { it.name }

            val moveObjectsList = moveList.map { move ->
                val moveDetail = moveDetailsMap[move.move.name]
                if (moveDetail != null) {
                    val typeImage = typeStyle(moveDetail.type.name)
                    val typeBackground = typeStyleBackground(moveDetail.type.name)
                    MoveObject(
                        name = moveDetail.name,
                        pp = moveDetail.pp,
                        type = moveDetail.type,
                        typeImage = typeImage!!,
                        typeBackground = typeBackground!!

                    )
                } else {
                    MoveObject(
                        name = move.move.name,
                        pp = 0,
                        type = null,
                        typeImage = R.drawable.type_unknown,
                        typeBackground = R.drawable.type_unknown_background
                    )
                }
            }
            _moveObjects.value = moveObjectsList
        }
    }
}
