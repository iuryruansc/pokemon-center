package br.com.pokemoncenter.data.models

sealed class SectionItem {
    data class Header(
        val title: String,
        var isExpanded: Boolean = false
    ) : SectionItem()
    data class TextContent(val text: String) : SectionItem()
    data class CharacterContent(
        val characterName: String,
        val characterImage: String,
        val characterRole: String
    ) : SectionItem()
}
