package com.example.latihan_shoppinglist.data

import com.google.gson.annotations.SerializedName

class Entity {

    @SerializedName("id")
    val id: Int = 0
    @SerializedName("name")
    val name: String? = null
    @SerializedName("date")
    val date: String? = null
    @SerializedName("note")
    val note: String? = null
    @SerializedName("quantiy")
    val quantity: Int = 0
}