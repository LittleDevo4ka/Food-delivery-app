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
import com.example.fooddeliveryapp.model.dataClasses.Category
import com.example.fooddeliveryapp.model.dataClasses.OnItemClickListener


class CategoryRecyclerItem(private val categoriesList: List<Category>,
                           private val context: Context,
                           onClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CategoryRecyclerItem.MyViewHolder>() {

    private var mainListener: OnItemClickListener = onClickListener

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.category_name_card)
        val categoryImage: ImageView = itemView.findViewById(R.id.category_card_image)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.categoryName.text = categoriesList[position].name

        holder.categoryImage.setOnClickListener {
            mainListener.onItemClick(categoriesList[position])
        }


        Glide.with(context).load(categoriesList[position].image_url)
            .into(holder.categoryImage)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}