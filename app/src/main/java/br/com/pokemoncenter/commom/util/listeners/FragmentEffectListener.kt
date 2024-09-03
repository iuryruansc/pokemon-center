package br.com.pokemoncenter.commom.util.listeners

import android.view.View

interface FragmentEffectListener {
    fun onItemClick(view: View, position: Int, mult: CharSequence)
}
