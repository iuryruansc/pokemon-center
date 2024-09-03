package br.com.pokemoncenter.commom.util.hofs.textformat

import java.util.Locale

fun formatHeight(height: Int): String {
    val meters = height / 10.0
    return String.format(Locale.ROOT, "%.1f m", meters)
}

fun formatWeight(weight: Int): String {
    val kilograms = weight / 10.0
    return String.format(Locale.ROOT, "%.1f kg", kilograms)
}
