package com.example.loginwithapi
import android.content.Context
import android.util.Log
import android.widget.Toast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class LoginViewModel (private val userRepository: UserRepository): ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()




    fun login(username: String, password: String, onTokenReceived: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val token = userRepository.login(username, password)
                token?.let { userRepository.saveToken(it) }
                onTokenReceived(token.toString())
            } catch (e: Exception) {
                // Handle the error (e.g., log it, show a message, etc.)
                println("Login failed: ${e.message}")
                onTokenReceived(null) // Indicate failure
            }
        }}


    fun getToken(): String? {
        return userRepository.getToken()
    }



    private var shouldRefetchProducts = true

    fun fetchProducts() {
        viewModelScope.launch {
            val token = getToken()
            if (token != null && shouldRefetchProducts) {
                try {
                    val products = userRepository.fetchProducts(token)
                    _products.value = products // Update the state flow with the product list
                    Log.d("FetchProducts", "Fetched products: $products")
                    shouldRefetchProducts = false
                } catch (e: Exception) {
                    Log.e("FetchProducts", "Error fetching products: ${e.message}")
                }
            }
        }
    }

    fun deleteProduct(context: Context, productId: String, onResult: (Boolean) -> Unit) {
        val token = getToken()
        if (token == null) {
            Log.e("DeleteProductViewModel", "Token is null")
            onResult(false)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val success = userRepository.deleteProduct(token, productId)
                if (success) {
                    // Update the local state directly
                    val updatedProducts = _products.value.filter { it.id != productId }
                    _products.value = updatedProducts
                    Log.d("DeleteProductViewModel", "Product deleted: $productId")

                    // Show Toast on Main Thread
                    showToast(context, "Product deleted successfully")
                } else {
                    showToast(context, "Failed to delete product")
                }
                onResult(success)
            } catch (e: Exception) {
                Log.e("DeleteProductViewModel", "Error deleting product: ${e.message}")
                showToast(context, "Failed to delete product")
                onResult(false)
            }
        }
    }

    private suspend fun showToast(context: Context, message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }


}


