package com.mvasilova.cocktailrecipes.data.mappers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import java.lang.reflect.Type

class JsonDeserializerDrink : JsonDeserializer<RecipeInfoDrink.Drink> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RecipeInfoDrink.Drink {
        val jsonObject = json!!.asJsonObject

        val dateModified = jsonObject.get("dateModified").getNullAsEmptyString()
        val idDrink = jsonObject.get("idDrink").getNullAsEmptyString()
        val strAlcoholic = jsonObject.get("strAlcoholic").getNullAsEmptyString()
        val strCategory = jsonObject.get("strCategory").getNullAsEmptyString()
        val strDrink = jsonObject.get("strDrink").getNullAsEmptyString()
        val strDrinkThumb = jsonObject.get("strDrinkThumb").getNullAsEmptyString()
        val strGlass = jsonObject.get("strGlass").getNullAsEmptyString()
        val strIBA = jsonObject.get("strIBA").getNullAsEmptyString()
        val strInstructions = jsonObject.get("strInstructions").getNullAsEmptyString()

        val ingredients = mutableListOf<String>()
        val measure = mutableListOf<String>()

        jsonObject.keySet().forEach { key ->
            when {
                key.startsWith("strIngredient") && jsonObject[key] != null &&
                    jsonObject[key].getNullAsEmptyString().isNotEmpty() -> {
                    ingredients.add(jsonObject[key].asString)
                }
                key.startsWith("strMeasure") && jsonObject[key] != null &&
                    jsonObject[key].getNullAsEmptyString().isNotEmpty() -> {
                    measure.add(jsonObject[key].asString)
                }
            }
        }
        return RecipeInfoDrink.Drink(
            dateModified = dateModified,
            idDrink = idDrink,
            strAlcoholic = strAlcoholic,
            strCategory = strCategory,
            strDrink = strDrink,
            strDrinkThumb = strDrinkThumb,
            strGlass = strGlass,
            strIBA = strIBA,
            strInstructions = strInstructions,
            ingredients = ingredients,
            measure = measure
        )
    }

    private fun JsonElement.getNullAsEmptyString() = if (this.isJsonNull) "" else this.asString
}
