package com.example.loginwithapi

import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import okhttp3.Request
import com.google.gson.Gson
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
    suspend fun login(username: String, password: String): String? {
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
                        loginResponse.token // Return the token
                    } else {
                        Log.e("AccessingApi", "Login failed: ${response.code}")
                        null // Indicate failure
                    }
                }
            } catch (e: Exception) {
                Log.e("AccessingApi", "Error during login: ${e.message}")
                null // Indicate failure
            }
        }
    }
}

