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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.proba.R
import com.example.proba.activity.homePage.CategoryView
import com.example.proba.activity.product.ProductView

@Composable
fun HomePage() {
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
                onMenuClick = { /* TODO */ },
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
                        // TODO: Navigate to Products page
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
                    ProductView(
                        productName = "Tomatoes",
                        price = 200.0,
                        producer = "Proizvodjac",
                        producerReview = 4.5,
                        city = "Niš",
                        imageProducer = R.drawable.user,
                        imageProduct = R.drawable.basket,
                        modifier = Modifier.weight(1f)
                    )

                    ProductView(
                        productName = "Potatoes",
                        price = 220.0,
                        producer = "Proizvodjac",
                        producerReview = 4.5,
                        city = "Niš",
                        imageProducer = R.drawable.user,
                        imageProduct = R.drawable.basket,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProductView(
                        productName = "Apples",
                        price = 180.0,
                        producer = "Proizvodjac",
                        producerReview = 4.2,
                        city = "Novi Sad",
                        imageProducer = R.drawable.user,
                        imageProduct = R.drawable.basket,
                        modifier = Modifier.weight(1f)
                    )

                    ProductView(
                        productName = "Carrots",
                        price = 150.0,
                        producer = "Proizvodjac",
                        producerReview = 4.7,
                        city = "Beograd",
                        imageProducer = R.drawable.user,
                        imageProduct = R.drawable.basket,
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
            bottomBarView()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage()
}
