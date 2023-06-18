package com.example.fooddeliveryapp.viewModel

import com.example.fooddeliveryapp.model.dataClasses.Category

interface RepositoryViewModel {

    fun setCategories(data: List<Category>?, code: Int)
}