package br.com.pokemoncenter.commom.util.hofs.general

import br.com.pokemoncenter.commom.Constants

fun calculateStatAtLevel(baseStat: Int, level: Int): Int {
    return (
        (Constants.BASE_STAT_MULTIPLIER * baseStat + Constants.INDIVIDUAL_VALUE) *
            level / Constants.LEVEL_DIVISOR + Constants.STAT_CONSTANT
        )
}
