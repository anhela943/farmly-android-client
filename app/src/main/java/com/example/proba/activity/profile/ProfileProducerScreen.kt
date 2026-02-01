package com.example.proba.activity.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.example.proba.model.ProductUi
import com.example.proba.viewmodel.FavoritesViewModel
import com.example.proba.navigation.MainRoutes

@Composable
fun ProfileProducerView(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
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
                        Text(
                            text = "Petra Petrovic Pr",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.darkGreenTxt)
                        )
                        Text(
                            text = "petra.petrovic@gmail.com",
                            fontSize = 12.sp,
                            color = colorResource(R.color.grey)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.clickable {
                                    navController.navigate(MainRoutes.ReviewPage)
                                },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.star),
                                    contentDescription = "Rating",
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "4.5 (130)",
                                    fontSize = 12.sp,
                                    color = colorResource(R.color.black)
                                )
                            }
                            Text(
                                text = "Niš",
                                fontSize = 12.sp,
                                color = colorResource(R.color.black)
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                                "when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
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

                        val productLeft = ProductUi(
                            id = "1", name = "Tomatoes", price = 200.0,
                            producer = "Milena", producerReview = 4.5,
                            city = "Niš", imageUrl = ""
                        )
                        ProductView(
                            productName = productLeft.name,
                            price = productLeft.price,
                            producer = productLeft.producer,
                            producerReview = productLeft.producerReview,
                            city = productLeft.city,
                            imageUrl = productLeft.imageUrl,
                            isFavorite = favoritesViewModel.isFavorite(productLeft),
                            onProductClick = { navController.navigate(MainRoutes.Product) },
                            onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                            onFavoriteClick = { favoritesViewModel.toggleFavorite(productLeft) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        val productRight = ProductUi(
                            id = "2", name = "Petra", price = 220.0,
                            producer = "Petra", producerReview = 4.5,
                            city = "Niš", imageUrl = ""
                        )
                        ProductView(
                            productName = productRight.name,
                            price = productRight.price,
                            producer = productRight.producer,
                            producerReview = productRight.producerReview,
                            city = productRight.city,
                            imageUrl = productRight.imageUrl,
                            isFavorite = favoritesViewModel.isFavorite(productRight),
                            onProductClick = { navController.navigate(MainRoutes.Product) },
                            onProducerClick = { navController.navigate(MainRoutes.ProfileProducer) },
                            onFavoriteClick = { favoritesViewModel.toggleFavorite(productRight) },
                            modifier = Modifier.fillMaxWidth()
                        )
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
                        Column( modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween) {
                            ReviewItem("marija", "4.3", "Opid uzas bjkbfajklbfajbfjabfafjlbadjfad", R.drawable.user)
                            Spacer(modifier = Modifier.height(10.dp))
                            ReviewItem("Marko", "4.3", "Opid dsfsdgfvszdgvbdfhbdfghbndghbndfzgbdzgvbfszcbhdfblnh sflkockkcccccccccccccbhioasdhfdiosssssssssssvdnbhuzas bjkbfajklbfajbfjabfafjlbadjfad", R.drawable.user)
                            Spacer(modifier = Modifier.height(10.dp))
                            ReviewItem("Petra", "4.3", "Opid dsfsdgfvszdgvbdfhbdfghbndghbndfzgbdzgvbfszcbhdfblnh sflkockkcccccccccccccbhioasdhfdiosssssssssssvdnbhuzas bjkbfajklbfajbfjabfafjlbadjfad", R.drawable.user)
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
    image: Int
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
                    Image(
                        painter = painterResource(image),
                        contentDescription = name,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
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
        navController = rememberNavController()
    )
}
