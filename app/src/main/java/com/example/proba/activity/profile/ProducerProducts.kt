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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val productLeft = ProductUi.from(
                            name = "Tomatoes",
                            price = 200.0,
                            producer = "Proizvodjac",
                            producerReview = 4.5,
                            city = "Niš",
                            imageProducer = R.drawable.user,
                            imageProduct = R.drawable.basket
                        )
                        ProductView(
                            productName = productLeft.name,
                            price = productLeft.price,
                            producer = productLeft.producer,
                            producerReview = productLeft.producerReview,
                            city = productLeft.city,
                            imageProducer = productLeft.imageProducer,
                            imageProduct = productLeft.imageProduct,
                            isFavorite = favoritesViewModel.isFavorite(productLeft),
                            onProductClick = { navController.navigate(MainRoutes.Product) },
                            onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                            onFavoriteClick = { favoritesViewModel.toggleFavorite(productLeft) },
                            modifier = Modifier.weight(1f)
                        )
                        val productRight = ProductUi.from(
                            name = "Potatoes",
                            price = 220.0,
                            producer = "Proizvodjac",
                            producerReview = 4.5,
                            city = "Niš",
                            imageProducer = R.drawable.user,
                            imageProduct = R.drawable.basket
                        )
                        ProductView(
                            productName = productRight.name,
                            price = productRight.price,
                            producer = productRight.producer,
                            producerReview = productRight.producerReview,
                            city = productRight.city,
                            imageProducer = productRight.imageProducer,
                            imageProduct = productRight.imageProduct,
                            isFavorite = favoritesViewModel.isFavorite(productRight),
                            onProductClick = { navController.navigate(MainRoutes.Product) },
                            onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                            onFavoriteClick = { favoritesViewModel.toggleFavorite(productRight) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val productLeft = ProductUi.from(
                            name = "Strawberry",
                            price = 250.0,
                            producer = "Proizvodjac",
                            producerReview = 4.5,
                            city = "Niš",
                            imageProducer = R.drawable.user,
                            imageProduct = R.drawable.basket
                        )
                        ProductView(
                            productName = productLeft.name,
                            price = productLeft.price,
                            producer = productLeft.producer,
                            producerReview = productLeft.producerReview,
                            city = productLeft.city,
                            imageProducer = productLeft.imageProducer,
                            imageProduct = productLeft.imageProduct,
                            isFavorite = favoritesViewModel.isFavorite(productLeft),
                            onProductClick = { navController.navigate(MainRoutes.Product) },
                            onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                            onFavoriteClick = { favoritesViewModel.toggleFavorite(productLeft) },
                            modifier = Modifier.weight(1f)
                        )
                        val productRight = ProductUi.from(
                            name = "Plum",
                            price = 300.0,
                            producer = "Proizvodjac",
                            producerReview = 4.5,
                            city = "Niš",
                            imageProducer = R.drawable.user,
                            imageProduct = R.drawable.basket
                        )
                        ProductView(
                            productName = productRight.name,
                            price = productRight.price,
                            producer = productRight.producer,
                            producerReview = productRight.producerReview,
                            city = productRight.city,
                            imageProducer = productRight.imageProducer,
                            imageProduct = productRight.imageProduct,
                            isFavorite = favoritesViewModel.isFavorite(productRight),
                            onProductClick = { navController.navigate(MainRoutes.Product) },
                            onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                            onFavoriteClick = { favoritesViewModel.toggleFavorite(productRight) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val productLeft = ProductUi.from(
                            name = "Potatoes",
                            price = 220.0,
                            producer = "Proizvodjac",
                            producerReview = 4.5,
                            city = "Niš",
                            imageProducer = R.drawable.user,
                            imageProduct = R.drawable.basket
                        )
                        ProductView(
                            productName = productLeft.name,
                            price = productLeft.price,
                            producer = productLeft.producer,
                            producerReview = productLeft.producerReview,
                            city = productLeft.city,
                            imageProducer = productLeft.imageProducer,
                            imageProduct = productLeft.imageProduct,
                            isFavorite = favoritesViewModel.isFavorite(productLeft),
                            onProductClick = { navController.navigate(MainRoutes.Product) },
                            onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                            onFavoriteClick = { favoritesViewModel.toggleFavorite(productLeft) },
                            modifier = Modifier.weight(1f)
                        )
                        val productRight = ProductUi.from(
                            name = "Strawberry",
                            price = 250.0,
                            producer = "Proizvodjac",
                            producerReview = 4.5,
                            city = "Niš",
                            imageProducer = R.drawable.user,
                            imageProduct = R.drawable.basket
                        )
                        ProductView(
                            productName = productRight.name,
                            price = productRight.price,
                            producer = productRight.producer,
                            producerReview = productRight.producerReview,
                            city = productRight.city,
                            imageProducer = productRight.imageProducer,
                            imageProduct = productRight.imageProduct,
                            isFavorite = favoritesViewModel.isFavorite(productRight),
                            onProductClick = { navController.navigate(MainRoutes.Product) },
                            onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                            onFavoriteClick = { favoritesViewModel.toggleFavorite(productRight) },
                            modifier = Modifier.weight(1f)
                        )
                    }
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
