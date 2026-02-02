package com.example.proba.activity.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.proba.R
import com.example.proba.activity.bottomBarView
import com.example.proba.model.ProductUi
import com.example.proba.navigation.MainRoutes
import com.example.proba.util.Resource
import com.example.proba.viewmodel.FavoritesViewModel
import com.example.proba.viewmodel.ProductDetailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProductPageScreen(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = viewModel(),
    productDetailViewModel: ProductDetailViewModel
) {
    val productState by productDetailViewModel.productState.collectAsState()

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
            when (val state = productState) {
                is Resource.Success -> {
                    val data = state.data
                    val product = ProductUi(
                        id = data.id,
                        name = data.name,
                        price = data.price,
                        producer = data.producer.fullName,
                        producerReview = data.producer.overallReview.overallReview,
                        city = data.producer.city,
                        imageUrl = data.imageUrl
                    )
                    ProductPageView(
                        productName = data.name,
                        price = data.price,
                        producerName = data.producer.fullName,
                        review = data.producer.overallReview.overallReview ?: 0.0,
                        city = data.producer.city,
                        description = data.description,
                        imageUrl = data.imageUrl,
                        producerImageUrl = data.producer.imageUrl,
                        isFavorite = favoritesViewModel.isFavorite(product),
                        onBackClick = { navController.popBackStack() },
                        onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(product) },
                        onContactProducerClick = { navController.navigate(MainRoutes.MessageChat) }
                    )
                }
                is Resource.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                }
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Loading...",
                            fontSize = 14.sp,
                            color = colorResource(R.color.darkGreenTxt)
                        )
                    }
                }
            }
        }
    }
}
