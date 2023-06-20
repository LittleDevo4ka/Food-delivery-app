package com.example.fooddeliveryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentCategoriesBinding
import com.example.fooddeliveryapp.model.dataClasses.Category
import com.example.fooddeliveryapp.model.dataClasses.OnItemClickListener
import com.example.fooddeliveryapp.viewModel.MainViewModel
import kotlinx.coroutines.launch
import kotlin.math.ceil


class CategoriesFragment : Fragment(), OnItemClickListener{

    private val tag = "CategoriesFragment"

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categorySwipeRefreshLayout.isRefreshing = true

        val imageSize = ceil(44 * resources.displayMetrics.density).toInt()

        val glideOptions = RequestOptions()
            .override(imageSize, imageSize)

        Glide.with(requireContext())
            .load(R.drawable.placeholder).apply(glideOptions)
            .into(binding.userProfileImageCategories)

        val adapterList: MutableList<Category> = mutableListOf()
        val myAdapter = CategoryRecyclerView(adapterList, requireContext(), this)
        binding.categoryRecyclerView.adapter = myAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.categoriesStateFlow.collect { categoriesList ->
                    adapterList.clear()

                    if (categoriesList != null) {
                        adapterList.addAll(categoriesList)
                    }

                    myAdapter.notifyDataSetChanged()
                    binding.categorySwipeRefreshLayout.isRefreshing = false
                }
            }
        }

        viewModel.getCategories()

        binding.categorySwipeRefreshLayout.setOnRefreshListener {
            updateCategories()
        }
    }

    private fun updateCategories() {
        viewModel.getCategories()
    }

    override fun onItemClick(category: Category) {
        findNavController().navigate(R.id.action_categoriesFragment_to_foodsFragment)
    }
}