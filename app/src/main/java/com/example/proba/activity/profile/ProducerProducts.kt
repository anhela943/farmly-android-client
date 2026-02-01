package com.example.proba.activity.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.proba.R
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proba.activity.product.ProductView
import com.example.proba.model.ProductUi
import com.example.proba.navigation.MainRoutes
import com.example.proba.viewmodel.FavoritesViewModel

@Composable
fun ProductsPageView(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgorund),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.25f },
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        colorResource(R.color.greenBackground),
                        RoundedCornerShape(topStart = 140.dp, topEnd = 140.dp)
                    )
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.size(30.dp))

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Products",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.darkGreenTxt)
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val product1 = ProductUi(
                        id = "1", name = "Tomatoes", price = 200.0,
                        producer = "Proizvodjac", producerReview = 4.5,
                        city = "Niš", imageUrl = ""
                    )
                    ProductView(
                        productName = product1.name,
                        price = product1.price,
                        producer = product1.producer,
                        producerReview = product1.producerReview,
                        city = product1.city,
                        imageUrl = product1.imageUrl,
                        isFavorite = favoritesViewModel.isFavorite(product1),
                        onProductClick = { navController.navigate(MainRoutes.Product) },
                        onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(product1) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    val product2 = ProductUi(
                        id = "2", name = "Potatoes", price = 220.0,
                        producer = "Proizvodjac", producerReview = 4.5,
                        city = "Niš", imageUrl = ""
                    )
                    ProductView(
                        productName = product2.name,
                        price = product2.price,
                        producer = product2.producer,
                        producerReview = product2.producerReview,
                        city = product2.city,
                        imageUrl = product2.imageUrl,
                        isFavorite = favoritesViewModel.isFavorite(product2),
                        onProductClick = { navController.navigate(MainRoutes.Product) },
                        onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(product2) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    val product3 = ProductUi(
                        id = "3", name = "Strawberry", price = 250.0,
                        producer = "Proizvodjac", producerReview = 4.5,
                        city = "Niš", imageUrl = ""
                    )
                    ProductView(
                        productName = product3.name,
                        price = product3.price,
                        producer = product3.producer,
                        producerReview = product3.producerReview,
                        city = product3.city,
                        imageUrl = product3.imageUrl,
                        isFavorite = favoritesViewModel.isFavorite(product3),
                        onProductClick = { navController.navigate(MainRoutes.Product) },
                        onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(product3) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    val product4 = ProductUi(
                        id = "4", name = "Plum", price = 300.0,
                        producer = "Proizvodjac", producerReview = 4.5,
                        city = "Niš", imageUrl = ""
                    )
                    ProductView(
                        productName = product4.name,
                        price = product4.price,
                        producer = product4.producer,
                        producerReview = product4.producerReview,
                        city = product4.city,
                        imageUrl = product4.imageUrl,
                        isFavorite = favoritesViewModel.isFavorite(product4),
                        onProductClick = { navController.navigate(MainRoutes.Product) },
                        onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(product4) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    val product5 = ProductUi(
                        id = "5", name = "Potatoes", price = 220.0,
                        producer = "Proizvodjac", producerReview = 4.5,
                        city = "Niš", imageUrl = ""
                    )
                    ProductView(
                        productName = product5.name,
                        price = product5.price,
                        producer = product5.producer,
                        producerReview = product5.producerReview,
                        city = product5.city,
                        imageUrl = product5.imageUrl,
                        isFavorite = favoritesViewModel.isFavorite(product5),
                        onProductClick = { navController.navigate(MainRoutes.Product) },
                        onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(product5) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    val product6 = ProductUi(
                        id = "6", name = "Strawberry", price = 250.0,
                        producer = "Proizvodjac", producerReview = 4.5,
                        city = "Niš", imageUrl = ""
                    )
                    ProductView(
                        productName = product6.name,
                        price = product6.price,
                        producer = product6.producer,
                        producerReview = product6.producerReview,
                        city = product6.city,
                        imageUrl = product6.imageUrl,
                        isFavorite = favoritesViewModel.isFavorite(product6),
                        onProductClick = { navController.navigate(MainRoutes.Product) },
                        onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(product6) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 26.dp, top = 74.dp)
                .size(30.dp)
                .zIndex(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(180f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductsPageView() {
    ProductsPageView(
        navController = rememberNavController()
    )
}
