package com.example.loginwithapi
import com.example.loginwithapi.UserRepository
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject

import kotlinx.coroutines.launch


class LoginViewModel (private val userRepository: UserRepository): ViewModel() {


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






        fun fetchProducts(token: String, onProductsReceived: (List<JsonObject>?) -> Unit) {
            viewModelScope.launch {
                val token = userRepository.getToken()
                if (token != null) {
                    try {

                        val products = userRepository.fetchProducts(token)
                        onProductsReceived(products)

                    } catch (e: Exception) {
                        println("Error fetching products: ${e.message}")
                        onProductsReceived(null) // Indicate failure
                    }
                }
            }
        }

}


