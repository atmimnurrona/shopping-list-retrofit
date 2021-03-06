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
import com.example.latihan_shoppinglist.data.repository.ItemRepository
import com.example.latihan_shoppinglist.databinding.FragmentListBinding

class ListFragment : Fragment() {

    lateinit var viewModel: ListViewModel
    lateinit var binding: FragmentListBinding
    lateinit var rvAdapter: ListViewAdapter
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListBinding.inflate(layoutInflater)
        viewModel.getAllData()
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

            rvAdapter = ListViewAdapter(viewModel)
            recyclerViewItem.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = rvAdapter
            }

            addItemFab.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_formFragment)
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
            Log.d("DATA", "$it")
            rvAdapter.setData(it)
        }

        //edit to form item
        viewModel.itemLiveData.observe(this) {
            Log.d("GET ITEM", "$it")
            Navigation.findNavController(requireView())
                    .navigate(
                            R.id.action_listFragment_to_formFragment,
                            bundleOf("edit_item" to it)
                    )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}