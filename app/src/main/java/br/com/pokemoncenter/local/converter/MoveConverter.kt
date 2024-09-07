package br.com.pokemoncenter.local.converter

import androidx.room.TypeConverter
import br.com.pokemoncenter.data.api.models.movedetailsresponse.EffectEntry
import br.com.pokemoncenter.data.api.models.movedetailsresponse.TargetDetail
import br.com.pokemoncenter.data.api.models.nestedmodels.Damage
import br.com.pokemoncenter.data.api.models.nestedmodels.Type
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MoveConverter {

    @TypeConverter
    fun fromType(type: Type): String {
        return Gson().toJson(type)
    }

    @TypeConverter
    fun toType(typeString: String): Type {
        val type = object : TypeToken<Type>() {}.type
        return Gson().fromJson(typeString, type)
    }

    @TypeConverter
    fun fromEffectEntries(effectEntry: List<EffectEntry>): String {
        return Gson().toJson(effectEntry)
    }

    @TypeConverter
    fun toEffectEntries(effectEntryString: String): List<EffectEntry> {
        val type = object : TypeToken<List<EffectEntry>>() {}.type
        return Gson().fromJson(effectEntryString, type)
    }

    @TypeConverter
    fun fromTargetDetail(targetDetail: TargetDetail): String {
        return Gson().toJson(targetDetail)
    }

    @TypeConverter
    fun toTargetDetail(targetDetailString: String): TargetDetail {
        val type = object : TypeToken<TargetDetail>() {}.type
        return Gson().fromJson(targetDetailString, type)
    }

    @TypeConverter
    fun fromDamageClass(damageClass: Damage): String {
        return Gson().toJson(damageClass)
    }

    @TypeConverter
    fun toDamageClass(damageClassString: String): Damage {
        val type = object : TypeToken<Damage>() {}.type
        return Gson().fromJson(damageClassString, type)
    }
}
