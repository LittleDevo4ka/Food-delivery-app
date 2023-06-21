package com.example.fooddeliveryapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentDishesBinding
import com.example.fooddeliveryapp.model.dataClasses.Category
import com.example.fooddeliveryapp.model.dataClasses.Dish
import com.example.fooddeliveryapp.model.dataClasses.OnItemClickListener
import com.example.fooddeliveryapp.viewModel.MainViewModel
import kotlinx.coroutines.launch
import kotlin.math.ceil

class DishesFragment : Fragment(), OnItemClickListener {

    private val tag = "DishesFragment"

    private lateinit var binding: FragmentDishesBinding
    private lateinit var viewModel: MainViewModel

    private var dishesReceived = false
    private var dishesTagsReceived = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDishesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dishesSwipeRefreshLayout.isRefreshing = true

        val imageSize = ceil(44 * resources.displayMetrics.density).toInt()

        val glideOptions = RequestOptions()
            .override(imageSize, imageSize)

        Glide.with(requireContext())
            .load(R.drawable.placeholder).apply(glideOptions)
            .into(binding.userProfileImageDishes)

        binding.backButtonDishes.setOnClickListener {
            findNavController().navigate(R.id.action_foodsFragment_to_categoriesFragment)
        }


        val dishesList: MutableList<Dish> = mutableListOf()
        val dishesAdapter = DishesRecyclerItem(dishesList, requireContext(), this)
        binding.dishesRecyclerView.adapter = dishesAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.dishesStateFlow.collect { newDishesList ->
                    dishesList.clear()

                    if (newDishesList != null) {
                        dishesList.addAll(newDishesList)
                    }

                    dishesAdapter.notifyDataSetChanged()

                    binding.dishesSwipeRefreshLayout.isRefreshing = false
                    if (dishesTagsReceived) {
                        binding.dishesSwipeRefreshLayout.isRefreshing = false
                    } else {
                        dishesReceived = true
                    }
                }
            }
        }

        val dishesTagsList: MutableList<String> = mutableListOf()
        val dishesTagsAdapter = DishesTagsRecyclerItem(dishesTagsList, this,
            requireContext(), viewModel)
        binding.dishesTagsRecyclerView.adapter = dishesTagsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.dishesTagsStateFlow.collect { newDishesTagsList ->
                    dishesTagsList.clear()

                    if (newDishesTagsList != null) {
                        dishesTagsList.addAll(newDishesTagsList)
                    }

                    dishesTagsAdapter.notifyDataSetChanged()

                    if (dishesReceived) {
                        binding.dishesSwipeRefreshLayout.isRefreshing = false
                    } else {
                        dishesTagsReceived = true
                    }
                }
            }
        }

        binding.dishesSwipeRefreshLayout.setOnRefreshListener {
            updateDishes()
        }

        viewModel.getDishes()
    }

    private fun updateDishes() {
        dishesReceived = false
        dishesTagsReceived = false

        viewModel.getDishes()
    }

    override fun onItemClick(category: Category) {
    }

    override fun onItemClick(dish: Dish) {
        viewModel.setDish(dish)

        val frag = ProductFragment()
        frag.show(childFragmentManager, "Something")
    }

    override fun onItemClick(tag: String) {
        viewModel.setTag(tag)
    }

}