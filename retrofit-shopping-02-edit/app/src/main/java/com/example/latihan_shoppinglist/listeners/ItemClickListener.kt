package com.example.latihan_shoppinglist.listeners

import com.example.latihan_shoppinglist.data.Entity

interface ItemClickListener {
    fun onDelete(item: Entity)
    fun onEdit(item: Entity)
}