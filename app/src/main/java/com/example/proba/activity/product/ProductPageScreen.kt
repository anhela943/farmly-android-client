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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proba.navigation.MainRoutes
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proba.model.ProductUi
import com.example.proba.viewmodel.FavoritesViewModel

@Composable
fun ProductPageScreen(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val product = ProductUi.from(
        name = "Sljive",
        price = 230.0,
        producer = "Milena Petrovic",
        producerReview = 2.3,
        city = "Nis",
        imageProduct = R.drawable.basket,
        imageProducer = R.drawable.user
    )

    Scaffold(
        bottomBar = {
            bottomBarView(navController)
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ProductPageView(
                productName = product.name,
                price = product.price,
                producerName = product.producer,
                review = product.producerReview,
                city = product.city,
                imageProduct = product.imageProduct,
                imageProducer = product.imageProducer,
                isFavorite = favoritesViewModel.isFavorite(product),
                onBackClick = { navController.popBackStack() },
                onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                onFavoriteClick = { favoritesViewModel.toggleFavorite(product) },
                onContactProducerClick = { navController.navigate(MainRoutes.MessageChat) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductPageScreenPreview() {
    ProductPageScreen(rememberNavController())
}
