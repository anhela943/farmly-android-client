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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proba.R
import com.example.proba.activity.FilterView
import com.example.proba.activity.SearchView
import com.example.proba.activity.bottomBarView
import com.example.proba.model.ProductUi
import com.example.proba.util.Resource
import com.example.proba.viewmodel.FavoritesViewModel
import com.example.proba.viewmodel.ProductViewModel
import com.example.proba.viewmodel.CategoryViewModel
import com.example.proba.navigation.MainRoutes
import com.example.proba.data.repository.ProductFilters
import kotlinx.coroutines.delay

@Composable
fun ProducesScreenView(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    productViewModel: ProductViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel()
) {
    var showFilter by remember { mutableStateOf(false) }
    val productsState by productViewModel.products.collectAsState()
    val favoriteIds by favoritesViewModel.favoriteIds.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }
    var priceRange by remember { mutableStateOf(0f..5000f) }
    var cityFilter by remember { mutableStateOf("") }

    // Debounce search to avoid excessive API calls
    LaunchedEffect(searchQuery, selectedCategoryId, cityFilter, priceRange) {
        delay(500)
        productViewModel.applyFilters(
            ProductFilters(
                city = cityFilter.ifBlank { null },
                priceFrom = if (priceRange.start > 0) priceRange.start else null,
                priceTo = if (priceRange.endInclusive < 5000f) priceRange.endInclusive else null,
                value = searchQuery.ifBlank { null },
                categoryId = selectedCategoryId
            )
        )
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
                        onSearchClick = { },
                        onSearchChange = { query -> searchQuery = query }
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
                when (productsState) {
                    is Resource.Success -> {
                        (productsState as Resource.Success).data.forEach { item ->
                            val product = ProductUi.fromApi(item)
                            ProductView(
                                productName = product.name,
                                price = product.price,
                                producer = product.producer,
                                producerReview = product.producerReview,
                                city = product.city,
                                imageUrl = product.imageUrl,
                                producerImageUrl = product.producerImageUrl,
                                isFavorite = favoriteIds.contains(product.id),
                                onProductClick = { navController.navigate(MainRoutes.productRoute(product.id)) },
                                onProducerClick = {
                                    product.producerId?.let {
                                        navController.navigate(MainRoutes.profileProducerRoute(it))
                                    }
                                },
                                onFavoriteClick = { favoritesViewModel.toggleFavorite(product) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    is Resource.Error -> {
                        Text(
                            text = "Failed to load products",
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
                .zIndex(2f),
            categoryViewModel = categoryViewModel,
            initialCategoryId = selectedCategoryId,
            onFilterApply = { filters ->
                selectedCategoryId = filters.categoryId
                filters.priceFrom?.let { priceRange = it..(priceRange.endInclusive) }
                filters.priceTo?.let { priceRange = (priceRange.start)..it }
                cityFilter = filters.city ?: ""
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProducesScreenViewPreview() {
    ProducesScreenView(
        navController = rememberNavController(),
        favoritesViewModel = viewModel()
    )
}
