package com.example.loginwithapi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val AccessingApi = AccessingApi()

    fun login(username: String, password: String, onTokenReceived: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val token = AccessingApi.login(username, password)
                onTokenReceived(token.toString())
            } catch (e: Exception) {
                // Handle the error (e.g., log it, show a message, etc.)
                println("Login failed: ${e.message}")
                onTokenReceived(null) // Indicate failure
            }
        }}
        fun fetchProducts(token: String, onProductsReceived: (List<JsonObject>?) -> Unit) {
            viewModelScope.launch {
                try {

                    val products = AccessingApi.fetchProducts(token)
                    onProductsReceived(products)
                    Log.d("LoginViewModel", "Fetching products with token: $token")
                } catch (e: Exception) {
                    println("Error fetching products: ${e.message}")
                    onProductsReceived(null) // Indicate failure
                }
            }
        }

}


