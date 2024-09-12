package br.com.pokemoncenter.commom

import br.com.pokemon_center.R

object Constants {
    const val MIN_LEVEL = 1
    const val MAX_LEVEL = 99
    const val EFFECTIVE_4X = 4.0
    const val EFFECTIVE_2X = 2.0
    const val EFFECTIVE_NORMAL = 1.0
    const val EFFECTIVE_HALF = 0.5
    const val EFFECTIVE_QUARTER = 0.25
    const val NO_EFFECT = 0.0
    const val CHARACTER_SPAN_COUNT = 3
    const val TEXT_SPAN_COUNT = 1
    const val ERROR_404 = 404
    const val ERROR_400 = 400
    const val FORMAT_HW = 10.0
    const val BASE_STAT_MULTIPLIER = 2
    const val INDIVIDUAL_VALUE = 31
    const val LEVEL_DIVISOR = 100
    const val STAT_CONSTANT = 5
    const val BALLOON_WIDTH_RATIO = 1.0f
    const val BALLOON_TEXT_SIZE = 15f
    const val BALLOON_ARROW_SIZE = 10
    const val BALLOON_ARROW_POSITION = 0.5f
    const val BALLOON_PADDING = 12
    const val BALLOON_CORNER_RADIUS = 8f
}

enum class StatIndex {
    HP, ATK, DEF, SATK, SDEF, SPEED
}

enum class FragmentPosition {
    INFO, STATS, EFFECTIVENESS, MOVES
}

enum class TypeEffectiveness(val value: Double, val colorResId: Int, val format: String) {
    QUADRUPLE_EFFECTIVE(Constants.EFFECTIVE_4X, R.color.quadrupleEffective, "x%.1f"),
    DOUBLE_EFFECTIVE(Constants.EFFECTIVE_2X, R.color.theme_dark_double_effective, "x%.1f"),
    DOUBLE_RESIST(Constants.EFFECTIVE_HALF, R.color.doubleResist, "x%.1f"),
    QUADRUPLE_RESIST(Constants.EFFECTIVE_QUARTER, R.color.quadrupleResist, "x%.2f"),
    NO_EFFECT(Constants.NO_EFFECT, R.color.noEffect, "x%.1f");

    companion object {
        fun fromValue(value: Double): TypeEffectiveness? = entries.find { it.value == value }
    }
}
