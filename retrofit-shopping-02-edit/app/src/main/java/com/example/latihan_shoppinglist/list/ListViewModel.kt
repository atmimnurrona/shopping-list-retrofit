package com.example.latihan_shoppinglist.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihan_shoppinglist.MainActivity
import com.example.latihan_shoppinglist.api.EntityApi
import com.example.latihan_shoppinglist.data.Entity
import com.example.latihan_shoppinglist.data.EntityRequest
import com.example.latihan_shoppinglist.data.repository.ItemRepositoryInterface
import com.example.latihan_shoppinglist.listeners.ItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//untuk lifedata
class ListViewModel(private val repository: ItemRepositoryInterface) : ViewModel(), ItemClickListener {

    private var _itemsLiveData = MutableLiveData<List<Entity>>()
    private var _itemLiveData = MutableLiveData<Entity>()

    val itemsLiveData: LiveData<List<Entity>>
        get() {
            return _itemsLiveData
        }

    val itemLiveData: LiveData<Entity>
        get() {
            return _itemLiveData
        }

//    init {
//        loadItemData(0)
//    }

    fun loadItemData() {
        getAllData()
    }

    fun getItemData(item: Entity) {
        findById(item)
    }

//    fun loadItemData(page: Int) {
//        _itemsLiveData.value = repository.list(page)
//    }

    fun getAllData() {
        val retrofit = Retrofit.Builder().baseUrl(MainActivity.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(EntityApi::class.java)
        val call = service.getData()
        call.enqueue(object : Callback<List<Entity>> {
            override fun onFailure(call: Call<List<Entity>>, t: Throwable) {
                Log.i("ONFAILURE", "${t.message}")
            }

            override fun onResponse(call: Call<List<Entity>>, response: Response<List<Entity>>) {
                if (response.isSuccessful) {
                    _itemsLiveData.value = response.body()!!
                }
            }


        })
    }

    override fun onDelete(item: Entity) {
        val retrofit = Retrofit.Builder().baseUrl(MainActivity.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(EntityApi::class.java)
        val call = service.delete(item.id)
        call.enqueue(object : Callback<Entity> {
            override fun onFailure(call: Call<Entity>, t: Throwable) {
                Log.d("DELETE", "UNSUCCESSFUL")
            }

            override fun onResponse(call: Call<Entity>, response: Response<Entity>) {
                if (response.isSuccessful) {
                    loadItemData()
                } else {
                    Log.d("DELETE", "UNSUCCESSFUL")
                }
            }

        })
    }

    override fun onEdit(item: Entity) {
        val retrofit = Retrofit.Builder().baseUrl(MainActivity.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(EntityApi::class.java)
        val request = EntityRequest()
        request.name = item.name
        request.date = item.date
        request.quantity = item.quantity
        request.note = item.note
        val call = service.editById(item.id, request)
        call.enqueue(object: Callback<Entity> {
            override fun onFailure(call: Call<Entity>, t: Throwable) {
            }

            override fun onResponse(call: Call<Entity>, response: Response<Entity>) {
                if (response.isSuccessful) {
                    _itemLiveData.value = response.body()
                }
            }

        })
    }

    fun findById(item: Entity) {
        val retrofit = Retrofit.Builder().baseUrl(MainActivity.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(EntityApi::class.java)
        val call = service.findById(item.id)
        call.enqueue(object: Callback<Entity> {
            override fun onFailure(call: Call<Entity>, t: Throwable) {

            }

            override fun onResponse(call: Call<Entity>, response: Response<Entity>) {
                if (response.isSuccessful) {
                    _itemLiveData.value = response.body()
                }
            }

        })
    }

}