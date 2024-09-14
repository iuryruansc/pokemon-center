package br.com.pokemoncenter.commom.util.listeners

import android.view.View

interface ActivityFavoritesListener {
    fun onItemClick(view: View, position: Int, pokemonName: CharSequence)
}