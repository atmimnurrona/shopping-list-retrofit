package com.example.latihan_shoppinglist.listeners

import com.example.latihan_shoppinglist.data.model.ItemRequest

interface ItemClickListener {
    fun onDelete(id: Int)
    fun onEdit(id: Int)
}