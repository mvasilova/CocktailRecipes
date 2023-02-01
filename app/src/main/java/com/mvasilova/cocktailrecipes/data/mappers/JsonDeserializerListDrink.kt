package com.mvasilova.cocktailrecipes.data.mappers

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mvasilova.cocktailrecipes.app.ext.fromJson
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter
import com.mvasilova.cocktailrecipes.data.entity.DrinksFilter.Drink
import java.lang.reflect.Type

class JsonDeserializerListDrink : JsonDeserializer<Any> {
    private val gson = Gson()

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DrinksFilter {
        val jsonObject = json!!.asJsonObject
        val drinks = try {
            val drinksRaw = jsonObject.getAsJsonArray("drinks").toString()
            gson.fromJson<List<Drink>>(drinksRaw)
        } catch (e: Exception) {
            listOf()
        }
        return DrinksFilter(drinks)
    }
}
