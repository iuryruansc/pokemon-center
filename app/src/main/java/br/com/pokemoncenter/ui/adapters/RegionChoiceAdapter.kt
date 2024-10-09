package br.com.pokemoncenter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import br.com.pokemon_center.R
import br.com.pokemoncenter.commom.util.listeners.ChoiceListener
import br.com.pokemoncenter.data.models.Region

class RegionChoiceAdapter(private val regions: List<Region>) :
    RecyclerView.Adapter<RegionChoiceAdapter.RegionChoiceViewHolder>() {

    private var choiceListener: ChoiceListener? = null

    fun setChoiceListener(listener: ChoiceListener) {
        this.choiceListener = listener
    }

    class RegionChoiceViewHolder(itemView: View, choiceListener: ChoiceListener?) : RecyclerView.ViewHolder(itemView) {
        val button: AppCompatButton = itemView.findViewById(R.id.region_button)

        init {
            button.setOnClickListener {
                choiceListener?.onChoiceClick(itemView, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionChoiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_pokedex_choice_item, parent, false)
        return RegionChoiceViewHolder(view, choiceListener)
    }

    override fun getItemCount(): Int = regions.size

    override fun onBindViewHolder(holder: RegionChoiceViewHolder, position: Int) {
        val item = regions[position]
        holder.button.text = item.name
        holder.button.setBackgroundResource(item.imageResId)
        holder.button.contentDescription = item.altText
    }
}
