package com.example.loginwithapi

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String)
//data class Product( val id: Int, val name: String, val price: Double) // Adjust fields as needed