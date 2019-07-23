package com.mvasilova.cocktailrecipes.app.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.mvasilova.cocktailrecipes.BuildConfig
import com.mvasilova.cocktailrecipes.data.network.Api
import com.mvasilova.cocktailrecipes.domain.repository.DrinksRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single { buildRetrofit(get()) }

    single { buildOkHttp() }

    single { buildApi(get()) }

    single { DrinksRepository(get()) }

}


private fun buildApi(retrofit: Retrofit): Api? {
    return retrofit.create(Api::class.java)
}

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

private fun buildRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}