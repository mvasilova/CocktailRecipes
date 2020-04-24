package com.mvasilova.cocktailrecipes.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDrink(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE id = :drinkId")
    fun deleteDrink(drinkId: String)

    @Query("SELECT COUNT(id) FROM favorite WHERE id = :drinkId")
    fun observeDrinkFavorite(drinkId: String): LiveData<Int>

    @Query("SELECT COUNT(id) FROM favorite WHERE id = :drinkId")
    fun isDrinkFavorite(drinkId: String): Int

    @Transaction
    fun actionFavorite(favorite: Favorite) {
        if (isDrinkFavorite(favorite.id) > 0) {
            deleteDrink(favorite.id)
        } else {
            insertDrink(favorite)
        }
    }

}