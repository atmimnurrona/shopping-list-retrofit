package com.example.latihan_shoppinglist.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.latihan_shoppinglist.data.Entity
import com.example.latihan_shoppinglist.data.model.Item
import com.example.latihan_shoppinglist.databinding.CardViewItemBinding
import com.example.latihan_shoppinglist.listeners.ItemClickListener

class ListViewHolder(view: View, val itemClickListener: ItemClickListener) :
        RecyclerView.ViewHolder(view) {
        private val binding = CardViewItemBinding.bind(view)

    fun bind(item: Entity) {
        binding.apply {
            nameTv.text = "Item name: ${item.name}"
            quantityTv.text = "Quantity: ${item.quantity.toString()}"
            dateTv.text = "Date: ${item.date}"
            noteTv.text = "Note: ${item.note}"
            deleteBtn.setOnClickListener {
                itemClickListener.onDelete(item)
            }
//            cardItem.setOnClickListener {
//                itemClickListener.onEdit(item)
//            }
        }
    }
}