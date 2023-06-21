package com.example.fooddeliveryapp.model.retrofit

import android.util.Log
import com.example.fooddeliveryapp.BuildConfig
import com.example.fooddeliveryapp.model.Repository
import com.example.fooddeliveryapp.model.dataClasses.Categories
import com.example.fooddeliveryapp.model.dataClasses.Category
import com.example.fooddeliveryapp.model.dataClasses.Dish
import com.example.fooddeliveryapp.model.dataClasses.Dishes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodAPIService(private val repository: Repository) {

    private val tag = "FoodAPIService"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://vk.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodAPI::class.java)

    fun getCategories() {
        val call = retrofit.getCategories(BuildConfig.baseCategoriesURL)
        call.enqueue(object: Callback<Categories>{
            override fun onResponse(
                call: Call<Categories>,
                response: Response<Categories>
            ) {
                if (response.isSuccessful) {
                    Log.i(tag, "getCategories: everything went well: ${response.code()}")
                    sendCategories(response.body()?.сategories, response.code())
                } else {
                    Log.w(tag, "getCategories: something went wrong: ${response.code()}")
                    sendCategories(null, response.code())
                }
            }

            override fun onFailure(call: Call<Categories>, t: Throwable) {
                Log.w(tag, "getCategories: something really went wrong: ${t.message}")
                sendCategories(null, 400)
            }

        })
    }

    fun sendCategories(data: List<Category>?, code: Int) {
        repository.sendCategories(data, code)
    }

    fun getDishes() {
        val call = retrofit.getDishes(BuildConfig.baseDishesURL)
        call.enqueue(object: Callback<Dishes>{
            override fun onResponse(call: Call<Dishes>, response: Response<Dishes>) {
                if (response.isSuccessful) {
                    Log.i(tag, "getDishes: everything went well: ${response.code()}")
                    sendDishes(response.body()?.dishes, response.code())
                } else {
                    Log.w(tag, "getDishes: something went wrong: ${response.code()}")
                    sendDishes(null, response.code())
                }
            }

            override fun onFailure(call: Call<Dishes>, t: Throwable) {
                Log.w(tag, "getDishes: something really went wrong: ${t.message}")
                sendDishes(null, 400)
            }
        })
    }

    fun sendDishes(data: List<Dish>?, code: Int) {
        repository.sendDishes(data, code)
    }
}