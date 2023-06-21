package com.example.fooddeliveryapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.model.dataClasses.OnItemClickListener
import com.example.fooddeliveryapp.viewModel.MainViewModel

class DishesTagsRecyclerItem(private val dishesTagsList: List<String>,
                             onClickListener: OnItemClickListener,
                             private val context: Context,
                             private val viewModel: MainViewModel):
    RecyclerView.Adapter<DishesTagsRecyclerItem.MyViewHolder>() {

    private var mainListener: OnItemClickListener = onClickListener

    private var previousSelectedTag: MyViewHolder? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tagName: TextView = itemView.findViewById(R.id.dish_tag_name_card)
        val tagButton: Button = itemView.findViewById(R.id.dish_tag_button_card)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dish_tag_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tagName.text = dishesTagsList[position]
        if (dishesTagsList[position] == viewModel.getTag()) {
            firstCheckTagCard(holder)
        }

        holder.tagButton.setOnClickListener {
            if (previousSelectedTag == holder) return@setOnClickListener
            checkTagCard(holder)

            mainListener.onItemClick(dishesTagsList[position])
        }
    }

    private fun firstCheckTagCard(newCheckTag: MyViewHolder) {
        newCheckTag.tagName.setTextColor(ContextCompat.getColor(context, R.color.white))
        newCheckTag.tagButton.setBackgroundColor(ContextCompat.getColor(context, R.color.checked_button))
        previousSelectedTag = newCheckTag
    }

    private fun checkTagCard(newCheckTag: MyViewHolder) {
        newCheckTag.tagName.setTextColor(ContextCompat.getColor(context, R.color.white))
        newCheckTag.tagButton.setBackgroundColor(ContextCompat.getColor(context, R.color.checked_button))

        val tempSelectedTag = previousSelectedTag
        if (tempSelectedTag != null) {
            tempSelectedTag.tagButton
                .setBackgroundColor(ContextCompat.getColor(context, R.color.unchecked_button))
            tempSelectedTag.tagName.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
        previousSelectedTag = newCheckTag
    }

    override fun getItemCount(): Int {
        return dishesTagsList.size
    }
}