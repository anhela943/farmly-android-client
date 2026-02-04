package com.example.proba.activity.favorite

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proba.R
import com.example.proba.activity.SearchView
import com.example.proba.activity.bottomBarView
import com.example.proba.activity.product.ProductView
import com.example.proba.util.Resource
import com.example.proba.viewmodel.FavoritesViewModel
import com.example.proba.navigation.MainRoutes

@Composable
fun FavoriteScreenView(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel
) {
    val favoritesState by favoritesViewModel.favoritesState.collectAsState()
    val favoriteIds by favoritesViewModel.favoriteIds.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgorund),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.25f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
        ) {
            Spacer(modifier = Modifier.height(46.dp))

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
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Favorites",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.darkGreenTxt),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.size(30.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when (val state = favoritesState) {
                    is Resource.Loading -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = colorResource(R.color.darkGreenTxt)
                            )
                        }
                    }
                    is Resource.Error -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = state.message ?: "Error loading favorites",
                                fontSize = 18.sp,
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    is Resource.Success -> {
                        val favorites = state.data
                        if (favorites.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 40.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.favorite),
                                    contentDescription = "No favorites",
                                    modifier = Modifier.size(68.dp),
                                    colorFilter = ColorFilter.tint(colorResource(R.color.darkGreenTxt))
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No favorites",
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorResource(R.color.darkGreenTxt)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Add to favorites by clicking the heart icon",
                                    fontSize = 18.sp,
                                    color = colorResource(R.color.darkGreenTxt)
                                )
                            }
                        } else {
                            favorites.forEach { item ->
                                ProductView(
                                    productName = item.name,
                                    price = item.price,
                                    producer = item.producer,
                                    producerReview = item.producerReview,
                                    city = item.city,
                                    imageUrl = item.imageUrl,
                                    producerImageUrl = item.producerImageUrl,
                                    isFavorite = favoriteIds.contains(item.id),
                                    onProductClick = {
                                        navController.navigate(MainRoutes.productRoute(item.id))
                                    },
                                    onProducerClick = {
                                        item.producerId?.let {
                                            navController.navigate(MainRoutes.profileProducerRoute(it))
                                        }
                                    },
                                    onFavoriteClick = {
                                        favoritesViewModel.toggleFavorite(item.id)
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
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
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenViewPreview() {
    FavoriteScreenView(
        navController = rememberNavController(),
        favoritesViewModel = viewModel()
    )
}
