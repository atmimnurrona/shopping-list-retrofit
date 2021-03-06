package com.example.latihan_shoppinglist.api

import com.example.latihan_shoppinglist.data.Entity
import com.example.latihan_shoppinglist.data.EntityRequest
import retrofit2.Call
import retrofit2.http.*

interface EntityApi {

    @GET("items")
    fun getData(): Call<List<Entity>>

    @POST("items")
    fun addData(@Body request : EntityRequest): Call<Entity>

    @DELETE("items/{id}")
    fun delete(@Path("id") id: Int): Call<Entity>

    @GET("items/{id}")
    fun findById(@Path("id") id: Int): Call<Entity>

    @PUT("items/{id}")
    fun editById(@Path("id") id: Int, @Body request: EntityRequest): Call<Entity>
}