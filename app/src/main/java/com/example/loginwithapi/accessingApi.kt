package com.example.loginwithapi

import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType


class AccessingApi {

    private val client = OkHttpClient()
    private val gson = Gson()


    @OptIn(UnstableApi::class)
    suspend fun login(username: String, password: String) {
        val json = gson.toJson(LoginRequest(username, password))

        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())


        val request = Request.Builder()
            .url("https://dummyjson.com/auth/login") // Ensure this URL is correct
            .post(requestBody)

            .build()

        return withContext(Dispatchers.IO) {
            try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        val loginResponse = gson.fromJson(responseBody, LoginResponse::class.java)

                         println("token is: ${loginResponse.token}")
                    } else {
                        Log.e("AccessingApi", "Login failed: ${response.code}")
                        null // Indicate failure
                    }
                }
            } catch (e: Exception) {
                Log.e("AccessingApi", "Error during login: ${e.message}")
                null // Indicate failure
            }
        }}



    suspend fun fetchProducts(token: String): List<JsonObject>? {
        println("Fetching products with token: $token")

        val request = Request.Builder()
            .url("https://dummyjson.com/auth/products")
            .addHeader(
                "Authorization", "Bearer $token"
            )
            .get()
            .build()

        return withContext(Dispatchers.IO) {
            try {
                client.newCall(request).execute().use { response ->

                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        println("Response Body: $responseBody")


                        val jsonObject = JsonParser.parseString(responseBody).asJsonObject

                        // Extract the products list from the JSON object
                        val productsJsonArray = jsonObject.getAsJsonArray("products")

                        // Convert the JSON array to a list of JsonObjects
                        productsJsonArray.map { it.asJsonObject }
                    } else {
                        println("Fetch products failed: ${response.code}")
                        null // Indicate failure
                    }
                }
            } catch (e: Exception) {
                println("Error during fetch: ${e.message}")
                null // Indicate failure
            }




    }
        }}



