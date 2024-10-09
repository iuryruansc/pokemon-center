package br.com.pokemoncenter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.pokemon_center.R
import br.com.pokemoncenter.commom.util.hofs.textformat.capitalizedName
import br.com.pokemoncenter.commom.util.hofs.types.typeStyleBackground
import br.com.pokemoncenter.commom.util.listeners.PokemonNameListener
import br.com.pokemoncenter.data.models.Pokemon
import coil.load

class PokedexAdapter(private val pokedex: List<Pokemon>) :
    RecyclerView.Adapter<PokedexAdapter.PokedexViewHolder>() {

    private var choiceListener: PokemonNameListener? = null

    fun setChoiceListener(listener: PokemonNameListener) {
        this.choiceListener = listener
    }

    class PokedexViewHolder(view: View, choiceListener: PokemonNameListener?) : RecyclerView.ViewHolder(view) {
        val cardName: TextView = view.findViewById(R.id.pokedex_name)
        val cardSprite: ImageView = view.findViewById(R.id.pokedex_sprite)
        val cardContainer: ConstraintLayout = view.findViewById(R.id.pokedex_container)
        val type1: View = view.findViewById(R.id.pokedex_type_1)
        val type2: View = view.findViewById(R.id.pokedex_type_2)

        init {
            view.setOnClickListener {
                choiceListener?.onItemClick(itemView, bindingAdapterPosition, cardName.text)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_pokedex_item, parent, false)
        return PokedexViewHolder(view, choiceListener)
    }

    override fun getItemCount(): Int = pokedex.size

    override fun onBindViewHolder(holder: PokedexViewHolder, position: Int) {
        val item = pokedex[position]
        holder.cardName.text = capitalizedName(item.name)
        holder.cardSprite.load(item.image)

        if (item.type2 != "null") {
            typeStyleBackground(item.type1)?.let { holder.type1.setBackgroundResource(it) }
            typeStyleBackground(item.type2!!)?.let { holder.type2.setBackgroundResource(it) }
            holder.cardContainer.setBackgroundResource(0)
        } else {
            typeStyleBackground(item.type1)?.let { holder.cardContainer.setBackgroundResource(it) }
            holder.type1.setBackgroundResource(0)
            holder.type2.setBackgroundResource(0)
        }
    }
}
