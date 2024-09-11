package br.com.pokemoncenter.commom.util.hofs.jsonparse

import org.json.JSONArray

fun jsonArrayToList(jsonArray: JSONArray): List<String> {
    return (0 until jsonArray.length()).map { jsonArray.getString(it) }
}
