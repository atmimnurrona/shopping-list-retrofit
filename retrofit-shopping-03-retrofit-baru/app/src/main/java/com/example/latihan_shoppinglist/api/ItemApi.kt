package com.example.latihan_shoppinglist.api

import com.example.latihan_shoppinglist.data.model.Item
import com.example.latihan_shoppinglist.data.model.ItemRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ItemApi {

    @GET("items")
    suspend fun getData(): Response<List<Item>>

    @POST("items")
    suspend fun addData(@Body request : ItemRequest): Response<Item>

    @DELETE("items/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Item>

    @GET("items/{id}")
    suspend fun findById(@Path("id") id: Int): Response<Item>

    @PUT("items/{id}")
    suspend fun editById(@Path("id") id: Int, @Body request: ItemRequest): Response<Item>
}