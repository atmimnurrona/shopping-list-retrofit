package com.example.latihan_shoppinglist.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihan_shoppinglist.MainActivity
import com.example.latihan_shoppinglist.api.ItemApi
import com.example.latihan_shoppinglist.data.model.Item
import com.example.latihan_shoppinglist.data.model.ItemRequest
import com.example.latihan_shoppinglist.data.repository.ItemRepositoryInterface
import com.example.latihan_shoppinglist.listeners.ItemClickListener
import com.example.latihan_shoppinglist.utils.ResourceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//untuk lifedata
class ListViewModel(private val repository: ItemRepositoryInterface) : ViewModel(), ItemClickListener {

    private var _itemsLiveData = MutableLiveData<ResourceState>()
    private var _updateLiveData = MutableLiveData<ResourceState>()
    val itemsLiveData: LiveData<ResourceState>
        get() {
            return _itemsLiveData
        }
    val updateLiveData: LiveData<ResourceState>
        get() {
            return _updateLiveData
        }

    init {
        getAllData()
    }

    fun getAllData() {
        CoroutineScope(Dispatchers.IO).launch {
            //itemsLiveData yg berisi ResourceState, loading selama data belum muncul
            _itemsLiveData.postValue(ResourceState.loading())
            //response diisi oleh repository
            val response = repository.getAllItem()
            //jika responsenya sukses,
            if (response.isSuccessful) {
                //maka dicek, apakah response.body ada isinya?
                response.body()?.let {
                    //jika iya, maka itemsLiveData diisi dengan ResourceState.success
                    Log.d("DATA", "$it")
                    _itemsLiveData.postValue(ResourceState.success(it))
                }
            } else {
                //jika response tidak sukses,
                //maka itemsLiveData diisi dengan ResourceState.fail
                _itemsLiveData.postValue(ResourceState.fail("error"))
            }
        }
    }

    override fun onDelete(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteItem(id)
            getAllData()
        }
    }

    override fun onEdit(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getItemById(id)
            if(response.isSuccessful) {
                _updateLiveData.postValue(ResourceState.success(response.body()))
            } else {
                _updateLiveData.postValue(ResourceState.fail("error"))
            }
            getAllData()
        }
    }
}