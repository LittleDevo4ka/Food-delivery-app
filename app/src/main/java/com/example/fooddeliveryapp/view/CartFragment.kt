package com.example.fooddeliveryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentCartBinding
import com.example.fooddeliveryapp.model.dataClasses.CartItem
import com.example.fooddeliveryapp.model.dataClasses.Category
import com.example.fooddeliveryapp.model.dataClasses.Dish
import com.example.fooddeliveryapp.model.dataClasses.OnItemClickListener
import com.example.fooddeliveryapp.viewModel.MainViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.ceil


class CartFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: MainViewModel
    private val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale("ru"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCartBinding.inflate(inflater, container, false)
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
            .into(binding.userProfileImageCart)

        val myAdapter = CartRecyclerItem(viewModel , viewModel.getCartList(), this, requireContext())
        binding.cartRecyclerView.adapter = myAdapter

        var summaryPrice = 0
        val cartList = viewModel.getCartList()
        for (i in cartList.indices) {
            val tempDish = viewModel.getDishById(cartList[i].id)
            if (tempDish != null) {
                summaryPrice += tempDish.price
            }
        }
        val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
        val formattedPrice = numberFormat.format(summaryPrice)
        binding.payButtonCart.text = "Оплатить ${formattedPrice} ₽"

        val calendar = Calendar.getInstance()
        binding.currentDateCart.text = dateFormat.format(calendar.time)
    }

    override fun onItemClick(category: Category) {
    }

    override fun onItemClick(dish: Dish) {
    }

    override fun onItemClick(tag: String) {
    }
}