package com.example.fooddeliveryapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentFoodsBinding
import com.example.fooddeliveryapp.viewModel.MainViewModel
import kotlin.math.ceil

class FoodsFragment : Fragment() {

    private val tag = "FoodsFragment"

    private lateinit var binding: FragmentFoodsBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFoodsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageSize = ceil(44 * resources.displayMetrics.density).toInt()

        val glideOptions = RequestOptions()
            .override(imageSize, imageSize)

        Glide.with(requireContext())
            .load(R.drawable.placeholder).apply(glideOptions)
            .into(binding.userProfileImageFoods)

        binding.backButtonFoods.setOnClickListener {
            findNavController().navigate(R.id.action_foodsFragment_to_categoriesFragment)
        }
    }

}