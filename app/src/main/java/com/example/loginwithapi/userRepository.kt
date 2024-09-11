package com.example.loginwithapi

import android.content.Context
import com.google.gson.JsonObject

class UserRepository (private val api: AccessingApi, private val context: Context) {

    fun saveToken(token: String) {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", null)
    }

    suspend fun login(username: String, password: String): String? {
        return api.login(username, password)
    }

    suspend fun fetchProducts(token: String): List<Product> {
        val jsonResponse: List<JsonObject>? = api.fetchProducts(token)

        return jsonResponse?.map { jsonObject ->
            Product(
                id = jsonObject.get("id").asString,
                title = jsonObject.get("title").asString // Extract title from JsonObject
            )
        } ?: emptyList()
    }

    suspend fun deleteProduct(token: String, productId: String): Boolean {

        return api.deleteProduct(token, productId)
    }


}