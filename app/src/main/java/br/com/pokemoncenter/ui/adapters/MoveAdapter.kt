package br.com.pokemoncenter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.pokemon_center.R
import br.com.pokemoncenter.commom.util.hofs.types.getTypeResources
import br.com.pokemoncenter.data.api.models.pokemonbynamedata.Moves
import br.com.pokemoncenter.local.dao.PokemonDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MoveAdapter(private val moveList: List<Moves>, private val moveDao: PokemonDao) :
    RecyclerView.Adapter<MoveAdapter.MoveViewHolder>() {

    class MoveViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moveName: TextView = view.findViewById(R.id.move_name)
        val moveType: ImageView = view.findViewById(R.id.move_type)
        val movePP: TextView = view.findViewById(R.id.move_pp)
        val layout: LinearLayout = view.findViewById(R.id.move_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_moves_item, parent, false)
        val cardView = view.findViewById<LinearLayout>(R.id.move_container)
        val desiredWidth = 425
        val desiredHeight = 125

        cardView.layoutParams.width = desiredWidth
        cardView.layoutParams.height = desiredHeight

        return MoveViewHolder(view)
    }

    override fun getItemCount(): Int = moveList.size

    override fun onBindViewHolder(holder: MoveViewHolder, position: Int) {
        val moveName = moveList[position].move.name
        holder.moveName.text = moveName.uppercase(Locale.ROOT)

        CoroutineScope(Dispatchers.IO).launch {
            val allMoves = moveDao.getAllMoves()
            withContext(Dispatchers.Main) {
                val moveDetail = allMoves.find { it.name == moveName }
                if (moveDetail != null) {
                    holder.movePP.text = String.format(Locale.ROOT,"PP: %d", moveDetail.pp)
                    val type = moveDetail.type.name

                    val typeResource = getTypeResources(type)
                    if (typeResource != null) {
                        holder.moveType.setImageResource(typeResource.first)
                        holder.layout.setBackgroundResource(typeResource.second)
                    }
                }
            }
        }
    }
}
