package com.example.proba.activity.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.proba.R
import com.example.proba.activity.bottomBarView

@Composable
fun ProductPageScreen() {
    Scaffold(
        bottomBar = {
            bottomBarView()
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ProductPageView(
                productName = "Sljive",
                price = 230,
                producerName = "Milena Petrovic",
                review = 2.3,
                city = "Nis",
                imageProduct = R.drawable.basket,
                imageProducer = R.drawable.user,
                onBackClick = { },
                onProducerClick = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductPageScreenPreview() {
    ProductPageScreen()
}
