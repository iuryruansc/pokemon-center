package br.com.pokemoncenter.commom.util.hofs.jsonparse

import br.com.pokemoncenter.commom.RegionImage
import br.com.pokemoncenter.data.models.Region
import org.json.JSONObject

fun parseRegionsJson(jsonString: String): List<Region> {
    val regions = mutableListOf<Region>()
    val jsonObject = JSONObject(jsonString)
    val regionsArray = jsonObject.getJSONArray("regions")

    for (i in 0 until regionsArray.length()) {
        val regionObject = regionsArray.getJSONObject(i)
        val name = regionObject.getString("name")
        val image = RegionImage.valueOf(regionObject.getString("image"))
        val altText = regionObject.getString("altText")
        val imageResId = image.imageResId

        val region = Region(name, image.toString(), altText, imageResId)
        regions.add(region)
    }
    return regions
}
