package br.com.pokemoncenter.data.api.models

import br.com.pokemoncenter.data.api.models.movedetailsresponse.EffectEntry
import br.com.pokemoncenter.data.api.models.movedetailsresponse.TargetDetail
import br.com.pokemoncenter.data.api.models.nestedmodels.Damage
import br.com.pokemoncenter.data.api.models.nestedmodels.Type
import br.com.pokemoncenter.data.api.models.speciesbynamedata.FlavorTextEntry
import com.google.gson.annotations.SerializedName

data class MoveDetailsResponse(
    val name: String,
    val accuracy: Int,
    val type: Type,
    val power: Int,
    val pp: Int,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    @SerializedName("effect_entries")
    val effectEntries: List<EffectEntry>,
    @SerializedName("damage_class")
    val damageClass: Damage,
    val target: TargetDetail
)
