package br.com.pokemoncenter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.pokemon_center.R
import br.com.pokemoncenter.commom.TypeEffectiveness
import br.com.pokemoncenter.commom.util.hofs.types.typeStyle
import br.com.pokemoncenter.commom.util.listeners.FragmentEffectListener
import java.util.Locale

class TypeEffectivenessAdapter(private val typeEffectiveness: Map<String, Double>) :
    RecyclerView.Adapter<TypeEffectivenessAdapter.TypeViewHolder>() {

    private var effectListener: FragmentEffectListener? = null

    fun setEffectListener(listener: FragmentEffectListener) {
        this.effectListener = listener
    }

    class TypeViewHolder(itemView: View, effectListener: FragmentEffectListener?) :
        RecyclerView.ViewHolder(itemView) {
        val tvType: ImageView = itemView.findViewById(R.id.tvType)
        val tvEffectiveness: TextView = itemView.findViewById(R.id.tvEffectiveness)
        val tvEffectBar: View = itemView.findViewById(R.id.effectivenessBar)

        init {
            itemView.setOnClickListener {
                effectListener?.onItemClick(itemView, bindingAdapterPosition, tvEffectiveness.text)
            }
        }
    }

    override fun getItemCount(): Int = typeEffectiveness.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_effectiveness_item, parent, false)

        return TypeViewHolder(view, effectListener)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val type = typeEffectiveness.keys.elementAt(position)

        holder.tvType.setImageResource(typeStyle(type)!!)

        when (
            val effectiveness =
                TypeEffectiveness.fromValue(typeEffectiveness[type] ?: 1.0)
        ) {
            TypeEffectiveness.QUADRUPLE_EFFECTIVE -> {
                holder.tvEffectBar.setBackgroundResource(effectiveness.colorResId)
                holder.tvEffectiveness.text =
                    String.format(Locale.ROOT, effectiveness.format, effectiveness.value)
            }

            TypeEffectiveness.DOUBLE_EFFECTIVE -> {
                holder.tvEffectBar.setBackgroundResource(effectiveness.colorResId)
                holder.tvEffectiveness.text =
                    String.format(Locale.ROOT, effectiveness.format, effectiveness.value)
            }

            TypeEffectiveness.DOUBLE_RESIST -> {
                holder.tvEffectBar.setBackgroundResource(effectiveness.colorResId)
                holder.tvEffectiveness.text =
                    String.format(Locale.ROOT, effectiveness.format, effectiveness.value)
            }

            TypeEffectiveness.QUADRUPLE_RESIST -> {
                holder.tvEffectBar.setBackgroundResource(effectiveness.colorResId)
                holder.tvEffectiveness.text =
                    String.format(Locale.ROOT, effectiveness.format, effectiveness.value)
            }

            TypeEffectiveness.NO_EFFECT -> {
                holder.tvEffectBar.setBackgroundResource(effectiveness.colorResId)
                holder.tvEffectiveness.text =
                    String.format(Locale.ROOT, effectiveness.format, effectiveness.value)
            }

            null -> TODO()
        }
    }
}
