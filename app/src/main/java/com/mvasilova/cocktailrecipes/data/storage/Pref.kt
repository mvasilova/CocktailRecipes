package com.mvasilova.cocktailrecipes.data.storage

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.mvasilova.cocktailrecipes.data.enums.AppTheme

class Pref(context: Context) {

    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var appTheme: AppTheme?
        get() = AppTheme.valueOf(
            sharedPreferences.getString(KEY_APP_THEME, AppTheme.SYSTEM.name)
                ?: AppTheme.SYSTEM.name
        )
        set(value) {
            sharedPreferences.edit {
                putString(KEY_APP_THEME, value?.name)
            }
        }

    companion object {
        const val FILE_NAME = "CocktailRecipesPreference"
        const val KEY_APP_THEME = "AppTheme"
    }
}