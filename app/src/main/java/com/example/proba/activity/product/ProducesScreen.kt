package com.example.proba.activity.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proba.R
import com.example.proba.activity.FilterView
import com.example.proba.activity.SearchView
import com.example.proba.activity.bottomBarView
import com.example.proba.model.ProductUi
import com.example.proba.viewmodel.FavoritesViewModel
import com.example.proba.navigation.MainRoutes

@Composable
fun ProducesScreenView(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    var showFilter by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgorund),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = 0.25f
                },
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.arrow),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(180f)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier.weight(1f)) {
                    SearchView(
                        onMenuClick = { showFilter = true },
                        onSearchClick = { /* TODO */ },
                        onSearchChange = { query -> /* TODO */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Products",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.darkGreenTxt),
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
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

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            bottomBarView(navController)
        }

        FilterView(
            isOpen = showFilter,
            onDismiss = { showFilter = false },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProducesScreenViewPreview() {
    ProducesScreenView(
        navController = rememberNavController()
    )
}
