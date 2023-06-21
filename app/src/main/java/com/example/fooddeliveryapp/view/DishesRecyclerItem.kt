package com.example.fooddeliveryapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.model.dataClasses.Dish
import com.example.fooddeliveryapp.model.dataClasses.OnItemClickListener

class DishesRecyclerItem(private val dishesList: List<Dish>,
                         private val context: Context,
                         onClickListener: OnItemClickListener):
RecyclerView.Adapter<DishesRecyclerItem.MyViewHolder>() {

    private var mainListener: OnItemClickListener = onClickListener

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishName: TextView = itemView.findViewById(R.id.dish_card_name)
        val dishImage: ImageView = itemView.findViewById(R.id.dish_card_image)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dish_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.dishName.text = dishesList[position].name

        holder.dishImage.setOnClickListener {
            mainListener.onItemClick(dishesList[position])
        }


        Glide.with(context).load(dishesList[position].image_url)
            .into(holder.dishImage)
    }

    override fun getItemCount(): Int {
        return dishesList.size
    }
}