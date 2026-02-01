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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.proba.util.Resource
import com.example.proba.viewmodel.CategoryViewModel
import com.example.proba.viewmodel.FavoritesViewModel
import com.example.proba.navigation.MainRoutes
import kotlinx.coroutines.delay

@Composable
fun HomePage(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val categoriesState by categoryViewModel.categories.collectAsState()
    var showFilter by remember { mutableStateOf(false) }
    val adImages = remember {
        listOf(R.drawable.onboarding1, R.drawable.onboarding2, R.drawable.onboarding3)
    }
    var adIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3500)
            adIndex = (adIndex + 1) % adImages.size
        }
    }

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
            Spacer(modifier = Modifier.height(12.dp))
            SearchView(
                onMenuClick = { showFilter = true },
                onSearchClick = { /* TODO */ },
                onSearchChange = { query -> /* TODO */ }
            )
            //reklama
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
                    painter = painterResource(id = adImages[adIndex]),
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
                when (categoriesState) {
                    is Resource.Success -> {
                        (categoriesState as Resource.Success).data.forEach { category ->
                            CategoryView(
                                name = category.name,
                                imageUrl = category.imageUrl,
                                onClick = { navController.navigate(MainRoutes.Explore) }
                            )
                        }
                    }
                    is Resource.Error -> {
                        Text(
                            text = "Failed to load categories",
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                    is Resource.Loading -> {
                        Text(
                            text = "Loading...",
                            fontSize = 14.sp,
                            color = colorResource(R.color.darkGreenTxt)
                        )
                    }
                }
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
                val product1 = ProductUi.from(
                    name = "Tomatoes",
                    price = 200.0,
                    producer = "Proizvodjac",
                    producerReview = 4.5,
                    city = "Niš",
                    imageProducer = R.drawable.user,
                    imageProduct = R.drawable.basket
                )
                ProductView(
                    productName = product1.name,
                    price = product1.price,
                    producer = product1.producer,
                    producerReview = product1.producerReview,
                    city = product1.city,
                    imageProducer = product1.imageProducer,
                    imageProduct = product1.imageProduct,
                    isFavorite = favoritesViewModel.isFavorite(product1),
                    onProductClick = { navController.navigate(MainRoutes.Product) },
                    onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                    onFavoriteClick = { favoritesViewModel.toggleFavorite(product1) },
                    modifier = Modifier.fillMaxWidth()
                )

                val product2 = ProductUi.from(
                    name = "Potatoes",
                    price = 220.0,
                    producer = "Proizvodjac",
                    producerReview = 4.5,
                    city = "Niš",
                    imageProducer = R.drawable.user,
                    imageProduct = R.drawable.basket
                )
                ProductView(
                    productName = product2.name,
                    price = product2.price,
                    producer = product2.producer,
                    producerReview = product2.producerReview,
                    city = product2.city,
                    imageProducer = product2.imageProducer,
                    imageProduct = product2.imageProduct,
                    isFavorite = favoritesViewModel.isFavorite(product2),
                    onProductClick = { navController.navigate(MainRoutes.Product) },
                    onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                    onFavoriteClick = { favoritesViewModel.toggleFavorite(product2) },
                    modifier = Modifier.fillMaxWidth()
                )

                val product3 = ProductUi.from(
                    name = "Apples",
                    price = 180.0,
                    producer = "Proizvodjac",
                    producerReview = 4.2,
                    city = "Novi Sad",
                    imageProducer = R.drawable.user,
                    imageProduct = R.drawable.basket
                )
                ProductView(
                    productName = product3.name,
                    price = product3.price,
                    producer = product3.producer,
                    producerReview = product3.producerReview,
                    city = product3.city,
                    imageProducer = product3.imageProducer,
                    imageProduct = product3.imageProduct,
                    isFavorite = favoritesViewModel.isFavorite(product3),
                    onProductClick = { navController.navigate(MainRoutes.Product) },
                    onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                    onFavoriteClick = { favoritesViewModel.toggleFavorite(product3) },
                    modifier = Modifier.fillMaxWidth()
                )

                val product4 = ProductUi.from(
                    name = "Carrots",
                    price = 150.0,
                    producer = "Proizvodjac",
                    producerReview = 4.7,
                    city = "Beograd",
                    imageProducer = R.drawable.user,
                    imageProduct = R.drawable.basket
                )
                ProductView(
                    productName = product4.name,
                    price = product4.price,
                    producer = product4.producer,
                    producerReview = product4.producerReview,
                    city = product4.city,
                    imageProducer = product4.imageProducer,
                    imageProduct = product4.imageProduct,
                    isFavorite = favoritesViewModel.isFavorite(product4),
                    onProductClick = { navController.navigate(MainRoutes.Product) },
                    onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                    onFavoriteClick = { favoritesViewModel.toggleFavorite(product4) },
                    modifier = Modifier.fillMaxWidth()
                )
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
