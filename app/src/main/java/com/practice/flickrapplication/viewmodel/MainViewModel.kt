package com.practice.flickrapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.flickrapplication.network.ImageItem
import com.practice.flickrapplication.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // Use StateFlow for images list
    private val _images = MutableStateFlow<List<ImageItem>>(emptyList())
    val images: StateFlow<List<ImageItem>> = _images

    // Use StateFlow for loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Selected image state to pass to the detail screen
    private val _selectedImage = MutableStateFlow<ImageItem?>(null)
    val selectedImage: StateFlow<ImageItem?> = _selectedImage

    val searchText = MutableStateFlow("")

    // Search for images based on tags
    fun searchImages(tags: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response =  RetrofitInstance.api.getImages(tags)
                Log.d("MainViewModel", "Response: $response")
                _images.value = response.items
            } catch (e: Exception) {
                // Handle error here if needed
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Select an image for the detail view
    fun selectImage(item: ImageItem) {
        _selectedImage.value = item
    }

    // Clear selected image
    fun clearSelectedImage() {
        _selectedImage.value = null
    }
}
