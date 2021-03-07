package com.example.latihan_shoppinglist.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihan_shoppinglist.R
import com.example.latihan_shoppinglist.data.model.Item
import com.example.latihan_shoppinglist.data.repository.ItemRepository
import com.example.latihan_shoppinglist.databinding.FragmentListBinding
import com.example.latihan_shoppinglist.form.FormViewModel
import com.example.latihan_shoppinglist.utils.ResourceState
import com.example.latihan_shoppinglist.utils.ResourceStatus

class ListFragment : Fragment() {

    lateinit var viewModel: ListViewModel
    lateinit var binding: FragmentListBinding
    lateinit var rvAdapter: ListViewAdapter
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        subscribe()
        viewModel.getAllData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("onCreateView", "irfaaaaaaaaaan")
        binding = FragmentListBinding.inflate(layoutInflater)
        binding.apply {

//            nextBtn.setOnClickListener {
//                viewModel.loadItemData(++page)
//                pageText.text = page.toString()
//            }
//
//            prevBtn.setOnClickListener {
//                if (page > 0) {
//                    viewModel.loadItemData(--page)
//                    pageText.text = page.toString()
//                }
//            }

            addItemFab.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_formFragment)
            }

            rvAdapter = ListViewAdapter(viewModel)
            recyclerViewItem.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = rvAdapter
            }
        }
        return binding.root
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ItemRepository()
                return ListViewModel(repo) as T
            }
        }).get(ListViewModel::class.java)
    }

    private fun subscribe() {
        //show list item
        viewModel.itemsLiveData.observe(this) {
            Log.d("SUBSCRIBE", "ekaaa")
            when (it.status) {
                ResourceStatus.LOADING -> Log.d("APP", "Loading..")
                ResourceStatus.SUCCESS -> {
                    val data : List<Item> = it.data as List<Item>
                    rvAdapter.setData(data)
                }
            }
        }

        //edit to form item
        viewModel.updateLiveData.observe(this) {
            val response = it.data as Item
            Log.d("GET ITEM", "${response}")
            Navigation.findNavController(requireView())
                    .navigate(
                            R.id.action_listFragment_to_formFragment,
                            bundleOf("edit_item" to response)
                    )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}