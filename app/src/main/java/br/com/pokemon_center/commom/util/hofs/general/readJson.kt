package br.com.pokemon_center.commom.util.hofs.general

import android.content.Context

fun readJson(context: Context, fileName: String) : String? {
    val jsonString: String
    try{
        jsonString = context.assets.open(fileName)
            .bufferedReader().use { it.readText() }
        return jsonString
    } catch (ioException: Exception) {
        ioException.printStackTrace()
        return null
    }
}