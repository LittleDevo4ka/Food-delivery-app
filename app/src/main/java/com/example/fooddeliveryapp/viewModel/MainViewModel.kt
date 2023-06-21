package com.example.fooddeliveryapp.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.fooddeliveryapp.model.Repository
import com.example.fooddeliveryapp.model.dataClasses.Category
import com.example.fooddeliveryapp.model.dataClasses.Dish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(application: Application) : AndroidViewModel(application), RepositoryViewModel {

    private val tag = "MainViewModel"

    private val repository = Repository(this)

    private var categoriesList: MutableList<Category> = mutableListOf()
    private val categoriesMutableFlow: MutableStateFlow<List<Category>?> = MutableStateFlow(null)
    val categoriesStateFlow: StateFlow<List<Category>?> = categoriesMutableFlow

    private var allDishesList: MutableList<Dish> = mutableListOf()
    private var dishesList: MutableList<Dish> = mutableListOf()
    private val dishesMutableFlow: MutableStateFlow<List<Dish>?> = MutableStateFlow(null)
    val dishesStateFlow: StateFlow<List<Dish>?> = dishesMutableFlow

    private val dishesTagsList: MutableList<String> = mutableListOf()
    private val dishesTagsMutableFlow: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    val dishesTagsStateFlow: StateFlow<List<String>?> = dishesTagsMutableFlow

    private var selectedTag = "Все меню"
    private val selectedTagMutableFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val selectedTagStateFlow: StateFlow<String?> = selectedTagMutableFlow

    private var gettingCategories = false
    private var gettingDishes = false

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
        if (data != null) {
            categoriesList.addAll(data)
            categoriesMutableFlow.value = categoriesList.toList()

            gettingCategories = false
        } else {
            Toast.makeText(getApplication(),
                "Error: couldn't get the categories: $code", Toast.LENGTH_SHORT).show()

            gettingCategories = false
        }
    }

    fun getDishes() {
        if (gettingDishes) {
            return
        } else {
            gettingDishes = true
        }
        allDishesList.clear()
        dishesList.clear()
        dishesMutableFlow.value = null

        repository.getDishes()
    }

    override fun setDishes(data: List<Dish>?, code: Int) {
        if (data != null) {
            allDishesList.addAll(data)
            setTag(selectedTag)


            for (dish in data) {
                for (tag in dish.tegs) {
                    if (tag !in dishesTagsList) {
                        dishesTagsList.add(tag)
                    }
                }
            }
            dishesTagsMutableFlow.value = dishesTagsList.toList()

            gettingDishes = false
        } else {
            Toast.makeText(getApplication(),
                "Error: couldn't get the dishes: $code", Toast.LENGTH_SHORT).show()
            gettingDishes = false
        }
    }

    fun setTag(newTag: String) {
        dishesMutableFlow.value = null
        selectedTag = newTag
        val newDishesList: MutableList<Dish> = mutableListOf()

        for (i in allDishesList.indices) {
            if (allDishesList[i].tegs.contains(newTag)) {
                newDishesList.add(allDishesList[i])
            }
        }
        dishesList = newDishesList
        dishesMutableFlow.value = dishesList.toList()
    }

    fun getTag(): String {
        return selectedTag
    }
}