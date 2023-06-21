package com.example.fooddeliveryapp.model.dataClasses

import android.view.View
import android.widget.Button
import android.widget.TextView

interface OnItemClickListener {

    fun onItemClick(category: Category)
    fun onItemClick(dish: Dish)
    fun onItemClick(tag: String)
}