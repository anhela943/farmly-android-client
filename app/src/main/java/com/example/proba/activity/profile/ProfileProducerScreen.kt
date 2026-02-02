package com.example.proba.activity.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proba.R
import com.example.proba.activity.bottomBarView
import com.example.proba.activity.product.ProductView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.proba.model.ProductUi
import com.example.proba.viewmodel.FavoritesViewModel
import com.example.proba.navigation.MainRoutes
import com.example.proba.util.Resource
import com.example.proba.viewmodel.ProducerProfileViewModel

@Composable
fun ProfileProducerView(
    navController: NavController,
    userId: String?,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    if (userId == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Missing producer ID",
                fontSize = 16.sp,
                color = colorResource(R.color.grey)
            )
        }
        return
    }

    val producerProfileViewModel: ProducerProfileViewModel = viewModel(
        factory = ProducerProfileViewModel.Factory(userId)
    )
    val profileState = producerProfileViewModel.profileState
    val productsState = producerProfileViewModel.productsState
    val reviewsState = producerProfileViewModel.reviewsState

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
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.size(30.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(180f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 22.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = screenHeight - 40.dp),
                    shape = RoundedCornerShape(topStart =  120.dp, topEnd =  120.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.greenBackground)
                    )
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp, bottom = 120.dp, start = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (val state = profileState) {
                            is Resource.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 60.dp),
                                    contentAlignment = Alignment.Center
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
                                        .padding(vertical = 24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = state.message,
                                        fontSize = 14.sp,
                                        color = colorResource(R.color.grey),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Button(
                                        onClick = { producerProfileViewModel.loadAll() },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = colorResource(R.color.darkGreenTxt),
                                            contentColor = colorResource(R.color.white)
                                        )
                                    ) {
                                        Text(text = "Retry")
                                    }
                                }
                            }
                            is Resource.Success -> {
                                val profile = state.data
                                val ratingValue = profile.overallReview ?: 0.0
                                val ratingText = String.format("%.1f", ratingValue)

                                Text(
                                    text = profile.fullName,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.darkGreenTxt)
                                )
                                Text(
                                    text = profile.email,
                                    fontSize = 12.sp,
                                    color = colorResource(R.color.grey)
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.star),
                                            contentDescription = "Rating",
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "$ratingText (${profile.numberOfReviews ?: 0})",
                                            fontSize = 12.sp,
                                            color = colorResource(R.color.black)
                                        )
                                    }
                                    Text(
                                        text = profile.city ?: "",
                                        fontSize = 12.sp,
                                        color = colorResource(R.color.black)
                                    )
                                }

                                Spacer(modifier = Modifier.height(18.dp))

                                Text(
                                    text = profile.description ?: "",
                                    fontSize = 13.sp,
                                    textAlign = TextAlign.Center,
                                    color = colorResource(R.color.black)
                                )

                                Spacer(modifier = Modifier.height(32.dp))

                                Text(
                                    text = "Products",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.darkGreenTxt),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 4.dp, bottom = 6.dp)
                                )

                                when (val products = productsState) {
                                    is Resource.Loading -> {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 16.dp),
                                            contentAlignment = Alignment.Center
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
                                                .padding(vertical = 12.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = products.message,
                                                fontSize = 13.sp,
                                                color = colorResource(R.color.grey),
                                                textAlign = TextAlign.Center
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Button(
                                                onClick = { producerProfileViewModel.loadProducts() },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = colorResource(R.color.darkGreenTxt),
                                                    contentColor = colorResource(R.color.white)
                                                )
                                            ) {
                                                Text(text = "Retry")
                                            }
                                        }
                                    }
                                    is Resource.Success -> {
                                        if (products.data.isEmpty()) {
                                            Text(
                                                text = "No products yet",
                                                fontSize = 14.sp,
                                                color = colorResource(R.color.grey),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 12.dp),
                                                textAlign = TextAlign.Center
                                            )
                                        } else {
                                            products.data.forEach { item ->
                                                val product = ProductUi.fromApi(item)
                                                ProductView(
                                                    productName = product.name,
                                                    price = product.price,
                                                    producer = product.producer,
                                                    producerReview = product.producerReview,
                                                    city = product.city,
                                                    imageUrl = product.imageUrl,
                                                    producerImageUrl = product.producerImageUrl,
                                                    isFavorite = favoritesViewModel.isFavorite(product),
                                                    onProductClick = {
                                                        navController.navigate(MainRoutes.productRoute(product.id))
                                                    },
                                                    onProducerClick = {
                                                        val targetId = product.producerId ?: userId
                                                        navController.navigate(
                                                            MainRoutes.profileProducerRoute(targetId)
                                                        )
                                                    },
                                                    onFavoriteClick = {
                                                        favoritesViewModel.toggleFavorite(product)
                                                    },
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                                Spacer(modifier = Modifier.height(12.dp))
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(32.dp))

                                Text(
                                    text = "Reviews",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.darkGreenTxt),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 4.dp, bottom = 6.dp)
                                )

                                when (val reviews = reviewsState) {
                                    is Resource.Loading -> {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 16.dp),
                                            contentAlignment = Alignment.Center
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
                                                .padding(vertical = 12.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = reviews.message,
                                                fontSize = 13.sp,
                                                color = colorResource(R.color.grey),
                                                textAlign = TextAlign.Center
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Button(
                                                onClick = { producerProfileViewModel.loadReviews() },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = colorResource(R.color.darkGreenTxt),
                                                    contentColor = colorResource(R.color.white)
                                                )
                                            ) {
                                                Text(text = "Retry")
                                            }
                                        }
                                    }
                                    is Resource.Success -> {
                                        if (reviews.data.isEmpty()) {
                                            Text(
                                                text = "No reviews yet",
                                                fontSize = 14.sp,
                                                color = colorResource(R.color.grey),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 12.dp),
                                                textAlign = TextAlign.Center
                                            )
                                        } else {
                                            Column(
                                                modifier = Modifier.fillMaxHeight(),
                                                verticalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                reviews.data.forEachIndexed { index, review ->
                                                    ReviewItem(
                                                        name = review.user.fullName,
                                                        rating = review.user.rating.toString(),
                                                        text = review.content,
                                                        imageUrl = review.user.imageUrl
                                                    )
                                                    if (index != reviews.data.lastIndex) {
                                                        Spacer(modifier = Modifier.height(10.dp))
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .size(132.dp)
                        .offset(y = (-51).dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    val imageUrl = (profileState as? Resource.Success)?.data?.imageUrl
                    if (imageUrl != null) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Producer photo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = "Producer photo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            bottomBarView(navController)
        }
    }
}

@Composable
private fun ReviewItem(
    name: String,
    rating: String,
    text: String,
    imageUrl: String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (imageUrl != null) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = name,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.user),
                            contentDescription = name,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.star),
                                contentDescription = "Rating",
                                modifier = Modifier.size(11.dp),
                                colorFilter = ColorFilter.tint(Color(0xFFFFC107))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = rating,
                                fontSize = 11.sp,
                                color = colorResource(R.color.black)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = text,
                    fontSize = 10.sp,
                    color = colorResource(R.color.black)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileProducerViewPreview() {
    ProfileProducerView(
        navController = rememberNavController(),
        userId = "1"
    )
}
