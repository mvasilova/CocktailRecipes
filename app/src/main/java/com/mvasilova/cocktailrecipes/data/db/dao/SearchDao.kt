package com.mvasilova.cocktailrecipes.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mvasilova.cocktailrecipes.data.db.entities.SearchHistory

@Dao
interface SearchDao {

    @Query("SELECT * FROM search")
    fun getAll(): LiveData<List<SearchHistory>>

    @Query("SELECT * FROM search")
    fun getSearchList(): List<SearchHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearch(searchHistory: SearchHistory)

    @Query("DELETE FROM search WHERE name=:name")
    fun deleteSearch(name: String?)

    @Transaction
    fun addSearchHistory(searchHistory: SearchHistory) {
        if (getSearchList().any { it.name == searchHistory.name }) {
            deleteSearch(searchHistory.name)
        }
        insertSearch(searchHistory)
    }
}
