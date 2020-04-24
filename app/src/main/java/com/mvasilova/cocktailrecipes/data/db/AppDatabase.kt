package com.mvasilova.cocktailrecipes.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mvasilova.cocktailrecipes.data.db.AppDatabase.Companion.DATABASE_VERSION
import com.mvasilova.cocktailrecipes.data.db.dao.FavoriteDao
import com.mvasilova.cocktailrecipes.data.db.dao.SearchDao
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite
import com.mvasilova.cocktailrecipes.data.db.entities.SearchHistory

@Database(entities = [Favorite::class, SearchHistory::class], version = DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun searchDao(): SearchDao

    companion object {
        const val DATABASE_VERSION = 3
        private const val DATABASE_NAME = "CocktailRecipesDataBase"
        fun buildDataSource(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
