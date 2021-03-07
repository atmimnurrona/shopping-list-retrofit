package com.example.latihan_shoppinglist.form

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihan_shoppinglist.MainActivity
import com.example.latihan_shoppinglist.api.ItemApi
import com.example.latihan_shoppinglist.data.model.Item
import com.example.latihan_shoppinglist.data.model.ItemRequest
import com.example.latihan_shoppinglist.data.repository.ItemRepository
import com.example.latihan_shoppinglist.utils.ResourceState
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FormViewModel(val repository: ItemRepository) : ViewModel() {

    private var _itemLiveData = MutableLiveData<ResourceState>()
    private var _isValid = MutableLiveData<ResourceState>()

    val itemLiveData : LiveData<ResourceState>
        get() {
            return _itemLiveData
        }

    val isValid: LiveData<ResourceState>
        get() {
            return _isValid
        }

    fun addData(item: ItemRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            _itemLiveData.postValue(ResourceState.loading())
            val response =
                    if(item.id == 0) {
                        repository.addItem(item)
                    } else {
                        repository.editItem(item)
                    }
            if (response.isSuccessful) {
                response.body()?.let {
                    _itemLiveData.postValue(ResourceState.success(it))
                }
            } else {
                _itemLiveData.postValue(ResourceState.fail("error"))
            }
        }
    }

    fun validation(item: ItemRequest) {
        GlobalScope.launch {
            _isValid.postValue(ResourceState.loading())
            delay(1000)
            if (item.date.isNullOrBlank()) {
                _isValid.postValue(ResourceState.fail("Date can not empty"))
            } else if (item.name.isNullOrBlank()) {
                _isValid.postValue(ResourceState.fail("Name can not empty"))
            } else if (item.note.isNullOrBlank()) {
                _isValid.postValue(ResourceState.fail("Note can not empty"))
            } else if (item.quantity.toString().isNullOrBlank()) {
                _isValid.postValue(ResourceState.fail("Quantity can not empty"))
            } else {
                _isValid.postValue(ResourceState.success(true))
            }
        }
    }
}