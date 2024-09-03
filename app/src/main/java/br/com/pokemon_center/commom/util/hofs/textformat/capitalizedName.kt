package br.com.pokemon_center.commom.util.hofs.textformat

internal fun capitalizedName(string: String) : String {
    val capitalizedName = string.replaceFirstChar { it.uppercase() }
    return capitalizedName
}
