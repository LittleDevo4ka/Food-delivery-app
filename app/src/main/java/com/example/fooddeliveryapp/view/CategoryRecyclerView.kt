package com.example.fooddeliveryapp.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.model.dataClasses.Category
import com.example.fooddeliveryapp.model.dataClasses.OnItemClickListener


class CategoryRecyclerView(private val categoriesList: List<Category>,
                           private val context: Context,
                           onClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CategoryRecyclerView.MyViewHolder>() {

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