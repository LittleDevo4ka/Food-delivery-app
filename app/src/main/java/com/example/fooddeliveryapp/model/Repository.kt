package com.example.fooddeliveryapp.model

import com.example.fooddeliveryapp.model.dataClasses.Category
import com.example.fooddeliveryapp.model.retrofit.FoodAPIService
import com.example.fooddeliveryapp.viewModel.RepositoryViewModel

class Repository(private val viewModel: RepositoryViewModel) {

    private val tag = "Repository"

    private val foodAPIService = FoodAPIService(this)

    fun getCategories() {
        foodAPIService.getCategories()
    }

    fun sendCategories(data: List<Category>?, code: Int) {
        viewModel.setCategories(data, code)
    }


}