package br.com.pokemoncenter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.pokemon_center.R
import br.com.pokemoncenter.commom.util.listeners.ActivityFavoritesListener
import br.com.pokemoncenter.local.entity.FavoritesEntity
import coil.load

class PokemonCardAdapter : ListAdapter<FavoritesEntity, PokemonCardAdapter.PokemonCardViewHolder>(DiffCallback()) {

    private var cardListener: ActivityFavoritesListener? = null

    fun setCardListener(listener: ActivityFavoritesListener) {
        this.cardListener = listener
    }

    class PokemonCardViewHolder(view: View, cardListener: ActivityFavoritesListener?) :
        RecyclerView.ViewHolder(view) {
        val cardName: TextView = view.findViewById(R.id.card_name)
        val cardSprite: ImageView = view.findViewById(R.id.card_sprite)
        val cardContainer: ConstraintLayout = view.findViewById(R.id.card_container)
        val type1: View = view.findViewById(R.id.type_1)
        val type2: View = view.findViewById(R.id.type_2)

        init {
            view.setOnClickListener {
                cardListener?.onItemClick(view, bindingAdapterPosition, cardName.text)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_favorites_item, parent, false)
        return PokemonCardViewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: PokemonCardViewHolder, position: Int) {
        val currentCard = getItem(position)
        holder.cardName.text = currentCard.name
        holder.cardSprite.load(currentCard.sprite)
        if (currentCard.backgroundTwo != null) {
            holder.type1.setBackgroundResource(currentCard.backgroundOne!!)
            holder.type2.setBackgroundResource(currentCard.backgroundTwo)
        } else {
            holder.cardContainer.setBackgroundResource(currentCard.backgroundOne!!)
        }
    }
}

class DiffCallback : ItemCallback<FavoritesEntity>() {
    override fun areItemsTheSame(oldItem: FavoritesEntity, newItem: FavoritesEntity): Boolean {
        return oldItem.name == newItem.name // Use a unique identifier to compare items
    }

    override fun areContentsTheSame(oldItem: FavoritesEntity, newItem: FavoritesEntity): Boolean {
        return oldItem == newItem // Check if the content of the items is the same
    }
}
