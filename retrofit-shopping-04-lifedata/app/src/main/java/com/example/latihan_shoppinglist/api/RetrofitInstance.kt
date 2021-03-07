package com.example.latihan_shoppinglist.api

import com.example.latihan_shoppinglist.MainActivity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val okHttpClient = OkHttpClient.Builder().build()
    val retrofit = Retrofit.Builder().baseUrl(MainActivity.baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    fun getApiService() = retrofit.create(ItemApi::class.java)
}