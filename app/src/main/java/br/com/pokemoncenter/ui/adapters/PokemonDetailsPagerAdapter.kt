package br.com.pokemoncenter.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.pokemoncenter.commom.TabIndex
import br.com.pokemoncenter.ui.fragments.EffectivenessFragment
import br.com.pokemoncenter.ui.fragments.InfoFragment
import br.com.pokemoncenter.ui.fragments.MovesFragment
import br.com.pokemoncenter.ui.fragments.StatsFragment

class PokemonDetailsPagerAdapter(
    fa: FragmentActivity,
    private val pokemonName: String,
    private val type1: String,
    private val type2: String?
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragmentArgs = Bundle().apply {
            putString("pokemonName", pokemonName)
            putString("type1", type1)
            putString("type2", type2)
        }
        return when (position) {
            TabIndex.INFO.ordinal -> InfoFragment().apply { arguments = fragmentArgs }
            TabIndex.STATS.ordinal -> StatsFragment().apply { arguments = fragmentArgs }
            TabIndex.EFFECTIVENESS.ordinal -> EffectivenessFragment().apply { arguments = fragmentArgs }
            TabIndex.MOVES.ordinal -> MovesFragment().apply { arguments = fragmentArgs }
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
