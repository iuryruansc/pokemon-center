package br.com.pokemoncenter.commom.util.hofs.textformat

internal fun capitalizedName(string: String): String {
    val capitalizedName = string.replaceFirstChar { it.uppercase() }
    return capitalizedName
}
