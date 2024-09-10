package com.example.loginwithapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    fun login(username: String, password: String, onTokenReceived: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val token = AccessingApi().login(username, password)
                onTokenReceived(token)
            } catch (e: Exception) {
                // Handle the error (e.g., log it, show a message, etc.)
                println("Login failed: ${e.message}")
                onTokenReceived(null) // Indicate failure
            }
        }
    }
}


