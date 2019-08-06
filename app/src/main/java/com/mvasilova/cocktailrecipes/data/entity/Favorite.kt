package com.mvasilova.cocktailrecipes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "drink_thumb") val drinkThumb: String?
)