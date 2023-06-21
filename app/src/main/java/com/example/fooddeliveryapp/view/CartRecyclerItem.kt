package com.example.fooddeliveryapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.model.dataClasses.CartItem
import com.example.fooddeliveryapp.model.dataClasses.Dish
import com.example.fooddeliveryapp.model.dataClasses.OnItemClickListener
import com.example.fooddeliveryapp.viewModel.MainViewModel

class CartRecyclerItem(private val viewModel: MainViewModel,
                       private val cartList: List<CartItem>,
                       onClickListener: OnItemClickListener,
                       private val context: Context) :
    RecyclerView.Adapter<CartRecyclerItem.MyViewHolder>()  {

    private var mainListener: OnItemClickListener = onClickListener

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartItemName: TextView = itemView.findViewById(R.id.cart_name_card)
        val cartItemPrice: TextView = itemView.findViewById(R.id.cart_price_card)
        val cartItemWeight: TextView = itemView.findViewById(R.id.cart_weight_card)
        val cartAmount: TextView = itemView.findViewById(R.id.product_amount_cart_card)
        val plusButton: Button = itemView.findViewById(R.id.plus_button_cart_card)
        val minusButton: Button = itemView.findViewById(R.id.minus_button_cart_card)
        val cartImage: ImageView = itemView.findViewById(R.id.cart_image_card)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_card, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cartAmount.text = "${cartList[position].amount}"

        val dish = viewModel.getDishById(cartList[position].id)

        if (dish != null) {
            holder.cartItemName.text = dish.name
            holder.cartItemPrice.text = "${dish.price} ₽"
            holder.cartItemWeight.text = " · ${dish.weight}г"


            Glide.with(context).load(dish.image_url)
                .placeholder(R.drawable.placeholder)
                .into(holder.cartImage)
        }

        holder.plusButton.setOnClickListener {
            viewModel.addToCard(cartList[position].id)
            val tempAmount = holder.cartAmount.text.toString().toInt() + 1
            holder.cartAmount.text = tempAmount.toString()
        }

        holder.minusButton.setOnClickListener {
            viewModel.removeCardItem(cartList[position].id)
            val tempAmount = holder.cartAmount.text.toString().toInt() - 1
            holder.cartAmount.text = tempAmount.toString()
        }


    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}