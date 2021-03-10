package com.example.latihan_shoppinglist.di.module

import android.content.Context
import android.content.SharedPreferences
import com.example.latihan_shoppinglist.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        gson: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MainActivity.baseUrl)
            .addConverterFactory(gson)
            .build()
    }

}