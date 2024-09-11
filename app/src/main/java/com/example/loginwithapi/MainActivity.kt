package com.example.loginwithapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.loginwithapi.ui.theme.LoginWithApiTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accessingApi = AccessingApi(this)
        val userRepository = UserRepository(accessingApi, this)
        viewModel = LoginViewModel(userRepository)

        enableEdgeToEdge()
        setContent {
            LoginWithApiTheme {

                LoginWithApiTheme {
                    AppNavHost(viewModel)
                }

                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(viewModel: LoginViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(viewModel, navController) // Pass the navController
        }
        composable("products") {
            ProductListScreen(viewModel) // You can pass products here if needed
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginWithApiTheme {
        Greeting("Android")
    }
}


