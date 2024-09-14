package br.com.pokemoncenter.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.pokemoncenter.local.db.AppDatabase
import br.com.pokemoncenter.local.entity.FavoritesEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val _favoritesList = MutableLiveData<List<FavoritesEntity?>>()
    val favoritesList: LiveData<List<FavoritesEntity?>> get() = _favoritesList

    private val database = AppDatabase.getInstance(application)
    private val pokemonDao = database.pokemonDao()

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    fun getFavorites() {
        viewModelScope.launch {
            val favorites = pokemonDao.getAllFavorites()
            _favoritesList.postValue(favorites)
        }

    }
}