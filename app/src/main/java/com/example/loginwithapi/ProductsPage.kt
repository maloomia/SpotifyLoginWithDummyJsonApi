package com.example.loginwithapi

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun ProductListScreen(viewModel: LoginViewModel) {
    // Assume you have a ViewModel to hold the products
    val products by viewModel.products.collectAsState(initial = emptyList())

    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    LazyColumn(
        modifier = Modifier
            .padding(30.dp, 80.dp, 30.dp, 0.dp)
            .fillMaxSize(),
        horizontalAlignment= Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {  item {
        Text(
            text = "Products",
            style = MaterialTheme.typography.h5,
            // You can choose a different style if you prefer
            modifier = Modifier.padding(bottom = 16.dp),

        )
    }
        if (products.isEmpty()) {
            item {
                Text(text = "No products available.", style = MaterialTheme.typography.h6)
            }
        } else {

            items(products) { product ->

                Card(
                    shape = RoundedCornerShape(5.dp),
                    elevation = CardDefaults.cardElevation(4.dp),

                    modifier = Modifier.padding(5.dp)
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(containerColor = Color(189, 209, 228))
                ) {
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                            .fillMaxWidth(),// Add padding for aesthetics
                    )

                    Button(
                        onClick = {
                            Log.d("DeleteButton", "Button clicked for product ID: ${product.id}")
                            loading = true

                            // Call delete method in ViewModel
                            viewModel.deleteProduct(context, product.id) { success ->
                                loading = false // Stop loading

                                if (success) {
                                    // Show success message

                                    viewModel.fetchProducts()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
    }

