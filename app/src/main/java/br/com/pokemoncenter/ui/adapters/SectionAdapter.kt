package br.com.pokemoncenter.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.pokemon_center.R
import br.com.pokemoncenter.data.models.SectionItem
import coil.load

class SectionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<SectionItem> = mutableListOf()

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val header: TextView = itemView.findViewById(R.id.sectionHeader)
        private val expandIcon: ImageView = itemView.findViewById(R.id.expandIcon)

        fun bind(item: SectionItem.Header) {
            header.text = item.title
            expandIcon.setImageResource(if (item.isExpanded) R.drawable.collapse_icon else R.drawable.expand_icon)
            expandIcon.setOnClickListener {
                item.isExpanded = !item.isExpanded
                expandIcon.setImageResource(if (item.isExpanded) R.drawable.collapse_icon else R.drawable.expand_icon)
                val adapter = bindingAdapter as SectionAdapter
                adapter.notifyItemChanged(absoluteAdapterPosition)
            }
        }
    }

    class TextContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textContent: TextView = itemView.findViewById(R.id.text_info_generation)

        fun bind(item: SectionItem.TextContent) {
            textContent.text = item.text
        }
    }

    class CharacterContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val characterName: TextView = itemView.findViewById(R.id.generation_info_name)
        private val characterImage: ImageView = itemView.findViewById(R.id.generation_info_sprite)
        private val characterRole: TextView = itemView.findViewById(R.id.generation_info_role)

        fun bind(item: SectionItem.CharacterContent) {
            characterName.text = item.characterName
            characterImage.load(Uri.parse(item.characterImage))
            characterRole.text = item.characterRole
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder(
                inflater.inflate(
                    R.layout.activity_generations_section,
                    parent,
                    false
                )
            )

            VIEW_TYPE_TEXT_CONTENT -> TextContentViewHolder(
                inflater.inflate(
                    R.layout.generation_item_info,
                    parent,
                    false
                )
            )

            VIEW_TYPE_CHARACTER_CONTENT -> CharacterContentViewHolder(
                inflater.inflate(
                    R.layout.generation_item_character,
                    parent,
                    false
                )
            )

            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is SectionItem.Header -> (holder as HeaderViewHolder).bind(item)
            is SectionItem.TextContent -> (holder as TextContentViewHolder).bind(item)
            is SectionItem.CharacterContent -> (holder as CharacterContentViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is SectionItem.Header -> VIEW_TYPE_HEADER
            is SectionItem.TextContent -> VIEW_TYPE_TEXT_CONTENT
            is SectionItem.CharacterContent -> VIEW_TYPE_CHARACTER_CONTENT
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val gridLayoutManager = GridLayoutManager(recyclerView.context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (items[position]) {
                    is SectionItem.CharacterContent -> 1
                    else -> 3
                }
            }
        }
        recyclerView.layoutManager = gridLayoutManager
    }

    fun setItems(newItems: List<SectionItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_TEXT_CONTENT = 1
        private const val VIEW_TYPE_CHARACTER_CONTENT = 2
    }
}
