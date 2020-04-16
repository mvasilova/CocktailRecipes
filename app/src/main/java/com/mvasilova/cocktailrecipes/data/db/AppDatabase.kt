package com.mvasilova.cocktailrecipes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mvasilova.cocktailrecipes.data.entity.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
