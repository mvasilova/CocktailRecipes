package com.mvasilova.cocktailrecipes.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mvasilova.cocktailrecipes.data.db.AppDatabase.Companion.DATABASE_VERSION
import com.mvasilova.cocktailrecipes.data.db.dao.FavoriteDao
import com.mvasilova.cocktailrecipes.data.db.entities.Favorite

@Database(entities = [Favorite::class], version = DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "CocktailRecipesDataBase"
        fun buildDataSource(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
