package com.example.latihan_shoppinglist.data

import com.google.gson.annotations.SerializedName

data class EntityRequest (

    @SerializedName("name")
    var name: String? = null,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("note")
    var note: String? = null,
    @SerializedName("quantiy")
    var quantity: Int = 0
)