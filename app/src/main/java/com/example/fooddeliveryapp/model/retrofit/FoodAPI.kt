package com.example.fooddeliveryapp.model.retrofit

import com.example.fooddeliveryapp.model.dataClasses.Categories
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface FoodAPI {

    @GET
    fun getCategories(@Url url: String): Call<Categories>
}