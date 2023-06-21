package com.example.fooddeliveryapp.viewModel

import com.example.fooddeliveryapp.model.dataClasses.Category
import com.example.fooddeliveryapp.model.dataClasses.Dish

interface RepositoryViewModel {

    fun setCategories(data: List<Category>?, code: Int)
    fun setDishes(data: List<Dish>?, code: Int)
}