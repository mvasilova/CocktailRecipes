package com.mvasilova.cocktailrecipes.app.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mvasilova.cocktailrecipes.BuildConfig
import com.mvasilova.cocktailrecipes.data.entity.RecipeInfoDrink
import com.mvasilova.cocktailrecipes.data.mappers.JsonDeserializerDrink
import com.mvasilova.cocktailrecipes.data.network.Api
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModule = module {

    single { buildRetrofit(get(), get()) }

    single { buildOkHttp() }

    single { buildApi(get()) }

    single { DrinksRepository(get()) }

    single { JsonDeserializerDrink() }

    single { buildJson(get()) }
}


private fun buildApi(retrofit: Retrofit): Api? {
    return retrofit.create(Api::class.java)
}

private fun buildJson(deserializerDrink: JsonDeserializerDrink) =
    GsonBuilder()
        .registerTypeAdapter(RecipeInfoDrink.Drink::class.java, deserializerDrink)
        .create()


private fun buildOkHttp(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
    okHttpClientBuilder.connectTimeout(50, TimeUnit.SECONDS)
    okHttpClientBuilder.readTimeout(50, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        okHttpClientBuilder.addNetworkInterceptor(StethoInterceptor())
    }

    return okHttpClientBuilder.build()
}

private fun buildRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}