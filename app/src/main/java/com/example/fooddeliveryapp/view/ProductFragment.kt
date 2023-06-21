package com.example.fooddeliveryapp.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentProductBinding
import com.example.fooddeliveryapp.model.dataClasses.Dish
import com.example.fooddeliveryapp.viewModel.MainViewModel

class ProductFragment : DialogFragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog = AlertDialog.Builder(context, R.style.MyDialog)

        binding = FragmentProductBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        alertDialog.setView(binding.root)

        binding.closeButtonProduct.setOnClickListener{
            dismiss()
        }


        return alertDialog.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dish = viewModel.getDish()

        if (dish != null) {
            binding.productName.text = dish.name
            binding.productPrice.text = "${dish.price} ₽"
            binding.productWeight.text = " · ${dish.weight}г"
            binding.productDescription.text = dish.description

            binding.addProductCartButton.setOnClickListener {
                viewModel.addToCard(dish.id)
            }

            Glide.with(requireContext()).load(dish.image_url)
                .placeholder(R.drawable.placeholder)
                .into(binding.productImage)
        }
    }
}