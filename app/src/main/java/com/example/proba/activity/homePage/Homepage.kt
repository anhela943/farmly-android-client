package com.example.proba.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.proba.R
import com.example.proba.activity.homePage.CategoryView
import com.example.proba.activity.product.ProductView
import com.example.proba.model.ProductUi
import com.example.proba.viewmodel.FavoritesViewModel
import com.example.proba.navigation.MainRoutes

@Composable
fun HomePage(
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
                    alpha = 0.5f
                },
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            SearchView(
                onMenuClick = { showFilter = true },
                onSearchClick = { /* TODO */ },
                onSearchChange = { query -> /* TODO */ }
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(170.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.onboarding2),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Categories",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.darkGreenTxt),
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CategoryView(name = "Dairy", imageCategory = R.drawable.logo)
                CategoryView(name = "Meat", imageCategory = R.drawable.logo)
                CategoryView(name = "Fruit", imageCategory = R.drawable.logo)
                CategoryView(name = "Fruit", imageCategory = R.drawable.logo)
                CategoryView(name = "Fruit", imageCategory = R.drawable.logo)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Products",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.darkGreenTxt)
                )
                Text(
                    text = "See all →",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.darkGreenTxt),
                    modifier = Modifier.clickable {
                        navController.navigate(MainRoutes.Explore)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(productRight) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val productLeft = ProductUi.from(
                        name = "Apples",
                        price = 180.0,
                        producer = "Proizvodjac",
                        producerReview = 4.2,
                        city = "Novi Sad",
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
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(productLeft) },
                        modifier = Modifier.weight(1f)
                    )

                    val productRight = ProductUi.from(
                        name = "Carrots",
                        price = 150.0,
                        producer = "Proizvodjac",
                        producerReview = 4.7,
                        city = "Beograd",
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
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(productRight) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
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
fun HomePagePreview() {
    HomePage(
        navController = rememberNavController()
    )
}
