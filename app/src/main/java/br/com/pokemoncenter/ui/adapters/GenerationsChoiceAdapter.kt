package br.com.pokemoncenter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import br.com.pokemon_center.R
import br.com.pokemoncenter.commom.util.listeners.GenerationChoiceListener
import br.com.pokemoncenter.data.models.ButtonData

class GenerationsChoiceAdapter(private val buttonDataList: List<ButtonData>) :
    RecyclerView.Adapter<GenerationsChoiceAdapter.GenChoiceViewHolder>() {

    private var choiceListener: GenerationChoiceListener? = null

    fun setChoiceListener(listener: GenerationChoiceListener) {
        this.choiceListener = listener
    }

    class GenChoiceViewHolder(itemView: View, choiceListener: GenerationChoiceListener?) :
        RecyclerView.ViewHolder(itemView) {
        val generationChoiceButton: Button = itemView.findViewById(R.id.gen_choice_button)

        init {
            generationChoiceButton.setOnClickListener {
                choiceListener?.onChoiceClick(itemView, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenChoiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.generations_choice_item, parent, false)
        return GenChoiceViewHolder(view, choiceListener)
    }

    override fun getItemCount(): Int = buttonDataList.size

    override fun onBindViewHolder(holder: GenChoiceViewHolder, position: Int) {
        val buttonData = buttonDataList[position]
        holder.generationChoiceButton.text = buttonData.text
        holder.generationChoiceButton.setBackgroundResource(buttonData.background)
    }
}
