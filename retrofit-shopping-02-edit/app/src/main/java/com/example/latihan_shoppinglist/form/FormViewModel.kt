package com.example.latihan_shoppinglist.form

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihan_shoppinglist.MainActivity
import com.example.latihan_shoppinglist.api.EntityApi
import com.example.latihan_shoppinglist.data.Entity
import com.example.latihan_shoppinglist.data.EntityRequest
import com.example.latihan_shoppinglist.data.model.Item
import com.example.latihan_shoppinglist.data.repository.ItemRepository
import com.example.latihan_shoppinglist.utils.ResourceState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FormViewModel(val repository: ItemRepository) : ViewModel() {

    private var _itemLiveData = MutableLiveData<Boolean>()
    private var _isValid = MutableLiveData<ResourceState>()


    val itemLiveData : LiveData<Boolean>
        get() {
            return _itemLiveData
        }

    val isValid: LiveData<ResourceState>
        get() {
            return _isValid
        }

//    fun save(item: Item) {
//        _itemLiveData.value = repository.save(item)
//    }

    fun addData(entity: EntityRequest) {
        val retrofit = Retrofit.Builder().baseUrl(MainActivity.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(EntityApi::class.java)
        val call = service.addData(entity)
        call.enqueue(object : Callback<Entity> {
            override fun onFailure(call: Call<Entity>, t: Throwable) {
                Log.i("ONFAILURE", "${t.message}")
            }

            override fun onResponse(call: Call<Entity>, response: Response<Entity>) {
                _itemLiveData.value = response.isSuccessful
            }


        })
    }

    fun validation(item: Item) {
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
                _isValid.postValue(ResourceState.success(item))
            }
        }
    }
}