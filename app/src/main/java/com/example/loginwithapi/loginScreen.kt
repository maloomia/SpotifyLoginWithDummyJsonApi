package com.example.loginwithapi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }


    Column (modifier = Modifier
        .fillMaxSize() // Make the column fill the entire screen
        .background(color = Color.Black), // Set the background color to black

        horizontalAlignment = Alignment.CenterHorizontally){

        Image(painter = painterResource(id = R.drawable.spotify_icon_svg) , contentDescription ="picture",

            Modifier
                .padding(50.dp, 50.dp, 50.dp, 5.dp)
                .height(150.dp)
                .width(150.dp),

            )

        androidx.compose.material3.Text(text = "Spotify", color = Color.White, fontSize = 45.sp)


        androidx.compose.material3.OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { androidx.compose.material3.Text("Username") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                focusedContainerColor = Color.Black,
                focusedBorderColor = Color.DarkGray,
                unfocusedTextColor = Color.DarkGray,

                ),
            shape = RoundedCornerShape(30.dp),

            modifier = Modifier.fillMaxWidth()
                .padding(60.dp, 50.dp, 60.dp, 0.dp),// Optional: to make it responsive
        )

        androidx.compose.material3.OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { androidx.compose.material3.Text("Password") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                focusedContainerColor = Color.Black,
                focusedBorderColor = Color.DarkGray,
                unfocusedTextColor = Color.DarkGray,

                ),
            shape = RoundedCornerShape(30.dp),

            modifier = Modifier.fillMaxWidth()
                .padding(60.dp, 30.dp, 60.dp, 0.dp),// Optional: to make it responsive
        )

        androidx.compose.material3.Button(
            onClick = {
                if (username.isNotBlank() && password.isNotBlank()) {
                    loading = true  // Start loading
                    viewModel.login(username, password) { token ->
                        loading = false  // Stop loading

                        if (token != null) {
                            viewModel.fetchProducts(token) { products ->
                                if (products != null) {
                                    println("Products: $products")
                                    // Handle showing products to the user
                                } else {
                                    println("Error fetching products.")
                                }
                            }
                        } else {
                            println("Login failed.")
                        }
                    }
                } else {
                    println("Username and password cannot be empty.")
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(29, 218, 99) // Change this to your desired color
            ),
            modifier = Modifier.padding(30.dp, 80.dp, 30.dp, 0.dp)
                .width(300.dp)
                .height(60.dp),
        ) {
            Text(
                text = "Log In",
                color = Color.White,
                fontSize = 25.sp
            )
        }

        // Show loading indicator when loading is true
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
    }
}






