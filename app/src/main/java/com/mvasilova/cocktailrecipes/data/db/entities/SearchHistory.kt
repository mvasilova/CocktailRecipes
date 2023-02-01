package com.mvasilova.cocktailrecipes.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mvasilova.cocktailrecipes.app.platform.DisplayableItem

@Entity(tableName = "search")
data class SearchHistory(
    @ColumnInfo(name = "name")
    val name: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : DisplayableItem {
    override val itemId: String
        get() = id.toString()
}
