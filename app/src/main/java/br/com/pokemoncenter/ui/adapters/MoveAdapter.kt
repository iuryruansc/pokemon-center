package br.com.pokemoncenter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.pokemon_center.R
import br.com.pokemoncenter.data.models.MoveObject
import coil.load
import java.util.Locale

class MoveAdapter(private val moveList: List<MoveObject>) :
    RecyclerView.Adapter<MoveAdapter.MoveViewHolder>() {

    class MoveViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moveName: TextView = view.findViewById(R.id.move_name)
        val moveType: ImageView = view.findViewById(R.id.move_type)
        val movePP: TextView = view.findViewById(R.id.move_pp)
        val layout: ConstraintLayout = view.findViewById(R.id.move_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_moves_item, parent, false)

        return MoveViewHolder(view)
    }

    override fun getItemCount(): Int = moveList.size

    override fun onBindViewHolder(holder: MoveViewHolder, position: Int) {
        val currentMove = moveList[position]
        holder.moveName.text = currentMove.name.uppercase(Locale.ROOT)
        holder.movePP.text = String.format(Locale.ROOT, "PP: %d", currentMove.pp)
        holder.moveType.load(currentMove.typeImage)
        holder.layout.setBackgroundResource(currentMove.typeBackground)
    }
}
