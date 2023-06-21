package com.example.fooddeliveryapp.model.dataClasses

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("id") val id: Int,
    @SerializedName("amount") var amount: Int
)
