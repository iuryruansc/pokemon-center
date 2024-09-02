package br.com.pokemon_center.commom.util

fun calculateStatAtLevel(baseStat: Int, level: Int): Int {

    return ((2 * baseStat + 31) * level / 100 + 5)
}