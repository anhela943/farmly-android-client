package com.example.proba.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proba.data.model.response.CategoryItem
import com.example.proba.data.model.response.ProductResponse
import com.example.proba.data.repository.CategoryRepository
import com.example.proba.data.repository.ProductRepository
import com.example.proba.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class ProductAddViewModel : ViewModel() {
    private val productRepository = ProductRepository()
    private val categoryRepository = CategoryRepository()

    private val _categories = MutableStateFlow<Resource<List<CategoryItem>>>(Resource.Loading)
    val categories: StateFlow<Resource<List<CategoryItem>>> = _categories.asStateFlow()

    private val _createProductState = MutableStateFlow<Resource<ProductResponse>?>(null)
    val createProductState: StateFlow<Resource<ProductResponse>?> = _createProductState.asStateFlow()

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = Resource.Loading
            when (val result = categoryRepository.getCategories()) {
                is Resource.Success -> {
                    _categories.value = Resource.Success(result.data.categories)
                }
                is Resource.Error -> {
                    _categories.value = Resource.Error(result.message, result.errors)
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun createProduct(
        name: String,
        description: String,
        price: Double,
        categoryId: Int,
        imageUri: Uri,
        context: Context
    ) {
        viewModelScope.launch {
            _createProductState.value = Resource.Loading
            val imageFile = uriToFile(imageUri, context)
            if (imageFile == null) {
                _createProductState.value = Resource.Error("Failed to process selected image")
                return@launch
            }
            val result = productRepository.createProduct(
                name = name,
                description = description,
                price = price,
                categoryId = categoryId,
                imageFile = imageFile
            )
            _createProductState.value = result
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val extension = getFileExtension(uri, context)
            val tempFile = File.createTempFile("product_image_", ".$extension", context.cacheDir)
            tempFile.outputStream().use { output ->
                inputStream.copyTo(output)
            }
            inputStream.close()
            tempFile
        } catch (e: Exception) {
            null
        }
    }

    private fun getFileExtension(uri: Uri, context: Context): String {
        val mimeType = context.contentResolver.getType(uri)
        return when (mimeType) {
            "image/jpeg" -> "jpg"
            "image/png" -> "png"
            "image/webp" -> "webp"
            else -> "jpg"
        }
    }
}
