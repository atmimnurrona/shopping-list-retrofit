package com.example.latihan_shoppinglist.di.module

import com.example.latihan_shoppinglist.api.ItemApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideShoppingApi(retrofit: Retrofit): ItemApi {
        return retrofit.create(ItemApi::class.java)
    }
}