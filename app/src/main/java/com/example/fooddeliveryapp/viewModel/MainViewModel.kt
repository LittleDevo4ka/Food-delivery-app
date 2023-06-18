package com.example.fooddeliveryapp.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.fooddeliveryapp.model.Repository
import com.example.fooddeliveryapp.model.dataClasses.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(application: Application) : AndroidViewModel(application), RepositoryViewModel {

    private val tag = "MainViewModel"

    private val repository = Repository(this)

    private var categoriesList: MutableList<Category> = mutableListOf()
    private val categoriesMutableFlow: MutableStateFlow<List<Category>?> = MutableStateFlow(null)
    val categoriesStateFlow: StateFlow<List<Category>?> = categoriesMutableFlow

    private var gettingCategories = false

    fun getCategories() {
        if (gettingCategories) {
            return
        } else {
            gettingCategories = true
        }
        categoriesList.clear()
        categoriesMutableFlow.value = null

        repository.getCategories()
    }

    override fun setCategories(data: List<Category>?, code: Int) {
        gettingCategories = false

        if (data != null) {
            categoriesList.addAll(data)
            categoriesMutableFlow.value = categoriesList.toList()
        } else {
            Toast.makeText(getApplication(),
                "Error: couldn't get the categories: $code", Toast.LENGTH_SHORT).show()
        }
    }
}