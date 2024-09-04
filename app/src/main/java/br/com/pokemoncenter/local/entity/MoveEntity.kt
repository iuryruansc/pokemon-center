package br.com.pokemoncenter.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.pokemoncenter.data.api.models.movedetailsresponse.EffectEntry
import br.com.pokemoncenter.data.api.models.movedetailsresponse.TargetDetail
import br.com.pokemoncenter.data.api.models.nestedmodels.Damage
import br.com.pokemoncenter.data.api.models.nestedmodels.Type
import br.com.pokemoncenter.data.api.models.speciesbynamedata.FlavorTextEntry

@Entity(tableName = "move_details")
data class MoveEntity (
    @PrimaryKey
    val name: String,
    val accuracy: Int,
    val type: Type,
    val power: Int,
    val pp: Int,
    val flavorTextEntries: List<FlavorTextEntry>,
    val effectEntries: List<EffectEntry>,
    val damageClass: Damage,
    val target: TargetDetail
)