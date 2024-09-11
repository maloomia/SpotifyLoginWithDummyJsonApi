package com.example.loginwithapi

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String)
data class Product(
    val id: String,
    val title: String
)