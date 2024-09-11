package br.com.pokemoncenter.commom.util.hofs.textformat

import java.util.Locale

fun removeBlankSpace(text: String): String {
    return text.lowercase(Locale.ROOT).replace(" ", "")
}
