package br.com.pokemoncenter.commom.util.hofs.textformat

import br.com.pokemoncenter.commom.Constants
import java.util.Locale

fun formatHeight(height: Int): String {
    val meters = height / Constants.FORMAT_HW
    return String.format(Locale.ROOT, "%.1f m", meters)
}

fun formatWeight(weight: Int): String {
    val kilograms = weight / Constants.FORMAT_HW
    return String.format(Locale.ROOT, "%.1f kg", kilograms)
}
