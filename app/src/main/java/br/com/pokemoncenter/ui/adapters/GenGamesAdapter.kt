package br.com.pokemoncenter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.pokemon_center.R
import br.com.pokemoncenter.commom.util.listeners.ChoiceListener

class GenGamesAdapter(private val games: IntArray) :
    RecyclerView.Adapter<GenGamesAdapter.GenGamesViewHolder>() {

    private var choiceListener: ChoiceListener? = null

    fun setChoiceListener(listener: ChoiceListener) {
        this.choiceListener = listener
    }

    class GenGamesViewHolder(view: View, choiceListener: ChoiceListener?) :
        RecyclerView.ViewHolder(view) {
        val game: ImageView = view.findViewById(R.id.gen_game)

        init {
            game.setOnClickListener {
                choiceListener?.onChoiceClick(it, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenGamesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_gen_games_item, parent, false)
        return GenGamesViewHolder(view, choiceListener)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: GenGamesViewHolder, position: Int) {
        holder.game.setImageResource(games[position])
    }
}