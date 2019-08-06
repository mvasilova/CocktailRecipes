package com.mvasilova.cocktailrecipes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mvasilova.cocktailrecipes.data.entity.Favorite
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): Observable<List<Favorite>>

    @Insert
    fun insertDrink(favorite: Favorite): Single<Unit>

    @Query("DELETE FROM favorite WHERE id = :drinkId")
    fun deleteDrink(drinkId: String): Single<Unit>

    @Query("SELECT COUNT(id) FROM favorite WHERE id = :drinkId")
    fun observeDrinkFavorite(drinkId: String): Observable<Int>

    @Query("SELECT COUNT(id) FROM favorite WHERE id = :drinkId")
    fun isDrinkFavorite(drinkId: String): Single<Int>
}