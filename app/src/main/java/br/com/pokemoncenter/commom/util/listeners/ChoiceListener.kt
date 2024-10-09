package br.com.pokemoncenter.commom.util.listeners

import android.view.View

interface ChoiceListener {
    fun onChoiceClick(view: View, position: Int)
}